package io.worduel.Model;

import java.util.ArrayList;

public class Player {
	//General Variables
	private String name;
	private String id;
	
	//Lobby Variables
	private boolean readyStatus;
	
	//Game Variables
	private ArrayList<String> pastGuessColorings;
	private int numGuesses;
	private int numYellows;
	private int numGreens;
	
	public Player(String id) {
		this.id = id;
		this.name = id;
	}
	
	//resets lobby variables
	public void resetLobbyVariables() {
		this.readyStatus = false;
	}
	
	//resets game variables
	public void resetGameVariables() {
		this.numGuesses = 0;
		this.numYellows = 0;
		this.numGreens = 0;
		this.pastGuessColorings = new ArrayList<String>();
	}
	
	//General Variable Getter/Setters
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	//Lobby Variable Getter/Setters
	public void setReadyStatus(boolean readyStatus) {
		this.readyStatus = readyStatus;
	}
	public boolean getReadyStatus() {
		return this.readyStatus;
	}

	//Game Variable Getter/Setters
	public int getNumGuesses() {
		return this.numGuesses;
	}
	public int getNumYellows() {
		return this.numYellows;
	}
	public int getNumGreens() {
		return this.numGreens;
	}
	public void addGuessColoring(String guessColoring) {
		
		pastGuessColorings.add(guessColoring);
	}
}
