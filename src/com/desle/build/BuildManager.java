package com.desle.build;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.desle.main.Main;

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
	Map<Player, Boolean> canBuild = new HashMap<Player, Boolean>();
	
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
		if (templateLocation.get(player) != null && templateLocation.get(player).distance(location) < 1)
			return;

		Rotation rotation = getRotation(player.getLocation().getYaw());
		
		List<Location> exceptions = new ArrayList<Location>();
		
		for (BuildBlock buildblock : build.getBuildBlocks()) {
			Location locationexception = getBuildLocation(rotation, location, buildblock.getPosition());
			exceptions.add(locationexception);
		}
		
		removeTemplate(player, exceptions);
		
		if (templatePositions.containsKey(player) || templateLocation.containsKey(player))
			return;

		boolean canPlace = canPlace(rotation, location, build.getBuildBlocks());
		

		canBuild.put(player, canPlace);
		
		byte data = 14;
		
		if (canPlace)
			data = 13;
		
		for (BuildBlock buildblock : build.getBuildBlocks()) {
			Location position = buildblock.getPosition();
			
			Location blockLocation = getBuildLocation(rotation, location, position);
			
			Material material = Material.STAINED_GLASS;
			
			if (!replaceableBlocks.contains(blockLocation.getBlock().getType()))
				material = Material.STAINED_CLAY;
			
			player.sendBlockChange(blockLocation, material, data);
			
			if (!templatePositions.containsKey(player))
				templatePositions.put(player, new ArrayList<Location>());
			
			templatePositions.get(player).add(blockLocation);
		}
		
		templateLocation.put(player, location);
	}
	
	public void removeTemplate(Player player, List<Location> exceptions) {
		if (!templatePositions.containsKey(player) || !templateLocation.containsKey(player))
			return;
		
		for (Location location : templatePositions.get(player)) {
			if (exceptions == null || !exceptions.contains(location))
				location.getBlock().getState().update();
		}
		
		templatePositions.remove(player);
		templateLocation.remove(player);
		canBuild.remove(player);
	}
	
	List<Material> replaceableBlocks = new ArrayList<Material>();
	
	public void initReplaceableBlocks() {
		List<String> rawblocks = Main.getMain().getConfig().getStringList("replaceable-blocks");
		
		for (String rawblock : rawblocks) {
			replaceableBlocks.add(Material.valueOf(rawblock.toUpperCase()));
		}
	}
	
	public boolean canPlace(Rotation rotation, Location location, List<BuildBlock> blocks) {
		if (replaceableBlocks.isEmpty())
			initReplaceableBlocks();
		
		for (BuildBlock block : blocks) {
			Location blockLocation = getBuildLocation(rotation, location, block.getPosition());
			
			if (!replaceableBlocks.contains(blockLocation.getBlock().getType()))
				return false;
		}
		
		return true;
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
		
		y = (int) (y + position.getY());
		
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
