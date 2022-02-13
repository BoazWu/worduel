package io.worduel.worduelapi.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameManager {
	
	@Autowired
	private RoomManager roomManager;
	
}
