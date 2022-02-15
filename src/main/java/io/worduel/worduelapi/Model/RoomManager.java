package io.worduel.worduelapi.Model;

import java.util.HashMap;

import org.springframework.stereotype.Component;

@Component
public class RoomManager {
	private HashMap<String, Room> roomMap;
	
	public RoomManager() {
		roomMap = new HashMap<String, Room>();
	}
	
	public void addRoom(String roomCode, Room room) {
		roomMap.put(roomCode, room);
	}
	public void removeRoom(String roomCode) {
		roomMap.remove(roomCode);
	}
	
	public boolean containsRoom(String roomCode) {
		return roomMap.containsKey(roomCode);
	}
	public Room getRoom(String roomCode) {
		return roomMap.get(roomCode);
	}
	
}
