package net.jda.rls;

import net.jda.UserBase;
import net.jda.startup.ControllerPlugin;

public class RAMmanagement extends Thread {
	public void run() {
		while(true) {
			for(UserEntity e:UserBase.getUserList()) {
				if(e.gameStatus()) continue;
				
				ControllerPlugin.removeController(e.id);
			}
			
			Lobby.manageServers();
			
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
	}
}
