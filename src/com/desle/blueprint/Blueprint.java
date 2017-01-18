package com.desle.blueprint;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;

import com.desle.build.Build;

public class Blueprint {
	
	public static List<Blueprint> blueprints = new ArrayList<Blueprint>();
	
	private String name;
	private int range;
	private int cooldown;
	private Build build;
	private ItemStack item;
	
	public Blueprint(String name, int cooldown, int range, Build build, ItemStack item) {
		this.name = name;
		this.cooldown = cooldown;
		this.range = range;
		this.build = build;
		this.item = item;
		
		blueprints.add(this);
	};
	
	public String getName() {
		return this.name;
	}
	
	public int getRange() {
		return this.range;
	}
	
	public int getCooldown() {
		return this.cooldown;
	}
	
	public Build getBuild() {
		return this.build;
	}
	
	public ItemStack getItem() {
		return this.item;
	}
}
