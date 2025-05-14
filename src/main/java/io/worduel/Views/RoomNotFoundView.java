package io.worduel.Views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("room-not-found")
public class RoomNotFoundView extends VerticalLayout{
	
	public RoomNotFoundView() {
		setSizeFull();
		setAlignItems(Alignment.CENTER);
		setJustifyContentMode(JustifyContentMode.CENTER);

		Button backButton = new Button("Back to Home"); 
	    backButton.addClickListener(click -> { 
	      getUI().ifPresent(ui -> ui.navigate(TestingView.class));
	    });

		H1 title = new H1("Room Not Found");

		add(
			title,
			backButton
		);
	}
}
