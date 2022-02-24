package io.worduel.Components;

import io.worduel.GlobalVariables;

public class GameTile extends Tile{
	public GameTile() {
		super(' ', GlobalVariables.largeTileLength, true);
		this.setClassName("GameTile");
	}
}
