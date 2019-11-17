package net.jda.startup.queue;

import java.util.ArrayList;

import net.jda.UserBase;
import net.jda.rls.UserEntity;
import net.jda.startup.ControllerPlugin;

public class InputQueue extends Thread {
	public ArrayList<InputQueue> queue;
	
	public long id;
	public String input;
	
	public InputQueue(long id, String input, ArrayList<InputQueue> nullOrQueue) {
		this.id = id;
		this.input = input;
		queue = nullOrQueue;
	}
	
	public void execute() {
		UserEntity user = UserBase.getUser(id);
		if(user == null) return;
		if(!user.gameStatus()) return;
		
		ControllerPlugin.controllerInput(id, input);
	}
	
	public void runQueue() {
		if(queue.isEmpty()) return;
		
		queue.get(0).execute();
		queue.remove(0);
	}
	
	public void run() {
		while(true) {
			runQueue();
			
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
