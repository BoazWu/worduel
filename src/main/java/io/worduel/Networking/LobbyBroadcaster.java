package io.worduel.Networking;

import java.util.LinkedList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import com.vaadin.flow.shared.Registration;

import io.worduel.Actions.Action;
import io.worduel.Actions.LobbyAction;
import io.worduel.Actions.LobbyActionTypes;
import io.worduel.Model.Room;

public class LobbyBroadcaster {
	private Executor executor = Executors.newSingleThreadExecutor();
    private LinkedList<Consumer<Action>> playerList = new LinkedList<>();
    
    private Room room;
    
    public LobbyBroadcaster(Room room) {
    	this.room = room;
    }
    
    public synchronized Registration register(String playerID, Consumer<Action> player) {
    	room.addPlayer(playerID);
    	playerList.add(player);
        
        return () -> {
            synchronized (Room.class) {
            	room.removePlayer(playerID);
            	playerList.remove(player);
            	broadcast(new LobbyAction(playerID, LobbyActionTypes.DISCONNECT));
            }
        };
    }

    public synchronized void broadcast(Action action) {
        for (Consumer<Action> player : playerList) {
            executor.execute(() -> player.accept(action));
        }
    }
}
