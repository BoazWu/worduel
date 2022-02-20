package io.worduel.worduelapi.Views;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.BeforeLeaveEvent;
import com.vaadin.flow.router.BeforeLeaveObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;

import io.worduel.Actions.LobbyAction;
import io.worduel.Actions.LobbyActionTypes;
import io.worduel.worduelapi.Model.GameManager;

@Push
@Route("/:roomCode")
public class RoomView extends Div implements BeforeEnterObserver, BeforeLeaveObserver {
	private String roomCode;
	private String playerID;
	
	
	private Registration roomBroadcasterRegistration;
	
	@Autowired
	GameManager gameManager;
	
	LobbyView lobbyView;

	public RoomView() {
		
	}
	
	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		roomCode = event.getRouteParameters().get("roomCode").get();
		if(!gameManager.containsRoom(roomCode)) {
			event.forwardTo(RoomNotFoundView.class);
		}else {
			playerID = gameManager.addPlayer();
			
			lobbyView = new LobbyView(roomCode, playerID, gameManager, event);
			
			gameManager.getRoom(roomCode).getLobbyBroadcaster().broadcast(new LobbyAction(playerID, LobbyActionTypes.CONNECT));
			
			add(lobbyView);
		}
	}

	@Override
	public void beforeLeave(BeforeLeaveEvent event) {
		exit();
		
	}
	@Override
    protected void onDetach(DetachEvent detachEvent) {
		exit();
    }

	private void exit() {
		roomBroadcasterRegistration.remove();
		roomBroadcasterRegistration = null;
		gameManager.removePlayer(playerID);
	}
}
