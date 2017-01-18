package com.desle.main;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	public static Plugin getMain() {
		return Bukkit.getPluginManager().getPlugin("Blueprints");
	}
	
	@Override
	public void onEnable() {
		
	}
	
	public void onDisable() {
		
	};
}
