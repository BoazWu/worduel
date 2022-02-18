package io.worduel.worduelapi.Pages;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import io.worduel.worduelapi.Components.*;

import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;

@Route("game")
@Theme(themeFolder = "mytheme")
public class GamePage extends VerticalLayout {
	
	private int letterCount = 5;
	public GamePage() {
		
		VerticalLayout gameField = new VerticalLayout();
		
		gameField.setAlignItems(FlexComponent.Alignment.CENTER);
		
		gameField.add(new H1("Game"));
				
		gameField.add((new GameRow(letterCount)).getGameRow());
		
		/*
		TextField taskField = new TextField();
		Button addButton = new Button("Submit");
		addButton.addClickListener(click -> {
			Label word = new Label((taskField.getValue()));
			gameField.add(word);
			taskField.clear();
		});
		addButton.addClickShortcut(Key.ENTER);
		
		add(gameField, new HorizontalLayout(taskField, addButton));
		*/
		add(gameField);
	}
	
	
}