package net.jda.startup.queue;

import java.util.ArrayList;

import net.jda.rls.UserEntity;
import net.dv8tion.jda.core.entities.Message;

public class MessageQueue extends Thread {
	public ArrayList<MessageQueue> queue;

	public UserEntity receive;
	public Message message;
	
	public MessageQueue(UserEntity receiver, Message message, ArrayList<MessageQueue> nullOrQueue) {
		receive = receiver;
		this.message = message;
		queue = nullOrQueue;
	}
	
	public void execute() {
		receive.channel.sendMessage(message).queue();
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
