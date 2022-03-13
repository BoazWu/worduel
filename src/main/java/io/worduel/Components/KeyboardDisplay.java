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

	public KeyboardDisplay() {
		this.addClassName("KeyboardDisplay");
		topRow = new HorizontalLayout();
		middleRow = new HorizontalLayout();
		bottomRow = new HorizontalLayout();

		topLetters = new char[] { 'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p' };
		middleLetters = new char[] { 'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l' };
		bottomLetters = new char[] { '-', 'z', 'x', 'c', 'v', 'b', 'n', 'm', '_' };

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
	public void setKeyColor(int index, char color) {
		Button target;
		if (index <= 9) {

			target = (Button) topRow.getComponentAt(index);
		} else if (index <= 18) {
			target = (Button) middleRow.getComponentAt(index - 10);
		} else {
			target = (Button) bottomRow.getComponentAt(index - 18);
		}
		if (color == 'g') {
			target.setClassName("GreenKeyboardButton");
		} else if (color == 'y') {
			target.setClassName("YellowKeyboardButton");
		} else {
			target.setClassName("GrayKeyboardButton");
		}
	}

}
