package io.worduel.Views;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.ClientCallable;
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
import io.worduel.Model.GameManager;

@Push
@Route("/:roomCode")
public class RoomView extends Div implements BeforeEnterObserver {
	private String roomCode;
	private String playerID;

	@Autowired
	private GameManager gameManager;

	private LobbyView lobbyView;
	private GameView gameView;
	private InterimScoresView interimScoresView;

	public RoomView() {

	}

	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		roomCode = event.getRouteParameters().get("roomCode").get();
		if (!gameManager.containsRoom(roomCode)) {
			event.forwardTo(RoomNotFoundView.class);
		} else {
			this.getElement()
					.executeJs("function closeListener() { $0.$server.windowClosed(); } "
							+ "window.addEventListener('beforeunload', closeListener); "
							+ "window.addEventListener('unload', closeListener);", getElement());

			playerID = gameManager.addPlayer();

			lobbyView = new LobbyView(this, roomCode, playerID, gameManager, event);

			gameManager.getRoom(roomCode).getLobbyBroadcaster()
					.broadcast(new LobbyAction(playerID, LobbyActionTypes.CONNECT));

			add(lobbyView);

		}
	}

	public void startGame() {
		gameView = new GameView(this, roomCode, playerID, gameManager);
		this.getUI().get().access(() -> {
			remove(lobbyView);
			add(gameView);
		});
	}

	public void roundOver() {
		// Check if this is the last round, if it is, show finalScoresView
		interimScoresView = new InterimScoresView(this.roomCode);
		this.getUI().get().access(() -> {
			remove(gameView);
			add(interimScoresView);
		});
	}

	@ClientCallable
	public void windowClosed() {
		gameManager.removePlayer(playerID);

		if (lobbyView != null) {
			lobbyView.unregisterFromLobby();
		}
		if (gameView != null) {
			gameView.unregisterFromGame();
		}
		System.out.println("Window closed " + playerID);
	}
}
