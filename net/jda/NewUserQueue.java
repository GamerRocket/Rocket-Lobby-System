package net.jda;

import java.util.ArrayList;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class NewUserQueue extends Thread {
	private static ArrayList<MessageReceivedEvent> queue = new ArrayList<MessageReceivedEvent>();
	
	public static void sendAction(MessageReceivedEvent event) {
		queue.add(event);
	}
	
	public void run() {
		while(true) {
			if(!queue.isEmpty()) {
				if(!UserBase.doesUserExist( queue.get(0).getAuthor().getId() )) {
					UserBase.addUser(queue.get(0));
					queue.remove(0);
				} else {
					queue.remove(0);
				}
			}
			
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
