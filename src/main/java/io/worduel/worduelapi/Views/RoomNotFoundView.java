package io.worduel.worduelapi.Views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("room-not-found")
public class RoomNotFoundView extends VerticalLayout{
	
	public RoomNotFoundView() {
		Button backButton = new Button("Back to Home"); 
	    backButton.addClickListener(click -> { 
	      //Go to home page
	    });
		add(
				new H1("Room Not Found"),
				backButton
		);
	}
}
