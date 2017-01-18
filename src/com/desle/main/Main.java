package com.desle.main;

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.desle.blueprint.Blueprint;
import com.desle.blueprint.BlueprintLoader;
import com.desle.build.Build;
import com.desle.build.BuildEvents;
import com.desle.build.BuildLoader;
import com.desle.build.BuildManager;

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
		
		Bukkit.getPluginManager().registerEvents(new BuildEvents(), getMain());
	}
	
	public void onDisable() {
		
	};
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player))
			return false;

		Player player = (Player) sender;
		
		if (label.equalsIgnoreCase("testbuild")) {
			
			BuildManager bm = BuildManager.getInstance();
			
			Build build = bm.getBuildByName(args[0]);
			Location location = player.getTargetBlock((Set<Material>) null, 100).getLocation();
			
			bm.showTemplate(player, location, build);
			
		} else if (label.equalsIgnoreCase("testblueprint")) {
			for (Blueprint blueprint : Blueprint.blueprints) {
				player.getInventory().addItem(blueprint.getItem());
				
			}
		}
		

		return true;
	}
}
