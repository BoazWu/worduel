package io.worduel.worduelapi.Model;

public class Player {
	private String name;
	private String id;
	private boolean readyStatus;
	
	public Player(String id) {
		this.id = id;
		this.name = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public void setReadyStatus(boolean readyStatus) {
		this.readyStatus = readyStatus;
	}
	public boolean getReadyStatus() {
		return readyStatus;
	}
}
