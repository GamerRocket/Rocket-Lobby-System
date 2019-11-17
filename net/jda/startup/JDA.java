package net.jda.startup;

import net.jda.*;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class JDA {
	
	public static void runBot(String applicationToken) throws Exception {
		Resource.api = new JDABuilder().setToken(applicationToken).build();
		Resource.api.addEventListener(new NewUserListener());
	}
	
	public static void addEventListener(ListenerAdapter eventListenerObject) {
		Resource.api.addEventListener(eventListenerObject);
	}

}
