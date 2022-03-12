package io.worduel.Model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import io.worduel.Actions.GameAction;
import io.worduel.Actions.GameActionTypes;

public class Game {
	
	private Room room;
	
	private int wordLength;
	private String correctWord;
	
	public Game(Room room, int wordLength) {
		this.room = room;
		this.wordLength = wordLength;
		
		this.correctWord = room.getGameManager().generateWord();
		System.out.println(this.correctWord);
	}
	
	public void makeInput(String playerID, String guessColoring) {
		
		room.getGameManager().getPlayer(playerID).addGuessColoring(guessColoring);
		room.getGameBroadcaster().broadcast(new GameAction(playerID, GameActionTypes.MAKE_INPUT));
	}
	
	
	public int getWordLength() {
		return wordLength;
	}
	public String getCorrectWord() {
		return correctWord;
	}
}
