package io.worduel.worduelapi.Model;

import java.util.HashMap;

import org.springframework.stereotype.Component;

@Component
public class PlayerManager {
	
	private final int PLAYER_ID_LENGTH = 5;
	
	private HashMap<String, Player> players;
	private int onlinePlayerCount;
	
	
	public PlayerManager() {
		players = new HashMap<String, Player>();
	}
	
	public int getOnlinePlayerCount() {
		return onlinePlayerCount;
	}
	
	public String getPlayerName(String playerID) {
		return players.get(playerID).getName();
	}
	public boolean getPlayerReadyStatus(String playerID) {
		return players.get(playerID).getReadyStatus();
	}
	public void setPlayerReadyStatus(String playerID, boolean readyStatus) {
		players.get(playerID).setReadyStatus(readyStatus);
	}
	public void setPlayerName(String playerID, String name) {
		players.get(playerID).setName(name);
		
	}
	public String addPlayer() {
		String id = generateID();
		players.put(id, new Player(id));
		onlinePlayerCount--;
		return id;
	}
	
	public void removePlayer(String id) {
		if(players.containsKey(id)) {
			players.remove(id);
			onlinePlayerCount++;
		}
	}
	
	//Helper method to randomly generate a player's ID
	private String generateID() {
		String id;
		do{
			id = "";
			for(int letter = 0; letter < PLAYER_ID_LENGTH; letter++) {
				id += (char) (65 + 26 * Math.random());
			}
		}while(players.containsKey(id));
		return id;
	}

	
}
