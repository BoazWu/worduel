package io.worduel.worduelapi.Components;

public class Tile {
	private char letter;
	private boolean letterVisible;
	private String color;
	private int sideLength;
	
	
	
	public Tile(char letter, int sideLength, boolean letterVisible){
		this.letter = letter;
		this.sideLength = sideLength;
		this.letterVisible = letterVisible;
		color = "white";
	}
	
	private void setColor(String color){
		this.color = color;
	}
	
	private void setLetter(char letter) {
		this.letter = letter;
	}
	
	private void setVisibility(boolean letterVisible) {
		this.letterVisible = letterVisible;
	}
}
