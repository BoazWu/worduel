package io.worduel.Components;

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
		
		topLetters = new char[] {
			'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p'	
		};
		middleLetters = new char[] {
			'a','s','d','f','g','h','j','k','l'
		};
		bottomLetters = new char[] {
			'-','z','x','c','v','b','n','m','_'
		};
		
		for(char letter : topLetters) {
			Button temp = new Button(("" + letter).toUpperCase());
			temp.addClassName("KeyBoardButton");
			topRow.add(temp);
		}
		for(char letter : middleLetters) {
			Button temp = new Button(("" + letter).toUpperCase());
			temp.addClassName("KeyBoardButton");
			middleRow.add(temp);
		}
		for(char letter : bottomLetters) {
			Button temp = new Button(("" + letter).toUpperCase());
			temp.addClassName("KeyBoardButton");
			bottomRow.add(temp);
		}
		
		this.add(topRow, middleRow, bottomRow);
	}
	
	public void setKeyColor(String color) {
		
	}

}
