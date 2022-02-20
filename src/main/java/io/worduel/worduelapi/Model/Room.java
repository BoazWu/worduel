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
import io.worduel.Actions.LobbyAction;
import io.worduel.Actions.LobbyActionTypes;
import io.worduel.Components.NameComponent;
import io.worduel.worduelapi.Networking.LobbyBroadcaster;

public class Room {
	private Executor executor = Executors.newSingleThreadExecutor();
	
	private String roomCode;
	private ArrayList<String> playersInRoom;
	private int playerCount;
    
    private HashMap<String, Boolean> playerReadyStatus;
    private int playerReadyCount;
    
    private LobbyBroadcaster lobbyBroadcaster;
    
    public Room(String roomCode) {
    	this.roomCode = roomCode;
    	this.playersInRoom = new ArrayList<String>();
    	this.playerReadyStatus = new HashMap<String, Boolean>();
    	this.playerReadyCount = 0;
    	this.playerCount = 0;
    	
    	lobbyBroadcaster = new LobbyBroadcaster(this);
    }
    
    
    public void addPlayer(String playerID) {
    	playerCount++;
        playersInRoom.add(playerID);
        playerReadyStatus.put(playerID, false);
    }
    
    public void removePlayer(String playerID) {
    	playerCount--;
        playersInRoom.remove(playerID);
        if(playerReadyStatus.get(playerID)) {
        	playerReadyCount--;
        }
        playerReadyStatus.remove(playerID);
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
					this.lobbyBroadcaster.broadcast(new LobbyAction("", LobbyActionTypes.START_GAME));
					playerReadyCount = 0;
					for(boolean b : playerReadyStatus.values()) {
						b = false;
					}
				}
			}else {
				playerReadyCount--;
			}
		}
	}
	public boolean getReadyStatus(String playerID) {
		return playerReadyStatus.get(playerID);
	}
	public LobbyBroadcaster getLobbyBroadcaster() {
		return this.lobbyBroadcaster;
	}
}
