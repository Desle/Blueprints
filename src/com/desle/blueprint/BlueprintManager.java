package com.desle.blueprint;

import org.bukkit.inventory.ItemStack;

public class BlueprintManager {
	
	private static BlueprintManager instance;
	public static BlueprintManager getInstance() {
		if (instance == null)
			instance = new BlueprintManager();
		
		return instance;
	}
	
	public Blueprint getBlueprintByItem(ItemStack item) {
		for (Blueprint blueprint : Blueprint.blueprints) {
			if (item.isSimilar(blueprint.getItem()))
				return blueprint;
		}
		
		return null;
	}
}
