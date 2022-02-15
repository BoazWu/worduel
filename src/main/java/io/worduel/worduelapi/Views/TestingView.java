package io.worduel.worduelapi.Views;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteParameters;
import com.vaadin.flow.router.RouterLink;

import io.worduel.worduelapi.Model.Room;
import io.worduel.worduelapi.Model.RoomManager;

@Push
@Route("/testing")
public class TestingView extends VerticalLayout{
	
	@Autowired
	RoomManager roomManager;
	
	public TestingView() {
    TextField roomCodeField = new TextField(); 
    Button makeRoomButton = new Button(new RouterLink("Make Room", RoomView.class, new RouteParameters("roomCode", roomCodeField.getValue()))); 
    makeRoomButton.addClickListener(click -> { 
    	roomManager.addRoom(roomCodeField.getValue(), new Room(roomCodeField.getValue()));
    	//go to page
    });

    add( 
      new H1("Worduel"),
      new HorizontalLayout(
        roomCodeField,
        makeRoomButton
      )
    );
	}
}
