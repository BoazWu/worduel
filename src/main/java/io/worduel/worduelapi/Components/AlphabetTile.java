package io.worduel.worduelapi.Components;

import io.worduel.worduelapi.*;

public class AlphabetTile extends Tile{
	public AlphabetTile(char letter){
		super(letter, GlobalVariables.smallTileLength, true);
	}
}
