package fr.niavlys.api;

import org.bukkit.plugin.java.JavaPlugin;

public class NvApi extends JavaPlugin {
	
	public static String name_child = "";
	public static String ver_child = "";
	
	public static Fonctions fonctions;
	
	public static String ver = "v1.1";
	
	public void onEnable() {
		fonctions = new Fonctions();
		System.out.println("[NvApi "+ver+"] Launched!");
	}
	
	public void onDisable() {
		System.out.println("[NvApi "+ver+"] Stopped!");
	}
	
}
