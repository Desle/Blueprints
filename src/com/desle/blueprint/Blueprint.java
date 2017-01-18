package com.desle.blueprint;

import java.util.ArrayList;
import java.util.List;

import com.desle.build.Build;

public class Blueprint {
	
	public static List<Blueprint> blueprints = new ArrayList<Blueprint>();
	
	private String name;
	private int range;
	private int cooldown;
	private Build build;
	
	public Blueprint(String name, int cooldown, int range, Build build) {
		this.name = name;
		this.cooldown = cooldown;
		this.range = range;
		this.build = build;
		
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
}
