package io.worduel.Views;

import java.io.IOException;
import java.util.Arrays;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.dom.DomEventListener;
import com.vaadin.flow.dom.DomListenerRegistration;
import com.vaadin.flow.shared.Registration;

import io.worduel.Components.NameComponent;
import io.worduel.Components.GameRow;
import io.worduel.Model.Game;
import io.worduel.Model.GameManager;

@CssImport("./themes/mytheme/styles.css")
public class GameView extends Div {

	private RoomView roomView;
	private String roomCode;
	private String playerID;
	
	private GameManager gameManager;
	
	private Game gameRound;

	private GameRow currentGameRow;
	
	private VerticalLayout gameRowList;
	
	private Registration gameBroadcasterRegistration;
	
	private DomListenerRegistration domListenerRegistration;

	public GameView(RoomView roomView, String roomCode, String playerID, GameManager gameManager) {
		
		this.roomView = roomView;
		this.roomCode = roomCode;
		this.playerID = playerID;
		this.gameManager = gameManager;
		this.setClassName("GameView");
		gameRound = gameManager.getRoom(roomCode).getGame();
		
		gameRowList = new VerticalLayout();
		
		this.roomView.getUI().get().access(() -> this.domListenerRegistration = this.roomView.getUI().get().getElement()
				.addEventListener("keydown", (DomEventListener) event -> {
					try {
					String key = event.getEventData().getString("event.key").toUpperCase();
					if(key.equals("ENTER")) {
							enterKeyPressed();

					}else if(key.equals("BACKSPACE")) {
						letterKeyPressed("_");
					}else if(key.length() == 1 && (int)(key.toCharArray()[0]) >= 65 && (int)(key.toCharArray()[0]) <= 90){
						letterKeyPressed(key);
					}
					}
				
				catch(Exception e) {
					e.printStackTrace();
				}
				}));
		
		gameBroadcasterRegistration = gameManager.getRoom(roomCode).getGameBroadcaster().register(this.playerID,
				action -> {
					NameComponent nameComponent;
					switch (action.getActionID()) {
					case "MAKE_INPUT":
						if (action.getSender() == this.playerID) {
							// I made the input
							// Do nothing
						} else {
							// Someone else made the input
							// Update the right-side sidebar
						}
						break;
					case "ROUND_OVER":
						roomView.roundOver();
						break;
					
					case "FINAL_ROUND_OVER":
						roomView.finalRoundOver();
						break;
					}
				});
		
		gameRowList.add(new GameRow(gameRound.getWordLength(), roomView.getUI().get()));
		currentGameRow = (GameRow) gameRowList.getComponentAt(gameRowList.getComponentCount() - 1);
		
		//You need to have an "addClickShortcut" method call somewhere in the code otherwise the JSON Event Data cannot process a KeyboardEvent
		//If you don't have it, the JSON Event Data has a value of null when you press a key
		//The event is still triggered, the data is simply null
		//I am not sure why this happens, I will look into it more later
		Button b = new Button("");
		b.addClickShortcut(Key.ENTER);
		
		add(new H1("Worduel"), gameRowList, b);
		boolean wordGuessed = false;

	}

	private void letterKeyPressed(String letter) {
		if(currentGameRow != null) {
			currentGameRow.editRow(letter);
		}
	}
	
	private void enterKeyPressed() throws IOException {
		if(this.currentGameRow == null) {
			return;
		}
		// check the word, if the row is full and the word is valid, get hints
		if (currentGameRow.checkFull() && gameManager.checkWord(currentGameRow.getGuess())) {
			
			String coloring = generateColoring(currentGameRow.getGuess(), gameRound.getCorrectWord());
			
			for(int i = 0; i < coloring.length(); i++) {
				currentGameRow.setTileColor(i, coloring.charAt(i));

			}
			gameRound.makeInput(playerID, coloring);
			if(coloring.equals(gameRound.getCorrectColoring())) {
				//Win! Do something here as well, like display a win pop up. or maybe just do nothing and wait for everyone else to finish
				currentGameRow = null;
			}else {
				gameRowList.add(new GameRow(gameRound.getWordLength(), roomView.getUI().get()));
				
				currentGameRow = (GameRow) gameRowList.getComponentAt(gameRowList.getComponentCount() - 1);
			}
			
			
		} else {
			// if word isn't valid, tell the user
			Notification notification = new Notification();
			notification.addThemeVariants(NotificationVariant.LUMO_ERROR);

			Div text = new Div(new Text("Invalid Word"));

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
	
	//Helper method to generate a guess coloring
	private String generateColoring(String g, String cw) {
		int length = g.length();
		char[] coloring = new char[length];
		boolean[] usedIndexBitmask = new boolean[length];
		Arrays.fill(coloring, 'w');
		char[] guess = g.toCharArray();
		char[] correctWord = cw.toCharArray();
		for(int i = 0; i < length; i++) {
			if(guess[i] == correctWord[i]) {
				coloring[i] = 'g';
				usedIndexBitmask[i] = true;
			}
		}
		for(int i = 0; i < length; i++) {
			if(coloring[i] != 'g') {
				for(int j = 0; j < length; j++) {
					if(guess[i] == correctWord[j] && !usedIndexBitmask[j]) {
						coloring[i] = 'y';
						usedIndexBitmask[j] = true;
						break;
					}
				}
			}
		}
		return String.valueOf(coloring);
	}
	
	public void unregisterFromGame() {
		gameBroadcasterRegistration.remove();	
		gameBroadcasterRegistration = null;
		domListenerRegistration.remove();
		domListenerRegistration = null;
	}

}