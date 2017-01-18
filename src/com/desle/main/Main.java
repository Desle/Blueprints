package com.desle.main;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.desle.blueprint.BlueprintLoader;
import com.desle.build.Build;
import com.desle.build.BuildBlock;
import com.desle.build.BuildLoader;

public class Main extends JavaPlugin {
	
	public static Plugin getMain() {
		return Bukkit.getPluginManager().getPlugin("Blueprints");
	}
	
	@Override
	public void onEnable() {
		saveDefaultConfig();
		
		BuildLoader buildloader = BuildLoader.getInstance();
		BlueprintLoader blueprintloader = BlueprintLoader.getInstance();
		
		buildloader.saveDefaultBuildsFile();
		blueprintloader.saveDefaultBlueprintFile();
		
		buildloader.loadBuilds();
		blueprintloader.loadBlueprints();
	}
	
	public void onDisable() {
		
	};
}
