package io.worduel.Views;

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
					String key = event.getEventData().getString("event.key").toUpperCase();
					if(key.equals("ENTER")) {
						enterKeyPressed();
					}else if(key.equals("BACKSPACE")) {
						letterKeyPressed("_");
					}else if(key.length() == 1 && (int)(key.toCharArray()[0]) >= 65 && (int)(key.toCharArray()[0]) <= 90){
						letterKeyPressed(key);
					}
				}));
		
		gameBroadcasterRegistration = gameManager.getRoom(roomCode).getGameBroadcaster().register(this.playerID,
				action -> {
					NameComponent nameComponent;
					switch (action.getActionID()) {
					case "MAKE_INPUT":
						if (action.getSender() == this.playerID) {
							// I made the input
							this.getUI().get().access(() -> add(
									new H4("I made the input " + gameRound.getPastGuessColoring(this.playerID))));
						} else {
							// Someone else made the input
							this.getUI().get().access(() -> add(new H4(action.getSender() + " made the input "
									+ gameRound.getPastGuessColoring(action.getSender()))));
						}
						break;
					case "ROUND_OVER":
						gameBroadcasterRegistration.remove();
						roomView.roundOver();
						break;
					}
				});
		
		gameRowList.add(new GameRow(gameRound.getWordLength(), roomView.getUI().get()));
		currentGameRow = (GameRow) gameRowList.getComponentAt(gameRowList.getComponentCount() - 1);
		
		//You need to have an "addClickShortcut" method call somewhere in the code otherwise the JSON Event Data cannot process a KeyboardEvent
		//If you don't have it, the JSON Event Data has a value of null when you press a key
		//The event is still triggered, the data is simply null
		//I am not sure why this happens, I will look into it more later
		Button b = new Button("temp");
		b.addClickShortcut(Key.ENTER);
		
		add(new H1("GameView"), gameRowList, b);
		boolean wordGuessed = false;

	}

	private void letterKeyPressed(String letter) {
		currentGameRow.editRow(letter);
	}
	
	private void enterKeyPressed() {
		// check the word, if the row is full and the word is valid, get hints
		if (currentGameRow.checkFull() && gameRound.checkWord(currentGameRow.getGuess())) {
			gameRowList.add(new GameRow(gameRound.getWordLength(), roomView.getUI().get()));
			
			
			gameRound.giveHints();
			System.out.println("Word Submitted: " + currentGameRow.getGuess());
			currentGameRow = (GameRow) gameRowList.getComponentAt(gameRowList.getComponentCount() - 1);
			
			
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
	
	public void disconnectListeners() {
		gameBroadcasterRegistration.remove();
		domListenerRegistration.remove();
	}

}