package io.worduel.worduelapi.Components;
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


public class GameRow extends HorizontalLayout{
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
		for(int i = 0; i < length; i++) {
			Label temp = new Label("a");

			gameTiles[i] = temp;
			this.add(temp);
			
		}
		guess = "";
		
	}
	
	// if all tiles are filled in, merge them into a word and return true
	public boolean checkFull() {
		guess = "";
		for(Label gameTile: gameTiles) {
			String letter = gameTile.getText(); 
			if(letter.equals("")) {
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
		if(letter.equals("_")) {
			ui.access(() ->  gameTiles[index].setText(""));
			index--;
		}
		// if there is space to edit 
		else if(index < length) {
			index++;
			ui.access(() -> gameTiles[index - 1].setText(letter));
			
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
}
