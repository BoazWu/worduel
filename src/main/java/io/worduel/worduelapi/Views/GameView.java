package io.worduel.worduelapi.Views;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
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
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.shared.Registration;

import io.worduel.Actions.GameAction;
import io.worduel.Actions.GameActionTypes;
import io.worduel.Actions.LobbyAction;
import io.worduel.Actions.LobbyActionTypes;
import io.worduel.Components.NameComponent;
import io.worduel.worduelapi.Components.GameRow;
import io.worduel.worduelapi.Model.Game;
import io.worduel.worduelapi.Model.GameManager;

@CssImport("./themes/mytheme/styles.css")
public class GameView extends Div{

	private RoomView roomView;
	
	private String roomCode;
	private String playerID;
	private GameManager gameManager;
	private Game gameRound;
	
	private Registration gameBroadcasterRegistration;
	
	public GameView(RoomView roomView, String roomCode, String playerID, GameManager gameManager) {
		this.roomView = roomView;
		this.roomCode = roomCode;
		this.playerID = playerID;
		this.gameManager = gameManager;
		gameRound = gameManager.getRoom(roomCode).getGame();
		
		gameBroadcasterRegistration = gameManager.getRoom(roomCode).getGameBroadcaster().register(this.playerID, action -> {
			NameComponent nameComponent;
			switch(action.getActionID()) {
				case "MAKE_INPUT":
					if(action.getSender() == this.playerID) {
						//I made the input
						this.getUI().get().access(() -> add(new H4("I made the input " + gameRound.getPastGuessColoring(this.playerID))));
					}else {
						//Someone else made the input
						this.getUI().get().access(() -> add(new H4(action.getSender() + " made the input " + gameRound.getPastGuessColoring(action.getSender()))));
					}
					break;
				case "ROUND_OVER":
					gameBroadcasterRegistration.remove();
					roomView.roundOver();
					break;
			}
		});
		GameRow gameRow = new GameRow(gameRound.getWordLength(), roomView.getUI().get());
		
		TextField inputTextField = new TextField(); 
	    Button inputButton = new Button("Input Letter"); 
		inputTextField.setMaxLength(1);
		inputTextField.setPattern("^[a-zA-Z_]$");
		inputTextField.setPreventInvalidInput(true);
	     
	     
		inputButton.addClickListener(click -> { 
	    	if(inputTextField.getValue() != "") {
	    		gameRow.editRow(inputTextField.getValue());
	    		inputTextField.setValue("");
	    	}
	    });

		add(new H1("GameView"), new HorizontalLayout(inputTextField, inputButton));
		boolean wordGuessed = false;
		

		newGuess(gameRow);

		
	}
	
	private void newGuess(GameRow gameRow) {
		
		Button submitButton = new Button("Submit");
		submitButton.addClickListener(click -> {

			// check the word, if the row is full and the word is valid, get hints
			if(gameRow.checkFull() && gameRound.checkWord(gameRow.getGuess())) {
				
				gameRound.giveHints();
				
				
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
		
		});
		submitButton.addClickShortcut(Key.ENTER);
		this.add(submitButton);
			
		add(gameRow ,
				submitButton
				
			);
		
		// listen for all 5 letters to be filled in 
		
	}
	
	
}
	
