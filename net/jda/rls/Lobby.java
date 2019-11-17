package net.jda.rls;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

import net.jda.UserBase;
import net.jda.rls.essentials.*;
import net.jda.startup.queue.GameQueue;

public class Lobby {
	public enum GameStatus {
		PENDING, RUNNING, COMPLETE
	}
	
	private static Map<String, ArrayList< GameEntity<?> >> gameServers =
				 new HashMap<String, ArrayList< GameEntity<?> >>();
	
	public static void addGame(String gameName) {
		gameServers.put(gameName, new ArrayList< GameEntity<?> >());
	}
	
	public static void joinGame(GameQueue<? extends GameEntity<?>> queue) {
		queue.start();
	}
	
	/**This method is called internally. Do not call this method.*/
	public static void addPlayerProcess(GameQueue<? extends GameEntity<?>> self) {
		int result = lobbyIsOpen(self.gameName);
		if(result == -1) {
			gameServers.get(self.gameName).add(self.getGameObject());
			result = lobbyIsOpen(self.gameName);
			gameServers.get(self.gameName).get(result).start();
		}
		
		gameServers.get(self.gameName).get(result).joinGame(UserBase.getUser( self.player.getId() ));
	}
	
	private static int lobbyIsOpen(String gameName) {
		if(gameServers.get(gameName).isEmpty()) return -1;
		
		for(int i=0; i < gameServers.get(gameName).size();i++) {
			if(gameServers.get(gameName).get(i).getGameStatus() == GameStatus.PENDING) 
				return i;
		}
		
		return -1;
	}
	
	/**This is called internally and calling it manually is STRONGLY suggested against*/
	public static void manageServers() {
		for(String key:gameServers.keySet()) {
			if(gameServers.get(key).isEmpty()) continue;
			
			for(int i=0; i < gameServers.get(key).size();i++) {
				if(gameServers.get(key).get(i).getGameStatus() == GameStatus.COMPLETE) {
					gameServers.get(key).remove(i);
					i--;
				}
			}
		}
	}
	
}
