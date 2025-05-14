package io.worduel.Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import static java.util.concurrent.TimeUnit.*;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
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
		
		
		InputStream in = getClass().getResourceAsStream("/words.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		
		String word;
		
		// populate all words
		while((word = br.readLine()) != null) {
			word = word.toUpperCase();
			allWords.add(word);
			
		}
		
		in = getClass().getResourceAsStream("/popularWords.txt");
		br = new BufferedReader(new InputStreamReader(in));
		int targetWordsIdx = 0;
		while(targetWordsIdx < TARGET_WORDS_THRESHOLD && (word = br.readLine()) != null) {
			word = word.toUpperCase();
			targetWords[targetWordsIdx++] = word;
		}
		
		br.close();
	}

	// returns true if the word was in word list, returns false if not
	public boolean checkWord(String guess) {
		return allWords.contains(guess);
	}
	
	//Generates the hidden word for this game
	public String generateWord() {
		return targetWords[(int)(Math.random() * TARGET_WORDS_THRESHOLD)];
	}
	
	private String generateUniqueDefaultWord() {
	    String potentialName;
	    int attempts = 0;
	    final int MAX_ATTEMPTS = 20; // Max attempts to find a unique name from the word list

	    // Create a set of current player names for quick lookup (case-insensitive)
	    Set<String> currentNames = new HashSet<>();
	    for (Player p : players.values()) {
	        if (p.getName() != null) { // Ensure name is not null before adding
	            currentNames.add(p.getName().toLowerCase());
	        }
	    }

	    do {
	        potentialName = generateWord(); // Uses existing method picking from targetWords
	        attempts++;
	        // Check if the lowercase version of the potential name is already in use
	    } while (potentialName == null || currentNames.contains(potentialName.toLowerCase()) && attempts < MAX_ATTEMPTS);

	    if (potentialName == null || (currentNames.contains(potentialName.toLowerCase()) && attempts >= MAX_ATTEMPTS) ) {
	        // Fallback if a unique word isn't found quickly or generateWord returns null
	        // Generate a more unique fallback, e.g., with a larger random number or part of ID
	        return ("Player" + (int)(Math.random() * 100000)).toLowerCase(); // Ensure fallback is lowercase
	    }
	    return potentialName.toLowerCase(); // Ensure returned word is lowercase
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
		Player newPlayer = new Player(id); // Player constructor sets name to id initially
		
		String defaultName = generateUniqueDefaultWord();
		newPlayer.setName(defaultName); // Override with a unique word from the library

		players.put(id, newPlayer);
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
