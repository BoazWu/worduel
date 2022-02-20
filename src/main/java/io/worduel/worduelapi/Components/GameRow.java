package io.worduel.worduelapi.Components;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;


public class GameRow extends HorizontalLayout{
	private GameTile[] gameTiles;
	
	public GameRow(int length) {
		gameTiles = new GameTile[length];
		for(int i = 0; i < length; i++) {
			GameTile temp = new GameTile();
			//temp.setClassName("GameTile");
			gameTiles[i] = temp;
			this.add(temp);
			
		}
	}
}
