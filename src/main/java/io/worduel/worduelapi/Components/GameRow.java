package io.worduel.worduelapi.Components;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;


public class GameRow{
	private GameTile[] gameTiles;
	private HorizontalLayout gameRow;
	
	public GameRow(int length) {
		gameRow = new HorizontalLayout();
		gameTiles = new GameTile[length];
		for(int i = 0; i < length; i++) {
			GameTile temp = new GameTile();
			gameTiles[i] = temp;
			gameRow.add(temp);
			
		}
	}
	
	public HorizontalLayout getGameRow(){
		return gameRow;
	}
}
