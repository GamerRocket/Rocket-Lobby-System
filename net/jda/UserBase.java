package net.jda;

import java.util.ArrayList;
import net.jda.rls.UserEntity;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class UserBase {
	private static ArrayList<UserEntity> users = new ArrayList<UserEntity>();
	
	public static void addUser(MessageReceivedEvent event) {
		users.add(new UserEntity( event, users.size() ));
	}
	
	public static ArrayList<UserEntity> getUserList() {
		return users;
	}
	
	public static boolean doesUserExist(String id) {
		for(UserEntity e:users)
			if(e.id.equals(id)) return true;
		
		return false;
	}
	
	public static UserEntity getUser(String id) {
		for(UserEntity e:users) {
			if(e.id.equals(id)) return e;
		}
		
		return null;
	}
	
	public static UserEntity getUser(long id) {
		for(UserEntity e:users) {
			if(e.id.equals( String.format("%d", id) )) return e;
		}
		
		return null;
	}
	
	public static void changeGameStatusOnUser(int index, boolean isInGame) {
		users.get(index).setStatusTo(isInGame);
	}
}
