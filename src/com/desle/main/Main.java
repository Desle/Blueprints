package com.desle.main;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.desle.blueprint.BlueprintLoader;
import com.desle.build.Build;
import com.desle.build.BuildBlock;
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
	}
	
	public void onDisable() {
		
	};
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!label.equalsIgnoreCase("testbuild"))
			return false;
		
		if (!(sender instanceof Player))
			return false;
		
		BuildManager bm = BuildManager.getInstance();
		
		Build build = bm.getBuildByName(args[0]);
		Player player = (Player) sender;
		Location location = player.getTargetBlock((Set<Material>) null, 100).getLocation();
		
		bm.showTemplate(player, location, build);
		
		return true;
	}
}
