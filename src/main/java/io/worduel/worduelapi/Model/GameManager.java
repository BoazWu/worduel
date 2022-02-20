package io.worduel.worduelapi.Model;

import java.util.HashMap;

import org.springframework.stereotype.Component;

@Component
public class GameManager {
	private final int ROOM_CODE_LENGTH = 5;
	private final int PLAYER_ID_LENGTH = 5;

	private HashMap<String, Player> players;
	private HashMap<String, Room> rooms;

	private int onlinePlayerCount;

	public GameManager() {
		players = new HashMap<String, Player>();
		rooms = new HashMap<String, Room>();
	}

	public int getOnlinePlayerCount() {
		return onlinePlayerCount;
	}

	public String getPlayerName(String playerID) {
		return players.get(playerID).getName();
	}

	public void setPlayerName(String playerID, String name) {
		players.get(playerID).setName(name);
	}

	public String addPlayer() {
		String id = generatePlayerID();
		players.put(id, new Player(id));
		onlinePlayerCount++;
		return id;
	}

	public void removePlayer(String id) {
		if (players.containsKey(id)) {
			players.remove(id);
			onlinePlayerCount--;
		}
	}

	public String addRoom() {
		String roomCode = generateRoomCode();
		rooms.put(roomCode, new Room(roomCode));
		return roomCode;
	}

	public void removeRoom(String roomCode) {
		rooms.remove(roomCode);
	}

	public boolean containsRoom(String roomCode) {
		return rooms.containsKey(roomCode);
	}

	public Room getRoom(String roomCode) {
		return rooms.get(roomCode);
	}

	// Helper method to randomly generate a Room Code
	private String generateRoomCode() {
		String roomCode;
		do {
			roomCode = "";
			for (int letter = 0; letter < ROOM_CODE_LENGTH; letter++) {
				roomCode += (char) (97 + 26 * Math.random());
			}
		} while (rooms.containsKey(roomCode));
		return roomCode;
	}

	// Helper method to randomly generate a player's ID
	private String generatePlayerID() {
		String id;
		do {
			id = "";
			for (int letter = 0; letter < PLAYER_ID_LENGTH; letter++) {
				id += (char) (65 + 26 * Math.random());
			}
		} while (players.containsKey(id));
		return id;
	}
}
