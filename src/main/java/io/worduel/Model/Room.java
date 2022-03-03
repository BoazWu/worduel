package io.worduel.Model;

import java.util.ArrayList;
import java.util.HashMap;

import io.worduel.Actions.LobbyAction;
import io.worduel.Actions.LobbyActionTypes;
import io.worduel.Networking.GameBroadcaster;
import io.worduel.Networking.LobbyBroadcaster;

public class Room {
	private String roomCode;
	private ArrayList<String> playersInRoom;
	private int playerCount;
    
    private HashMap<String, Boolean> playerReadyStatus;
    private int playerReadyCount;
    
    private LobbyBroadcaster lobbyBroadcaster;
    private GameBroadcaster gameBroadcaster;
    
    private Game game;
    
    private GameManager gameManager;
    
    public Room(String roomCode, GameManager gameManager) {
    	this.gameManager = gameManager;
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
        if(playerCount == 0) {
        	gameManager.removeRoom(roomCode);
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
					gameBroadcaster = new GameBroadcaster(this);
					game = new Game(this, 5, playersInRoom);
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
	public GameBroadcaster getGameBroadcaster() {
		return this.gameBroadcaster;
	}
	public String getRoomCode() {
		return this.roomCode;
	}
	public Game getGame() {
		return this.game;
	}
	public int getPlayerCount() {
		return this.playerCount;
	}
}
