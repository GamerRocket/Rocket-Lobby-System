package net.jda.rls;

import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class UserEntity extends Thread {
	public MessageChannel channel;
	public String id;
	
	private int userIndex;
	private User user;
	private boolean isInGame = false;
	
	public UserEntity(MessageReceivedEvent event, int index) {
		channel = event.getChannel();
		user = event.getAuthor();
		id = user.getId();
		userIndex = index;
		start();
	}
	
	public void run() {
		channel = user.openPrivateChannel().complete();
	}
	
	public int getIndex() {
		return userIndex;
	}
	
	public boolean gameStatus() {
		return isInGame;
	}
	
	/**This is called internally. You normally should not have to call this method*/
	public void setStatusTo(boolean isInGame) {
		this.isInGame = isInGame;
	}
}
