package io.worduel.Model;

public class Player {
	private String name;
	private String id;
	
	public Player(String id) {
		this.id = id;
		this.name = id;
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
