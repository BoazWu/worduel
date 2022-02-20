package io.worduel.Actions;

public class LobbyAction extends Action{
	public LobbyAction(String sender, LobbyActionTypes actionID){
		super(sender, actionID.name());
	}
}
