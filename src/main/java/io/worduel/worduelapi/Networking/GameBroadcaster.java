package io.worduel.worduelapi.Networking;

import java.util.LinkedList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import com.vaadin.flow.shared.Registration;

import io.worduel.Actions.Action;
import io.worduel.Actions.LobbyAction;
import io.worduel.Actions.LobbyActionTypes;
import io.worduel.worduelapi.Model.Room;

public class GameBroadcaster {
	private Executor executor = Executors.newSingleThreadExecutor();
    private LinkedList<Consumer<Action>> playerList = new LinkedList<>();
    
    private Room room;
    
    public GameBroadcaster(Room room) {
    	this.room = room;
    }
    
    public synchronized Registration register(String playerID, Consumer<Action> player) {
    	playerList.add(player);
        
        return () -> {
            synchronized (Room.class) {
            	playerList.remove(player);
            }
        };
    }

    public synchronized void broadcast(Action action) {
        for (Consumer<Action> player : playerList) {
            executor.execute(() -> player.accept(action));
        }
    }
}
