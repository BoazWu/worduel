package io.worduel.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import io.worduel.Actions.LobbyAction;
import io.worduel.Actions.LobbyActionTypes;
import io.worduel.Networking.GameBroadcaster;
import io.worduel.Networking.LobbyBroadcaster;

public class Room {
	private String roomCode;
	private ArrayList<String> playersInRoom;
	private int playerCount;
    
    private int playerReadyCount;
    
    private LobbyBroadcaster lobbyBroadcaster;
    private GameBroadcaster gameBroadcaster;
    
    private Game game;
    
    private GameManager gameManager;
    
    public Room(String roomCode, GameManager gameManager) {
    	this.gameManager = gameManager;
    	this.roomCode = roomCode;
    	this.playersInRoom = new ArrayList<String>();
    	this.playerReadyCount = 0;
    	this.playerCount = 0;
    	
    	lobbyBroadcaster = new LobbyBroadcaster(this);
    }
    
    
    public void addPlayer(String playerID) {
    	playerCount++;
        playersInRoom.add(playerID);
    }
    
    public void removePlayer(String playerID) {
    	playerCount--;
        playersInRoom.remove(playerID);
        if(gameManager.getPlayer(playerID).getReadyStatus() == true) {
        	playerReadyCount--;
        }
        if(playerCount == 0) {
        	gameManager.removeRoom(roomCode);
        }
    }
    
    public ArrayList<String> getPlayersInRoom(){
    	return playersInRoom;
    }
	public void setReadyStatus(String playerID, boolean readyStatus) {
		if(readyStatus != gameManager.getPlayer(playerID).getReadyStatus()) {
			gameManager.getPlayer(playerID).setReadyStatus(readyStatus);
			if(readyStatus) {
				playerReadyCount++;
				if(playerCount >= 2 && playerReadyCount == playerCount) {
					gameBroadcaster = new GameBroadcaster(this);
					game = new Game(this, 5);
					this.lobbyBroadcaster.broadcast(new LobbyAction("", LobbyActionTypes.START_GAME));
					playerReadyCount = 0;
					for(String player : playersInRoom) {
						gameManager.getPlayer(playerID).resetLobbyVariables();
					}
				}
			}else {
				playerReadyCount--;
			}
		}
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
	
	public GameManager getGameManager() {
		return this.gameManager;
	}
}
