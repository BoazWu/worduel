package io.worduel.Components;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;

public class GameRow extends HorizontalLayout {
	private Label[] gameTiles;
	private UI ui;
	private String guess;
	private int index;
	private int length;

	public GameRow(int length, UI ui) {
		index = 0;
		this.ui = ui;
		this.length = length;
		gameTiles = new Label[length];
		for (int i = 0; i < length; i++) {
			Label temp = new Label("");
			temp.setClassName("GameLabel");
			gameTiles[i] = temp;
			this.add(temp);

		}
		guess = "";

	}

	// if all tiles are filled in, merge them into a word and return true
	public boolean checkFull() {
		guess = "";
		for (Label gameTile : gameTiles) {
			String letter = gameTile.getText();
			if (letter.equals("")) {
				return false;
			} else {
				guess += letter;
			}
		}
		return true;
	}

	public String getGuess() {
		return guess;
	}

	public void editRow(String letter) {
		// backSpace option
		if (letter.equals("_")) {
			if (index != 0) {
				ui.access(() -> gameTiles[index].setText(""));
				index--;
			}
		}
		// if there is space to edit
		else if (index < length) {

			int temp = index;
			ui.access(() -> gameTiles[temp].setText(letter));
			index++;

		} else {
			// tell user too many letters
			Notification notification = new Notification();
			notification.addThemeVariants(NotificationVariant.LUMO_ERROR);

			Div text = new Div(new Text("Too many letters"));

			Button closeButton = new Button(new Icon("lumo", "cross"));
			closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
			closeButton.getElement().setAttribute("aria-label", "Close");
			closeButton.addClickListener(event -> {
				notification.close();
			});

			HorizontalLayout layout = new HorizontalLayout(text, closeButton);
			layout.setAlignItems(Alignment.CENTER);

			notification.add(layout);
			notification.open();
		}

	}

	// Sets GameTile at a certain index to a certain color
	// g = green, y = yellow, w = white
	public void setTileColor(int tileIndex, char color) {
		if (color == 'g') {
			gameTiles[tileIndex].setClassName("GameLabel GreenTile");
		} else if (color == 'y') {
			gameTiles[tileIndex].setClassName("GameLabel YellowTile");
		} else {
			gameTiles[tileIndex].setClassName("GameLabel GrayTile");
		}
	}
}
