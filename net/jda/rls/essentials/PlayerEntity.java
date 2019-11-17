package net.jda.rls.essentials;

import net.jda.rls.UserEntity;

public abstract class PlayerEntity {
	public UserEntity user;
	public Controller controller;
	
	public PlayerEntity(UserEntity player) {
		user = player;
		controller = new Controller(player.id);
	}

}
