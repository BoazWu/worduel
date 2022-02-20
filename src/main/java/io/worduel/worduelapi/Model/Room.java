package io.worduel.worduelapi.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.shared.Registration;

import io.worduel.Actions.Action;
import io.worduel.Actions.RoomAction;
import io.worduel.Actions.RoomActionTypes;
import io.worduel.Components.NameComponent;
import io.worduel.worduelapi.Networking.Broadcaster;

public class Room {
	private Executor executor = Executors.newSingleThreadExecutor();
	
	private String roomCode;
	private ArrayList<String> playersInRoom;
	private int playerCount;

    private LinkedList<Consumer<Action>> playerList = new LinkedList<>();
    private HashMap<String, NameComponent> nameComponents;
    
    private HashMap<String, Boolean> playerReadyStatus;
    private int playerReadyCount;
    
    public Room(String roomCode) {
    	this.roomCode = roomCode;
    	this.nameComponents = new HashMap<String, NameComponent>();
    	this.playersInRoom = new ArrayList<String>();
    	this.playerReadyStatus = new HashMap<String, Boolean>();
    	this.playerReadyCount = 0;
    	this.playerCount = 0;
    }
    
    public synchronized Registration register(String playerID, Consumer<Action> player) {
    	playerCount++;
    	playerList.add(player);
        playersInRoom.add(playerID);
        playerReadyStatus.put(playerID, false);
        
        return () -> {
            synchronized (Broadcaster.class) {
            	broadcast(new RoomAction(playerID, RoomActionTypes.DISCONNECT));
            	playerCount--;
                playerList.remove(player);
                playersInRoom.remove(playerID);
                if(playerReadyStatus.get(playerID)) {
                	playerReadyCount--;
                }
                playerReadyStatus.remove(playerID);
            }
        };
    }

    public synchronized void broadcast(Action action) {
        for (Consumer<Action> player : playerList) {
            executor.execute(() -> player.accept(action));
        }
    }
    public ArrayList<String> getPlayersInRoom(){
    	return playersInRoom;
    }
	public void setReadyStatus(String playerID, boolean readyStatus) {
		if(readyStatus != playerReadyStatus.get(playerID)) {
			playerReadyStatus.put(playerID, readyStatus);
			if(readyStatus) {
				playerReadyCount++;
				if(playerCount >= 2 && playerReadyCount == playerCount) {
					//Start the game
					System.out.println("Start the game");
				}
			}else {
				playerReadyCount--;
			}
		}
	}
	public boolean getReadyStatus(String playerID) {
		return playerReadyStatus.get(playerID);
	}
}
