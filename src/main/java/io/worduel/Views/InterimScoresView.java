package io.worduel.Views;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H4;

import io.worduel.Model.GameManager;

public class InterimScoresView extends Div{

	private String roomCode;
	private GameManager gameManager;
	
	public InterimScoresView(String roomCode, GameManager gameManager) {
		this.roomCode = roomCode;
		this.gameManager = gameManager;
		add(new H1("Current Scores"));
		for(String playerID : gameManager.getRoom(roomCode).getPlayersInRoom()) {
			add(new H4(gameManager.getPlayer(playerID).getName() + ": " + gameManager.getPlayer(playerID).getScore()));
		}
		
	}
}
