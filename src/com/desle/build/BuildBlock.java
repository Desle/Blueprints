package com.desle.build;

import org.bukkit.Location;
import org.bukkit.Material;

public class BuildBlock {
	
	private Location position;
	private Material material;
	private byte data;
	
	public BuildBlock(Location position, Material material, byte data) {
		this.position = position;
		this.material = material;
		this.data = data;
	}
	
	public Location getPosition() {
		return this.position;
	}
	
	public byte getData() {
		return this.data;
	}
	
	public Material getMaterial() {
		return this.material;
	}
}
