package fr.niavlys.api;

import org.bukkit.plugin.java.JavaPlugin;

import fr.niavlys.api.db.DatabaseManager;

public class NvApi extends JavaPlugin {
	private static DatabaseManager databaseManager;
	
	public static String name_child = "";
	public static String ver_child = "";
	
	public static Fonctions fonctions;
	
	public static String ver = "v0.2.4";
	
	public void onEnable() {
		fonctions = new Fonctions();
		System.out.println("[NvApi "+ver+"] Launched!");
	}
	
	public void onDisable() {
		System.out.println("[NvApi "+ver+"] Stopped!");
	}
	
	public static DatabaseManager getDatabaseManager() {
		return databaseManager;
	}
	
	public static void setCredentials(String name, String ver, String url, String username, String password, String dbName, Integer port) {
		name_child = name;
		ver_child = ver;
		databaseManager = new DatabaseManager(url, username, password, dbName, port);
	}
	
}
