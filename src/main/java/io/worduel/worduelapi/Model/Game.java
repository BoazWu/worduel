package io.worduel.worduelapi.Model;

import java.util.ArrayList;
import java.util.HashMap;

import io.worduel.Actions.GameAction;
import io.worduel.Actions.GameActionTypes;

public class Game {
	
	private Room room;
	
	private int wordLength;
	
	private ArrayList<String> players;
	
	private HashMap<String, Integer> numGuesses;
	private HashMap<String, ArrayList<String>> pastGuesses; 
	private HashMap<String, Integer> numYellows;
	private HashMap<String, Integer> numGreens;
	private HashMap<String, String> pastGuessColoring;
	
	public Game(Room room, int wordLength, ArrayList<String> players) {
		this.room = room;
		this.wordLength = wordLength;
		this.players = players;
		
		this.numGuesses = new HashMap<String, Integer>();
		this.pastGuesses = new HashMap<String, ArrayList<String>>();
		this.numYellows = new HashMap<String, Integer>();
		this.numGreens = new HashMap<String, Integer>();
		this.pastGuessColoring = new HashMap<String, String>();

		for(String playerID : players) {
			numGuesses.put(playerID, 0);
			pastGuesses.put(playerID, new ArrayList<String>());
			numYellows.put(playerID, 0);
			numGreens.put(playerID, 0);
			pastGuessColoring.put(playerID, "");
		}
		
	}
	
	public void makeInput(String playerID, String input) {
		
		pastGuesses.get(playerID).add(input);
		pastGuessColoring.put(playerID, input); //here it should be the coloring, not the actual input
		/*do something with the input
		*
		*
		*
		**/
		room.getGameBroadcaster().broadcast(new GameAction(playerID, GameActionTypes.MAKE_INPUT));
	}
	
	//Generates the hidden word for this game
	private String generateWord() {
		//remember to use wordLength
		return "word";
	}
	
	// returns true if the word was in word list, returns false if not
	public boolean checkWord(String guess) {
		//checks word list
		return true;
	}
	
	// returns an array containing which gives a hint for each index
	public void giveHints() {
		
	}
	
	public String getPastGuessColoring(String playerID) {
		return pastGuessColoring.get(playerID);
	}
	
	public int getWordLength() {
		return wordLength;
	}
}
