package io.worduel.Model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Comparator;

import io.worduel.Actions.GameAction;
import io.worduel.Actions.GameActionTypes;

public class Game {
	
	private Room room;
	
	private int wordLength;
	private String correctWord;
	
	private String correctColoring;
	
	private long startTime;
	
	//tracks how many players have won
	private int playerDoneAmount;
	
	private ArrayList<Player> playersDoneList;
	
	public Game(Room room, int wordLength) {
		this.room = room;
		this.wordLength = wordLength;
		this.playerDoneAmount = 0;
		this.correctColoring = "";
		this.playersDoneList = new ArrayList<Player>();
		for(int i = 0; i < wordLength; i++) {
			correctColoring += 'g';
		}
		this.correctWord = room.getGameManager().generateWord();
		System.out.println(this.correctWord);
		
		this.startTime = System.currentTimeMillis();
	
	}
	
	public void makeInput(String playerID, String guessColoring) {
		
		room.getGameManager().getPlayer(playerID).addGuessColoring(guessColoring);
		room.getGameBroadcaster().broadcast(new GameAction(playerID, GameActionTypes.MAKE_INPUT));
		
		if(guessColoring.equals(correctColoring)) {
			playerDoneAmount++;
			playersDoneList.add(room.getGameManager().getPlayer(playerID));
			room.getGameManager().getPlayer(playerID).setFinishTime((int)(System.currentTimeMillis() - this.startTime));
			if(playerDoneAmount == room.getPlayerCount()) {
				Collections.sort(playersDoneList, new Comparator<Player>() {
					@Override
					public int compare(Player p1, Player p2) {
						if (p1.getNumGuesses() != p2.getNumGuesses()) {
							return Integer.compare(p1.getNumGuesses(), p2.getNumGuesses());
						}
						return Integer.compare(p1.getFinishTime(), p2.getFinishTime());
					}
				});

				for(int i = 0; i < playersDoneList.size(); i++) {
					playersDoneList.get(i).addScore(room.getPlayerCount() - i);
				}
				room.roundOver();
			}
		}
		
		
	}
	
	
	public int getWordLength() {
		return this.wordLength;
	}
	public String getCorrectWord() {
		return this.correctWord;
	}
	public String getCorrectColoring() {
		return this.correctColoring;
	}
}
