package io.worduel.worduelapi.Model;

public class Player {
	private String name;
	private String id;

	public Player(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
