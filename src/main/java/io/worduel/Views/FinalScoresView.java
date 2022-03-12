package io.worduel.Views;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;

public class FinalScoresView extends Div{
	private String roomCode;
	
	public FinalScoresView(String roomCode) {
		this.roomCode = roomCode;
		add(new H1("Final Scores View"));
	}
}
