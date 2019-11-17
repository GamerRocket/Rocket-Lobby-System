package net.jda.startup;

import java.util.ArrayList;
import net.jda.startup.queue.*;
import net.jda.rls.RAMmanagement;

public class RLS extends Thread {
	
	private static short revolver = 0;
	private static MessageQueue queues[] = 
		{new MessageQueue(null,null, new ArrayList<MessageQueue>()), 
		 new MessageQueue(null,null, new ArrayList<MessageQueue>()), 
		 new MessageQueue(null,null, new ArrayList<MessageQueue>())};
	
	private static InputQueue inputQueue = new InputQueue(0L, null, new ArrayList<InputQueue>());
	

	public static void startLobbySystem() {
		(new RAMmanagement()).start();
		
		for(MessageQueue e:queues) e.start();
		inputQueue.start();
	}
	
	public static void sendRequest(MessageQueue request) {
		if(request == null) return;
		if(++revolver == queues.length) revolver = 0;
		
		queues[revolver].queue.add(request);
	}
	
	public static void sendRequest(InputQueue request) {
		if(request == null) return;
		
		inputQueue.queue.add(request);
	}

}
