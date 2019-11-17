package net.jda.startup;

import java.util.Map;
import java.util.HashMap;
import net.jda.startup.queue.InputQueue;

import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class ControllerPlugin extends ListenerAdapter {
	private static Map<Long, String> controllers = new HashMap<Long, String>();
	
	public static void runPlugin() {
		JDA.addEventListener(new ControllerPlugin());
	}
	
	public static void controllerInput(long ID, String input) {
		controllers.put(ID, input);
	}
	
	public static String getInput(long ID) {
		return controllers.get(ID);
	}
	
	public static void removeController(String ID) {
		controllers.remove(Long.parseLong(ID));
	}
	
	public void onMessageReceived(MessageReceivedEvent event) {
		if(event.getChannelType() != ChannelType.PRIVATE) return;
		if(event.getAuthor().isBot()) return;
		
		RLS.sendRequest(new InputQueue(event.getAuthor().getIdLong(), 
									   event.getMessage().getContentRaw(), null));
	}
}
