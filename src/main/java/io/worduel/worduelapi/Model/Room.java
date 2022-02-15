package io.worduel.worduelapi.Model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import com.vaadin.flow.shared.Registration;

import io.worduel.worduelapi.Networking.Broadcaster;

public class Room {
	private String roomCode;
	private Player host;
	
	static Executor executor = Executors.newSingleThreadExecutor();

    static LinkedList<Consumer<String>> playerList = new LinkedList<>();

    public Room(String roomCode) {
    	this.roomCode = roomCode;
    }
    
    public static synchronized Registration register(Consumer<String> player) {
        playerList.add(player);

        return () -> {
            synchronized (Broadcaster.class) {
                playerList.remove(player);
            }
        };
    }

    public static synchronized void broadcast(String message) {
        for (Consumer<String> player : playerList) {
            executor.execute(() -> player.accept(message));
        }
    }
}
