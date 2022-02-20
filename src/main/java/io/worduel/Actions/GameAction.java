package io.worduel.Actions;

public class GameAction extends Action{
	public GameAction(String sender, GameActionTypes actionID){
		super(sender, actionID.name());
	}
}
