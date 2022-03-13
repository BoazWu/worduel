package io.worduel.Components;

import java.awt.Component;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class KeyboardDisplay extends VerticalLayout {
	private HorizontalLayout topRow;
	private HorizontalLayout middleRow;
	private HorizontalLayout bottomRow;
	private char[] topLetters;
	private char[] middleLetters;
	private char[] bottomLetters;
	
	private char[] keyColors;
	private int[] charToIndex;

	public KeyboardDisplay() {
		this.addClassName("KeyboardDisplay");
		topRow = new HorizontalLayout();
		middleRow = new HorizontalLayout();
		bottomRow = new HorizontalLayout();

		topLetters = new char[] { 'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p' };
		middleLetters = new char[] { 'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l' };
		bottomLetters = new char[] { '-', 'z', 'x', 'c', 'v', 'b', 'n', 'm', '_' };
		charToIndex = new int[] {10, 23, 21, 12, 2, 13, 14, 15, 7, 16, 17, 18, 25, 24, 8, 9, 0, 3, 11, 4, 6, 22, 1, 20, 5, 19};
		keyColors = new char[26];

		for (char letter : topLetters) {
			Button temp = new Button(("" + letter).toUpperCase());
			temp.addClassName("KeyBoardButton");
			topRow.add(temp);
		}
		for (char letter : middleLetters) {
			Button temp = new Button(("" + letter).toUpperCase());
			temp.addClassName("KeyBoardButton");
			middleRow.add(temp);
		}
		for (char letter : bottomLetters) {
			Button temp = new Button(("" + letter).toUpperCase());
			temp.addClassName("KeyBoardButton");
			bottomRow.add(temp);
		}

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

}
