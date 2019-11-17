package net.jda.startup.queue;

import net.jda.UserBase;

import net.jda.rls.Lobby;
import net.dv8tion.jda.core.entities.User;
import net.jda.rls.essentials.GameEntity;

public class GameQueue<GameClass extends GameEntity<?>> extends Thread {
	public String gameName;
	public User player;
	private GameClass gameClassObject;
	
	public GameQueue(GameClass gameObject, String gameName, User user) {
		gameClassObject = gameObject;
		this.gameName = gameName;
		player = user;
	}
	
	public GameClass getGameObject() {
		return gameClassObject;
	}
	
	public void run() {
		if(!UserBase.doesUserExist( player.getId() )) return;
		Lobby.addPlayerProcess(this);
	}
	
}
