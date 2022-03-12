package io.worduel.Model;

import java.util.ArrayList;

public class Player implements Comparable{
	//General Variables
	private String name;
	private String id;
	
	//Lobby Variables
	private boolean readyStatus;
	
	//Game Variables
	private int score;
	
	//Round Variables
	private ArrayList<String> pastGuessColorings;
	private int numGuesses;
	private int numYellows;
	private int numGreens;
	private int finishTime;
	
	public Player(String id) {
		this.id = id;
		this.name = id;
	}
	
	//resets lobby variables
	public void resetLobbyVariables() {
		this.readyStatus = false;
	}
	
	//resets round variables
	public void resetRoundVariables() {
		this.numGuesses = 0;
		this.numYellows = 0;
		this.numGreens = 0;
		this.pastGuessColorings = new ArrayList<String>();
		this.finishTime = -1;
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
	public void addScore(int score) {
		this.score += score;
	}
	public int getScore() {
		return this.score;
	}
	
	//Round Variable Getter/Setters
	public int getNumGuesses() {
		return this.numGuesses;
	}
	public int getNumYellows() {
		return this.numYellows;
	}
	public int getNumGreens() {
		return this.numGreens;
	}
	public int getFinishTime() {
		return this.finishTime;
	}
	public void setFinishTime(int finishTime) {
		this.finishTime = finishTime;
	}
	public void addGuessColoring(String guessColoring) {
		this.numGuesses++;
		this.pastGuessColorings.add(guessColoring);
	}

	@Override
	public int compareTo(Object o) {
		if(o instanceof Player) {
			Player otherPlayer = (Player) o;
			if(this.numGuesses != otherPlayer.getNumGuesses()) {
				return this.numGuesses - otherPlayer.getNumGuesses();
			}else {
				return this.finishTime - otherPlayer.getFinishTime();
			}
		}
		System.out.println("Comparing Player " + this.id + " to non-player object " + o.toString());
		return 0;
	}
}
