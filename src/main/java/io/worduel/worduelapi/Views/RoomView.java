package io.worduel.worduelapi.Views;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

import io.worduel.worduelapi.Model.RoomManager;

@Push
@Route("/room/:roomCode")
public class RoomView extends Div implements BeforeEnterObserver {
	private String roomCode;
	
	@Autowired
	RoomManager roomManager;

	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		roomCode = event.getRouteParameters().get("roomCode").get();
		if(!roomManager.containsRoom(roomCode)) {
			event.forwardTo(RoomNotFoundView.class);
		}
	}
	
	public RoomView() {
		add(
			new H1("RoomCode: " + roomCode)
		);
	}
}
