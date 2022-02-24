package io.worduel.Views;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;

public class InterimScoresView extends Div{

	private String roomCode;
	
	public InterimScoresView(String roomCode) {
		this.roomCode = roomCode;
		add(new H1("Interim Scores View"));
	}
}
