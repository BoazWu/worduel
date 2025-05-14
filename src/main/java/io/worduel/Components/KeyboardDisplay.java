package io.worduel.Components;

import java.awt.Component;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;

public class KeyboardDisplay extends VerticalLayout {
	private HorizontalLayout topRow;
	private HorizontalLayout middleRow;
	private HorizontalLayout bottomRow;
	private char[] topLetters;
	private char[] middleLetters;
	private char[] bottomLetters;
	
	private char[] keyColors;
	private int[] charToIndex;

	private Button[] keys;

	public KeyboardDisplay() {
		this.addClassName("KeyboardDisplay");
		this.setAlignItems(Alignment.CENTER);

		topRow = new HorizontalLayout();
		middleRow = new HorizontalLayout();
		bottomRow = new HorizontalLayout();

		topLetters = new char[] { 'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P' };
		middleLetters = new char[] { 'A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L' };
		bottomLetters = new char[] { '-', 'Z', 'X', 'C', 'V', 'B', 'N', 'M', '_' };
		charToIndex = new int[] {10, 23, 21, 12, 2, 13, 14, 15, 7, 16, 17, 18, 25, 24, 8, 9, 0, 3, 11, 4, 6, 22, 1, 20, 5, 19};
		keyColors = new char[26];
		
		keys = new Button[28];
		
		int index = 0;
		
		for (char letter : topLetters) {
			Button temp = new Button(("" + letter).toUpperCase());
			temp.addClassName("KeyBoardButton");
			topRow.add(temp);
			keys[index] = temp;
			index++;
		}
		for (char letter : middleLetters) {
			Button temp = new Button(("" + letter).toUpperCase());
			temp.addClassName("KeyBoardButton");
			middleRow.add(temp);
			keys[index] = temp;
			index++;
		}
		for (char letter : bottomLetters) {
			if(letter == '-') {
				Button temp = new Button("\u23CE");
				temp.addClassName("KeyBoardButton");
				bottomRow.add(temp);
				keys[index] = temp;
			} else if (letter == '_') {
				Button temp = new Button("\u232B");
				temp.addClassName("KeyBoardButton");
				bottomRow.add(temp);
				keys[index] = temp;
			} else {
				Button temp = new Button(("" + letter).toUpperCase());
				temp.addClassName("KeyBoardButton");
				bottomRow.add(temp);
				keys[index] = temp;
			}
			index++;
		}

		// Center the content within each row
		topRow.setJustifyContentMode(JustifyContentMode.CENTER);
		middleRow.setJustifyContentMode(JustifyContentMode.CENTER);
		bottomRow.setJustifyContentMode(JustifyContentMode.CENTER);

		this.add(topRow, middleRow, bottomRow);
	}

	// possible indices range from 0-25 where each index corresponds to qwerty
	// keyboard location
	// example: 0 -> q, 1 -> w, 25 -> m
	public void setKeyColor(char key, char color) {
		int index = charToIndex[key - 'A'];
		
		Button target;
		if (index <= 9) {

			target = (Button) topRow.getComponentAt(index);
		} else if (index <= 18) {
			target = (Button) middleRow.getComponentAt(index - 10);
		} else {
			target = (Button) bottomRow.getComponentAt(index - 18);
		}
		if (color == 'g' && keyColors[key - 'A'] != 'g') {
			target.setClassName("GreenKeyboardButton");
			keyColors[key - 'A'] = 'g';
		} else if (color == 'y' && keyColors[key - 'A'] != 'y' && keyColors[key - 'A'] != 'g') {
			target.setClassName("YellowKeyboardButton");
			keyColors[key - 'A'] = 'y';
		} else if (keyColors[key - 'A'] != 'w' && keyColors[key - 'A'] != 'y' && keyColors[key - 'A'] != 'g'){
			target.setClassName("GrayKeyboardButton");
			keyColors[key - 'A'] = 'w';
		}
	}

	public char getKeyColor(char key) {
		return keyColors[key - 'A'];
	}
	
	public Button[] getKeys() {
		return keys;
	}
	
	public char[] getTopLetters() {
		return topLetters;
	}
	
	public char[] getMiddleLetters() {
		return middleLetters;
	}
	
	public char[] getBottomLetters() {
		return bottomLetters;
	}
}
