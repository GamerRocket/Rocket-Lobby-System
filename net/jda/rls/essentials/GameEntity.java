package net.jda.rls.essentials;

import net.jda.UserBase;
import net.jda.startup.RLS;
import net.jda.startup.queue.MessageQueue;

import net.jda.rls.UserEntity;
import net.jda.rls.Lobby.GameStatus;

import java.io.File;
import java.util.ArrayList;
import net.dv8tion.jda.core.MessageBuilder;

public abstract class GameEntity<PlayerClass extends PlayerEntity> extends Thread {
	public ArrayList<PlayerClass> players;
	public GameStatus status;
	
	private ArrayList<Integer> allPlayersWhoJoinedIndexs = new ArrayList<Integer>();
	
	public GameEntity() {
		players = new ArrayList<PlayerClass>();
		status = GameStatus.PENDING;
	}
	
	public GameStatus getGameStatus() {
		return status;
	}
	
	public void joinGame(UserEntity newPlayer) {
		if(newPlayer.gameStatus()) 
		{
			newPlayer.channel.sendFile(new File("Lobby Manual.txt"), "Lobby System Manual & FAQ.txt").queue();
			return;
		}
		
		allPlayersWhoJoinedIndexs.add(newPlayer.getIndex());
		players.add(getNewPlayer(newPlayer));
		
		UserBase.changeGameStatusOnUser(newPlayer.getIndex(), true);
		RLS.sendRequest(new MessageQueue(newPlayer,
						getMessageStyle().append("You have successfully Joined a Game!"
									+ " Waiting for other players... \n\n"
									+ "While you wait, when the game starts you should know that "
									+ "typing in report if a player is afk can end a dead game. "
									+ "That way you can all leave without getting a cooldown!").build(), null));
	}
	
	public void run() {
		while(!isLobbyAfkTimerCompleted() && status == GameStatus.PENDING) {
			if(players.size() == maxPlayers()) {
				status = GameStatus.RUNNING;
				break;
			}
			
			localSleep((short) 5);
		}
		
		for(PlayerClass e:players) e.controller.clearInput();
		
		while(status == GameStatus.RUNNING) {
			process();
			afkReportHandler();
			localSleep((short) 30);
		}
		
		status = GameStatus.COMPLETE;
		for(int e:allPlayersWhoJoinedIndexs) UserBase.changeGameStatusOnUser(e, false);
	}
	
	/**This is your game loop. All your game mechanics are handled within this method.
	 * 
	 * @note The Game thread sleeps for 30 milliseconds every loop. This is done to try and "simulate" 
	 * a single frame in a traditional GUI Video Game that runs at 60 fps. This will save RAM 
	 * resources, and give you a better idea on how many times a second this code is executed.
	 */
	protected abstract void process();
	
	/**In case you like to add a unique style/spin to your messages*/
	protected abstract MessageBuilder getMessageStyle();
	
	/**
	 * This method exists in case you want to add a timer to a lobbys lifespan. This way any dead
	 *  lobbies don't run forever. This both saves RAM usage and keeps players sanity in tact.
	 *  
	 * @return The result of any custom timers you make. Otherwise, just make this return false;
	 */
	protected abstract boolean isLobbyAfkTimerCompleted();
	
	public abstract int maxPlayers();
	
	/**
	 * Long name I know. But this is important in keeping track of reports. 
	 * If this amount of people have reported someone for being afk, the game will auto-end.
	 * 
	 * @note Set this to return 0 or less than 0 if you don't want the game to ever auto-end 
	 * (not recommended for player sanity)
	 */
	protected abstract int afkReportGameEndThreshold();
	
	/**
	 * This exists as a way to get around the pain that is Java Generics.
	 * 
	 * @param user This is already taken care of internally, just know it exists and you use it.
	 * @return You will be returning a new object of the class you are using for players. 
	 * like new TicTacToePlayer(user);
	 */
	protected abstract PlayerClass getNewPlayer(UserEntity user);
	
	private void afkReportHandler() {
		if(afkReportGameEndThreshold() <= 0) return;
		
		int reportCounter = 0;
		for(PlayerClass e:players)
			if(e.controller.getInput().equalsIgnoreCase("report")) reportCounter++;
		
		if(reportCounter >= afkReportGameEndThreshold()) {
			status = GameStatus.COMPLETE;
			for(PlayerClass e:players)
				RLS.sendRequest(new MessageQueue(e.user,
								getMessageStyle().append("The Game Ended due to afk reports.").build(), null));
		}
	}
	
	private void localSleep(short duration) {
		try {
			Thread.sleep(duration);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
