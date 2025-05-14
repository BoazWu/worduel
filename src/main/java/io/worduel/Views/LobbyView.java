package io.worduel.Views;

import java.util.HashMap;

import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
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
	
	private VerticalLayout playersInRoomLayout;
	private HashMap<String, NameComponent> nameComponents;
	
	public LobbyView(RoomView roomView, String roomCode, String playerID, GameManager gameManager, BeforeEnterEvent event) {
		this.roomView = roomView;
		
		this.roomCode = roomCode;
		this.playerID = playerID;
		this.gameManager = gameManager;
		
		nameComponents = new HashMap<String, NameComponent>();
		playersInRoomLayout = new VerticalLayout();
		playersInRoomLayout.addClassName("player-list-panel");
        playersInRoomLayout.setAlignItems(Alignment.CENTER);
        playersInRoomLayout.setWidth("300px");

		for(String otherPlayerID : gameManager.getRoom(roomCode).getPlayersInRoom()) {
			NameComponent nameComponent = new NameComponent(gameManager.getPlayerName(otherPlayerID), gameManager.getPlayer(otherPlayerID).getReadyStatus());
			event.getUI().access(() -> playersInRoomLayout.add(nameComponent));
			nameComponents.put(otherPlayerID, nameComponent);
		}
		lobbyBroadcasterRegistration = gameManager.getRoom(roomCode).getLobbyBroadcaster().register(playerID, action -> {
			NameComponent nameComponent;
			switch(action.getActionID()) {
				case "CONNECT":
					nameComponent = new NameComponent(gameManager.getPlayerName(action.getSender()), false);
		            event.getUI().access(() -> playersInRoomLayout.add(nameComponent));
		            nameComponents.put(action.getSender(), nameComponent);
					break;
				case "DISCONNECT":
					nameComponent = nameComponents.get(action.getSender());
		            event.getUI().access(() -> playersInRoomLayout.remove(nameComponent));
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
		
		TextField yourNameField = new TextField("Your Name");
        yourNameField.setValue(gameManager.getPlayerName(playerID));
        yourNameField.setValueChangeMode(ValueChangeMode.EAGER);
        yourNameField.addValueChangeListener(nameChangeEvent -> {
            String newName = nameChangeEvent.getValue();
            if (newName != null && !newName.trim().isEmpty() && !newName.equals(gameManager.getPlayerName(playerID))) {
                gameManager.setPlayerName(playerID, newName.trim());
                gameManager.getRoom(roomCode).getLobbyBroadcaster().broadcast(new LobbyAction(playerID, LobbyActionTypes.UPDATE_NAME));
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
	    
        H3 playerListTitle = new H3("Players in Lobby");

        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setAlignItems(Alignment.CENTER);
        mainLayout.setSizeFull(); 

        Span roomInfo = new Span("Room: " + roomCode);
        roomInfo.addClassName("room-info-text");

        Button exitRoomButton = new Button("Exit Room", VaadinIcon.SIGN_OUT.create());
        exitRoomButton.addClickListener(e -> {
            UI.getCurrent().navigate(TestingView.class);
        });
        exitRoomButton.addClassName("exit-room-button");

        HorizontalLayout topBarLayout = new HorizontalLayout(roomInfo, exitRoomButton);
        topBarLayout.setWidth("100%");
        topBarLayout.setJustifyContentMode(JustifyContentMode.END);
        topBarLayout.setAlignItems(Alignment.CENTER);
        topBarLayout.getStyle().set("padding-right", "20px");

		mainLayout.add(
            topBarLayout,
			yourNameField,
			readyUpButton,
            playerListTitle,
			playersInRoomLayout
		);
        add(mainLayout);
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
