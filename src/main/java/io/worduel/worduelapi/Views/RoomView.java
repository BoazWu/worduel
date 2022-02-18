package io.worduel.worduelapi.Views;

import java.util.HashMap;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.BeforeLeaveEvent;
import com.vaadin.flow.router.BeforeLeaveObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;

import io.worduel.Actions.RoomAction;
import io.worduel.Actions.RoomActionTypes;
import io.worduel.Components.NameComponent;
import io.worduel.worduelapi.Model.PlayerManager;
import io.worduel.worduelapi.Model.RoomManager;

@Push
@Route("/room/:roomCode")
public class RoomView extends Div implements BeforeEnterObserver, BeforeLeaveObserver {
	private String roomCode;
	private String playerID;
	private VerticalLayout playersInRoom;
	private HashMap<String, NameComponent> nameComponents;
	
	private Registration roomBroadcasterRegistration;
	
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
		}else {
			playerID = playerManager.addPlayer();
			nameComponents = new HashMap<String, NameComponent>();
			playersInRoom = new VerticalLayout();
			for(String playerID : roomManager.getRoom(roomCode).getPlayersInRoom()) {
				NameComponent nameComponent = new NameComponent(playerManager.getPlayerName(playerID));
				event.getUI().access(() -> playersInRoom.add(nameComponent));
				nameComponents.put(playerID, nameComponent);
			}
			roomBroadcasterRegistration = roomManager.getRoom(roomCode).register(playerID, action -> {
				NameComponent nameComponent;
				switch(action.getActionID()) {
					case "CONNECT":
						nameComponent = new NameComponent(playerManager.getPlayerName(action.getSender()));
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
						event.getUI().access(()-> nameComponent.setName(playerManager.getPlayerName(action.getSender())));
						break;
					case "READY_UP":
						nameComponent = nameComponents.get(action.getSender());
						event.getUI().access(()-> nameComponent.setReadyStatus(true));
						break;
					case "UNREADY":
						nameComponent = nameComponents.get(action.getSender());
						event.getUI().access(()-> nameComponent.setReadyStatus(false));
						break;
				}
	        });
			
			roomManager.getRoom(roomCode).broadcast(new RoomAction(playerID, RoomActionTypes.CONNECT));
			
			TextField nameTextField = new TextField(); 
		    Button updateNameButton = new Button("Update Name"); 
		    updateNameButton.addClickListener(click -> { 
		    	if(nameTextField.getValue() != "") {
		    		playerManager.setPlayerName(playerID, nameTextField.getValue());
		    		roomManager.getRoom(roomCode).broadcast(new RoomAction(playerID, RoomActionTypes.UPDATE_NAME));
		    		nameTextField.setValue("");
		    	}
		    });
			
			
			Button readyUpButton = new Button("Ready Up"); 
		    readyUpButton.addClickListener(click -> { 
		      if(playerManager.getPlayerReadyStatus(playerID)) {
		    	  playerManager.setPlayerReadyStatus(playerID, false);
		    	  readyUpButton.setText("Ready Up");
		    	  roomManager.getRoom(roomCode).broadcast(new RoomAction(playerID, RoomActionTypes.UNREADY));
		    	  
		      }else {
		    	  playerManager.setPlayerReadyStatus(playerID, true);
		    	  readyUpButton.setText("Unready");
		    	  roomManager.getRoom(roomCode).broadcast(new RoomAction(playerID, RoomActionTypes.READY_UP));
		      }
		    });
		    
			add(
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
		playerManager.removePlayer(playerID);
	}
}
