package com.desle.build;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class BuildManager {
	
	private static BuildManager instance;
	public static BuildManager getInstance() {
		if (instance == null)
			instance = new BuildManager();
		
		return instance;
	}
	
	
	public Build getBuildByName(String name) {
		for (Build build : Build.builds) {
			if (build.getName().equalsIgnoreCase(name))
				return build;
		}
		
		return null;
	}
	
	//
	public enum Rotation { NORTH, EAST, SOUTH, WEST; }
	
	
	Map<Player, Location> templateLocation = new HashMap<Player, Location>();
	Map<Player, List<Location>> templatePositions = new HashMap<Player, List<Location>>();
	
	@SuppressWarnings("deprecation")
	public void build(Player player, Build build) {
		if (!templateLocation.containsKey(player))
			return;
		
		Location location = templateLocation.get(player);
		
		for (BuildBlock buildblock : build.getBuildBlocks()) {
			byte data = buildblock.getData();
			Material material = buildblock.getMaterial();
			Location position = buildblock.getPosition();
			
			Rotation rotation = getRotation(player.getLocation().getYaw());
			Location blockLocation = getBuildLocation(rotation, location, position);
			
			blockLocation.getBlock().setType(material);
			blockLocation.getBlock().setData(data);
		}
	}
	
	@SuppressWarnings("deprecation")
	public void showTemplate(Player player, Location location, Build build) {
		removeTemplate(player);
		
		if (templatePositions.containsKey(player) || templateLocation.containsKey(player))
			return;
		
		for (BuildBlock buildblock : build.getBuildBlocks()) {
			Location position = buildblock.getPosition();
			
			Rotation rotation = getRotation(player.getLocation().getYaw());
			Location blockLocation = getBuildLocation(rotation, location, position);
			
			player.sendBlockChange(blockLocation, Material.STAINED_GLASS, (byte) 0);
			
			templatePositions.get(player).add(location);
		}
		
		templateLocation.put(player, location);
	}
	
	public void removeTemplate(Player player) {
		for (Location location : templatePositions.get(player)) {
			location.getBlock().getState().update();
		}
		
		templatePositions.remove(player);
		templateLocation.remove(player);
	}
	
	public Location getBuildLocation(Rotation rotation, Location location, Location position) {
		World world = location.getWorld();
		int x = (int) location.getX();
		int y = (int) location.getY();
		int z = (int) location.getZ();
		
		switch (rotation) {
			case NORTH: x += position.getX(); 
						z += position.getZ();
						break;
			case EAST:  x += position.getZ();
						z += position.getX();
						break;
			case SOUTH: x -= position.getX();
						z -= position.getZ();
						break;
			case WEST:  x -= position.getZ();
						z -= position.getX();
						break;
		}
		
		return new Location(world, x, y, z);
	}
	
	public Rotation getRotation(float yaw) {
		if (yaw < 0)
			yaw += 360;
		
		   if (yaw >= 315 || yaw < 45)
			   return Rotation.SOUTH;
		   
		   if (yaw < 135) 
		        return Rotation.WEST;
		   
		    if (yaw < 225) 
		        return Rotation.NORTH;
		    
		    if (yaw < 315) 
		        return Rotation.EAST;
		        
		    return Rotation.NORTH;
	}
}
