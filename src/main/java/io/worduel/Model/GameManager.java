package io.worduel.Model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import static java.util.concurrent.TimeUnit.*;

import org.springframework.stereotype.Component;

@Component
public class GameManager {
	private final int ROOM_CODE_LENGTH = 5;
	private final int PLAYER_ID_LENGTH = 5;
	
	//The n most common words can be target words
	private final int TARGET_WORDS_THRESHOLD = 1000;

	private HashMap<String, Player> players;
	private HashMap<String, Room> rooms;

	private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
	
	private int onlinePlayerCount;

	private HashSet<String> allWords;
	private String[] targetWords;
	
	public GameManager() throws IOException {
		players = new HashMap<String, Player>();
		rooms = new HashMap<String, Room>();
		allWords = new HashSet<String>();
		targetWords = new String[TARGET_WORDS_THRESHOLD];
		
		BufferedReader br = new BufferedReader(new FileReader("src/main/java/io/worduel/AllWords.txt"));
		String word;
		int targetWordsIdx = 0;
		while((word = br.readLine()) != null) {
			word = word.toUpperCase();
			allWords.add(word);
			if(targetWordsIdx < TARGET_WORDS_THRESHOLD) {
				targetWords[targetWordsIdx++] = word;
			}
		}
	}

	// returns true if the word was in word list, returns false if not
	public boolean checkWord(String guess) {
		return allWords.contains(guess);
	}
	
	//Generates the hidden word for this game
	public String generateWord() {
		return targetWords[(int)(Math.random() * TARGET_WORDS_THRESHOLD)];
	}
	
	public int getOnlinePlayerCount() {
		return onlinePlayerCount;
	}

	public String getPlayerName(String playerID) {
		return players.get(playerID).getName();
	}
	public Player getPlayer(String playerID) {
		return players.get(playerID);
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
	public void deleteRoom(String roomCode) {
		if (rooms.containsKey(roomCode)) {
			rooms.remove(roomCode);
		}
	}

	public String addRoom() {
		String roomCode = generateRoomCode();
		rooms.put(roomCode, new Room(roomCode, this));
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
	public void runFunctionWithDelay (Runnable function, int delay) {
		scheduler.schedule(function, delay, SECONDS);
	}

}
