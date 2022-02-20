package io.worduel.worduelapi.Views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.shared.Registration;

import io.worduel.Actions.GameAction;
import io.worduel.Actions.GameActionTypes;
import io.worduel.Actions.LobbyAction;
import io.worduel.Actions.LobbyActionTypes;
import io.worduel.Components.NameComponent;
import io.worduel.worduelapi.Model.GameManager;

public class GameView extends Div{

	private RoomView roomView;
	
	private String roomCode;
	private String playerID;
	private GameManager gameManager;
	
	private Registration gameBroadcasterRegistration;
	
	public GameView(RoomView roomView, String roomCode, String playerID, GameManager gameManager) {
		this.roomView = roomView;
		
		this.roomCode = roomCode;
		this.playerID = playerID;
		this.gameManager = gameManager;
		
		gameBroadcasterRegistration = gameManager.getRoom(roomCode).getGameBroadcaster().register(this.playerID, action -> {
			NameComponent nameComponent;
			switch(action.getActionID()) {
				case "MAKE_INPUT":
					if(action.getSender() == this.playerID) {
						//I made the input
						this.getUI().get().access(() -> add(new H4("I made the input " + gameManager.getRoom(roomCode).getGame().getPastGuessColoring(this.playerID))));
					}else {
						//Someone else made the input
						this.getUI().get().access(() -> add(new H4(action.getSender() + " made the input " + gameManager.getRoom(roomCode).getGame().getPastGuessColoring(action.getSender()))));
					}
					break;
				case "ROUND_OVER":
					gameBroadcasterRegistration.remove();
					roomView.roundOver();
					break;
			}
		});
		
		TextField inputTextField = new TextField(); 
	    Button inputButton = new Button("Make Input"); 
	    inputButton.addClickListener(click -> { 
	    	if(inputTextField.getValue() != "") {
	    		gameManager.getRoom(roomCode).getGame().makeInput(playerID, inputTextField.getValue());
	    		inputTextField.setValue("");
	    	}
	    });
		
		
		add(
				new H1("GameView"),
				new HorizontalLayout(
					    inputTextField,
					    inputButton
					)
				);
	}
	
}
