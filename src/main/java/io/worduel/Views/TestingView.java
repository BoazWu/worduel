package io.worduel.Views;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
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
		setSizeFull();
		setAlignItems(Alignment.CENTER);
		setJustifyContentMode(JustifyContentMode.CENTER);

		H1 title = new H1("Worduel");
		title.addClassName("home-title");

		VerticalLayout newGameLayout = new VerticalLayout();
		newGameLayout.setAlignItems(Alignment.CENTER);
		newGameLayout.addClassName("home-panel");
		Button makeRoomButton = new Button("New Game");
		makeRoomButton.addClassName("home-button");
		makeRoomButton.addClickListener(click -> {
			String roomCode = roomManager.addRoom();
			makeRoomButton.getUI().ifPresent(ui -> ui.navigate(RoomView.class, new RouteParameters("roomCode", roomCode)));
		});
		newGameLayout.add(makeRoomButton);

		VerticalLayout joinGameLayout = new VerticalLayout();
		joinGameLayout.setAlignItems(Alignment.CENTER);
		joinGameLayout.addClassName("home-panel");
		TextField roomCodeField = new TextField("Room Code");
		roomCodeField.addClassName("home-input");
		Button joinRoomButton = new Button("Join Game");
		joinRoomButton.addClassName("home-button");
		joinRoomButton.addClickListener(click -> {
			joinRoomButton.getUI().ifPresent(ui -> ui.navigate(RoomView.class, new RouteParameters("roomCode", roomCodeField.getValue())));
		});
		joinGameLayout.add(roomCodeField, joinRoomButton);

		HorizontalLayout mainActionsLayout = new HorizontalLayout(newGameLayout, joinGameLayout);
		mainActionsLayout.setWidth("60%");
		mainActionsLayout.setJustifyContentMode(JustifyContentMode.CENTER);
		mainActionsLayout.expand(newGameLayout);
		mainActionsLayout.expand(joinGameLayout);
		mainActionsLayout.setAlignItems(Alignment.END);

		add(title, mainActionsLayout);
	}
}
