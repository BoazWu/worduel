package io.worduel.worduelapi.Model;

import java.util.HashMap;

import org.springframework.stereotype.Component;

@Component
public class RoomManager {
	private final int ROOM_CODE_LENGTH = 5;
	
	private HashMap<String, Room> rooms;
	
	public RoomManager() {
		rooms = new HashMap<String, Room>();
	}
	
	public String addRoom() {
		String roomCode = generateRoomCode();
		rooms.put(roomCode, new Room(roomCode));
		return roomCode;
	}
	public void removeRoom(String roomCode) {
		rooms.remove(roomCode);
	}
	
	public boolean containsRoom(String roomCode) {
		return rooms.containsKey(roomCode);
	}
	public Room getRoom(String roomCode) {
		return rooms.get(roomCode);
	}
	
	//Helper method to randomly generate a Room Code
		private String generateRoomCode() {
			String roomCode;
			do{
				roomCode = "";
				for(int letter = 0; letter < ROOM_CODE_LENGTH; letter++) {
					roomCode += (char) (97 + 26 * Math.random());
				}
			}while(rooms.containsKey(roomCode));
			return roomCode;
		}
}
