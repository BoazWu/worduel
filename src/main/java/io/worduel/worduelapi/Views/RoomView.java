package io.worduel.worduelapi.Views;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.BeforeLeaveEvent;
import com.vaadin.flow.router.BeforeLeaveObserver;
import com.vaadin.flow.router.Route;

import io.worduel.worduelapi.Model.PlayerManager;
import io.worduel.worduelapi.Model.RoomManager;

@Push
@Route("/room/:roomCode")
public class RoomView extends Div implements BeforeEnterObserver, BeforeLeaveObserver {
	private String roomCode;
	private String playerID;
	
	@Autowired
	RoomManager roomManager;
	
	@Autowired
	PlayerManager playerManager;

	public RoomView() {
		
	}
	
	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		roomCode = event.getRouteParameters().get("roomCode").get();
		if(!roomManager.containsRoom(roomCode)) {
			event.forwardTo(RoomNotFoundView.class);
		}
		playerID = playerManager.addPlayer();
		add(
			new H1("RoomCode: " + roomCode),
			new H1("Your ID: " + playerID)
		);
	}

	@Override
	public void beforeLeave(BeforeLeaveEvent event) {
		playerManager.removePlayer(playerID);
	}
}
