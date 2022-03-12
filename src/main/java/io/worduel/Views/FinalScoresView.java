package io.worduel.Views;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H4;

import io.worduel.Model.GameManager;

public class FinalScoresView extends Div{
	private String roomCode;
	private GameManager gameManager;
	
	//This will eventually have a Home button, Play Again button, back to lobby button, etc
	public FinalScoresView(String roomCode, GameManager gameManager) {
		this.roomCode = roomCode;
		this.gameManager = gameManager;
		add(new H1("Final Scores"));
		for(String playerID : gameManager.getRoom(roomCode).getPlayersInRoom()) {
			add(new H4(gameManager.getPlayer(playerID).getName() + ": " + gameManager.getPlayer(playerID).getScore()));
		}
	}
}
