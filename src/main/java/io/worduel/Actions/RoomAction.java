package io.worduel.Actions;

public class RoomAction extends Action{
	public RoomAction(String sender, RoomActionTypes actionID){
		super(sender, actionID.name());
	}
}
