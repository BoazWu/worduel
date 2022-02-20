package io.worduel.worduelapi.Views;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.shared.Registration;

import io.worduel.worduelapi.Model.GameManager;

public class GameView extends Div{

	private String roomCode;
	private String playerID;
	private GameManager gameManager;
	
	private Registration gameBroadcasterRegistration;
	
	public GameView(String roomCode, String playerID, GameManager gameManager) {
		this.roomCode = roomCode;
		this.playerID = playerID;
		this.gameManager = gameManager;
		add(new H1("GameView"));
	}
	
}
