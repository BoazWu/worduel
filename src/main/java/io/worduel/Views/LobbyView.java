package io.worduel.Views;

import java.util.HashMap;

import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.shared.Registration;

import io.worduel.Actions.LobbyAction;
import io.worduel.Actions.LobbyActionTypes;
import io.worduel.Components.NameComponent;
import io.worduel.Model.GameManager;

public class LobbyView extends Div{
	
	private RoomView roomView;

	private String roomCode;
	private String playerID;
	private GameManager gameManager;
	
	private Registration lobbyBroadcasterRegistration;
	
	private VerticalLayout playersInRoom;
	private HashMap<String, NameComponent> nameComponents;
	
	public LobbyView(RoomView roomView, String roomCode, String playerID, GameManager gameManager, BeforeEnterEvent event) {
		this.roomView = roomView;
		
		this.roomCode = roomCode;
		this.playerID = playerID;
		this.gameManager = gameManager;
		
		nameComponents = new HashMap<String, NameComponent>();
		playersInRoom = new VerticalLayout();
		for(String otherPlayerID : gameManager.getRoom(roomCode).getPlayersInRoom()) {
			NameComponent nameComponent = new NameComponent(gameManager.getPlayerName(otherPlayerID), gameManager.getPlayer(otherPlayerID).getReadyStatus());
			event.getUI().access(() -> playersInRoom.add(nameComponent));
			nameComponents.put(otherPlayerID, nameComponent);
		}
		lobbyBroadcasterRegistration = gameManager.getRoom(roomCode).getLobbyBroadcaster().register(playerID, action -> {
			NameComponent nameComponent;
			switch(action.getActionID()) {
				case "CONNECT":
					nameComponent = new NameComponent(gameManager.getPlayerName(action.getSender()), false);
		            event.getUI().access(() -> playersInRoom.add(nameComponent));
		            nameComponents.put(action.getSender(), nameComponent);
					break;
				case "DISCONNECT":
					nameComponent = nameComponents.get(action.getSender());
		            event.getUI().access(() -> playersInRoom.remove(nameComponent));
		            nameComponents.remove(action.getSender());
					break;
				case "UPDATE_NAME":
					nameComponent = nameComponents.get(action.getSender());
					event.getUI().access(()-> nameComponent.setName(gameManager.getPlayerName(action.getSender())));
					break;
				case "READY_UP":
					nameComponent = nameComponents.get(action.getSender());
					event.getUI().access(()-> nameComponent.setReadyStatus(true));
					break;
				case "UNREADY":
					nameComponent = nameComponents.get(action.getSender());
					event.getUI().access(()-> nameComponent.setReadyStatus(false));
					break;
				case "START_GAME":
					roomView.startGame();
					break;
			}
        });
		
		TextField nameTextField = new TextField(); 
	    Button updateNameButton = new Button("Update Name"); 
	    updateNameButton.addClickListener(click -> { 
	    	if(nameTextField.getValue() != "") {
	    		gameManager.setPlayerName(playerID, nameTextField.getValue());
	    		gameManager.getRoom(roomCode).getLobbyBroadcaster().broadcast(new LobbyAction(playerID, LobbyActionTypes.UPDATE_NAME));
	    		nameTextField.setValue("");
	    	}
	    });
		
		
		Button readyUpButton = new Button("Ready Up"); 
	    readyUpButton.addClickListener(click -> { 
	      if(gameManager.getPlayer(playerID).getReadyStatus()) {
	    	  gameManager.getRoom(roomCode).setReadyStatus(playerID, false);
	    	  readyUpButton.setText("Ready Up");
	    	  gameManager.getRoom(roomCode).getLobbyBroadcaster().broadcast(new LobbyAction(playerID, LobbyActionTypes.UNREADY));
	    	  
	      }else {
	    	  gameManager.getRoom(roomCode).setReadyStatus(playerID, true);
	    	  readyUpButton.setText("Unready");
	    	  gameManager.getRoom(roomCode).getLobbyBroadcaster().broadcast(new LobbyAction(playerID, LobbyActionTypes.READY_UP));
	      }
	    });
	    
		add(
			new H1("Lobby View"),
			new H1("RoomCode: " + roomCode),
			new H1("Your ID: " + playerID),
			new HorizontalLayout(
			    nameTextField,
			    updateNameButton
			),
			readyUpButton,
			playersInRoom
		);
	}
	@Override
    protected void onDetach(DetachEvent detachEvent) {
		//exit();
    }
	public void unregisterFromLobby() {
		lobbyBroadcasterRegistration.remove();
		lobbyBroadcasterRegistration = null;

	}
}
