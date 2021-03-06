package io.worduel.Components;

import com.vaadin.flow.component.textfield.TextField;


public class Tile extends TextField{
	private char letter;
	private boolean letterVisible;
	private String color;
	private int sideLength;
	
	
	
	public Tile(char letter, int sideLength, boolean letterVisible){
		this.letter = letter;
		this.sideLength = sideLength;
		this.letterVisible = letterVisible;
		color = "white";
		this.setMaxlength(1);
		this.setPattern("^[a-zA-Z]$");
		this.setPreventInvalidInput(true);

	}
	
	public void setColor(String color){
		this.color = color;
	}
	
	public void setLetter(char letter) {
		this.letter = letter;
	}
	
	public void setVisibility(boolean letterVisible) {
		this.letterVisible = letterVisible;
	}
}
