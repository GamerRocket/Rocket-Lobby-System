package net.jda.rls.essentials;

import net.jda.startup.ControllerPlugin;

public class Controller {
	public long id;
	
	public Controller(String id) {
		this.id = Long.parseLong(id);
		clearInput();
	}
	
	public String getInput() {
		return ControllerPlugin.getInput(id);
	}
	
	public void clearInput() {
		ControllerPlugin.controllerInput(id, "");
	}

}
