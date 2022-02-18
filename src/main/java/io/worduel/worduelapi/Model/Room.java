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
	private String roomCode;
	private Player host;
	private ArrayList<String> playersInRoom;
	
	private Executor executor = Executors.newSingleThreadExecutor();

    private LinkedList<Consumer<Action>> playerList = new LinkedList<>();
    private HashMap<String, NameComponent> nameComponents;
    
    
    public Room(String roomCode) {
    	this.roomCode = roomCode;
    	this.nameComponents = new HashMap<String, NameComponent>();
    	this.playersInRoom = new ArrayList<String>();
    }
    
    public synchronized Registration register(String playerID, Consumer<Action> player) {
        playerList.add(player);
        playersInRoom.add(playerID);
        
        return () -> {
            synchronized (Broadcaster.class) {
            	broadcast(new RoomAction(playerID, RoomActionTypes.DISCONNECT));
                playerList.remove(player);
                playersInRoom.remove(playerID);
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
    
}
