package com.desle.build;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

import com.desle.blueprint.Blueprint;
import com.desle.blueprint.BlueprintManager;

public class BuildEvents implements Listener {
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		Player player = e.getPlayer();
		
		ItemStack item = e.getItemDrop().getItemStack();
		
		BlueprintManager blueprintmanager = BlueprintManager.getInstance();
		BuildManager buildmanager = BuildManager.getInstance();
		
		Blueprint blueprint = blueprintmanager.getBlueprintByItem(item);
		
		if (blueprint == null) {
			buildmanager.removeTemplate(player, new ArrayList<Location>());
			return;
		}
		
		if (!buildmanager.templatePositions.containsKey(player))
			return;
		
		buildmanager.build(player, blueprint.getBuild());
		
		e.getItemDrop().setItemStack(new ItemStack(Material.AIR));
	}
	
	@EventHandler
	public void MoveEvent(Player e) {
		Player player = e.getPlayer();
		
		Block block = player.getTargetBlock((Set<Material>) null, 100);
		
		BuildManager buildmanager = BuildManager.getInstance();
		
		@SuppressWarnings("deprecation")
		ItemStack item = player.getItemInHand();
		
		BlueprintManager blueprintmanager = BlueprintManager.getInstance();
		
		Blueprint blueprint = blueprintmanager.getBlueprintByItem(item);
		
		if (blueprint == null) {
			if (buildmanager.templatePositions.containsKey(player))
				buildmanager.removeTemplate(player, new ArrayList<Location>());
			return;
		}
		
		if (block == null) return;
		
		Location location = block.getLocation();
		
		if (location.distance(player.getEyeLocation()) > blueprint.getRange()) {
			buildmanager.removeTemplate(player, null);
			return;
		}
		
		buildmanager.showTemplate(player, location, blueprint.getBuild());
	}
}
