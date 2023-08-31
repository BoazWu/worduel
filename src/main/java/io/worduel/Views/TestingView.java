package io.worduel.Views;

import java.awt.Window;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteParameters;

import io.worduel.Model.GameManager;

@Push
@Route("")
public class TestingView extends VerticalLayout{
	
	@Autowired
	GameManager roomManager;
	
	public TestingView() {
	    
		TextField roomCodeField = new TextField(); 
	    Button makeRoomButton = new Button("Make Room"); 
	    makeRoomButton.addClickListener(click -> { 
	    	String roomCode = roomManager.addRoom();
	    	makeRoomButton.getUI().ifPresent(ui -> ui.navigate(RoomView.class, new RouteParameters("roomCode", roomCode)));
	    });
	    Button joinRoomButton = new Button("Join Room"); 
	    joinRoomButton.addClickListener(click -> { 
	    	joinRoomButton.getUI().ifPresent(ui -> ui.navigate(RoomView.class, new RouteParameters("roomCode", roomCodeField.getValue())));
	    });
	    
	    
	    

	    
	    HorizontalLayout userInput = new HorizontalLayout(
		    	roomCodeField,        
		    	joinRoomButton
		      );
	   
	    VerticalLayout centerLayout = new VerticalLayout(
	    	new HorizontalLayout(
	    	    	makeRoomButton
	    	      ),
	    	      userInput
	    );
	    centerLayout.setClassName("HomeLayout");
	    add( 
	      new H1("Worduel"),
	      centerLayout
	      
	    );
	    
	    setAlignItems(FlexComponent.Alignment.CENTER);
	    

	}
}
