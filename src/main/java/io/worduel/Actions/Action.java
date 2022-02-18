package io.worduel.Actions;

public class Action {
	private String actionID;
	private String sender;
	public Action(String sender, String actionID) {
		this.sender = sender;
		this.actionID = actionID;
	}
	public String getActionID() {
		return actionID;
	}
	public String getSender() {
		return sender;
	}
}
