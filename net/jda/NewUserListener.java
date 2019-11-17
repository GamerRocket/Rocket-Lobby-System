package net.jda;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class NewUserListener extends ListenerAdapter {
	
	public NewUserListener() {
		(new NewUserQueue()).start();
	}
	
	public void onMessageReceived(MessageReceivedEvent event) {
		if(event.getAuthor().isBot()) return;
		NewUserQueue.sendAction(event);
	}
}
