package com.desle.build;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.desle.main.Main;

public class BuildLoader {
	
	private static BuildLoader instance;
	public static BuildLoader getInstance() {
		if (instance == null) 
			instance = new BuildLoader();
		
		return instance;
	}
	
	public void loadBuilds() {
		FileConfiguration f = getBuildsFile();
		
		if (f.getConfigurationSection("builds") == null)
			return;
		
		for (String key : f.getConfigurationSection("builds").getKeys(false)) {
			String path = "builds." + key + ".";
			
			boolean canFloat = f.getBoolean(path + "can-float");
			int timeToBuild = f.getInt(path + "time-to-build");
			
			List<BuildBlock> blocks = new ArrayList<BuildBlock>();
			
			for (String rawblock : f.getStringList(path + "blocks")) {
				
				String[] rawblockinfo = rawblock.split(":");
				
				byte blockdata = (byte) Integer.parseInt(rawblockinfo[0]);
				
				String blockmaterialname = rawblockinfo[1].toUpperCase();
				
				Material blockmaterial = Material.valueOf(blockmaterialname);
				
				String rawposition = rawblockinfo[2];
				String[] coords = rawposition.split(",");
				
				int x = Integer.parseInt(coords[0]);
				int y = Integer.parseInt(coords[1]);
				int z = Integer.parseInt(coords[2]);
				
				Location blockposition = new Location(null, x, y, z);
				
				BuildBlock block = new BuildBlock(blockposition, blockmaterial, blockdata);
				
				blocks.add(block);
			}
			
			
			new Build(key, blocks, canFloat, timeToBuild);
		}
	}
	
	public void saveBuild(Build build) {
		FileConfiguration f = getBuildsFile();
		
		String name = build.getName();
		String path = "builds." + name + ".";
		
		f.set(path + "can-float", build.canFloat());
		f.set(path + "time-to-build", build.getTimeToBuild());
		
		List<String> blocks = new ArrayList<String>();
		
		for (BuildBlock block : build.getBuildBlocks()) {
			int data = (int) block.getData();
			String material = block.getMaterial().name();
			
			int x = (int) block.getPosition().getX();
			int y = (int) block.getPosition().getY();
			int z = (int) block.getPosition().getZ();
			
			String position = x + "," + y + "," + z;
			
			blocks.add(data + ":" + material + ":" + position);
		}
		
		f.set(path + "blocks", blocks);
		
		saveBuildsFile();
	}
	
	public void saveBuilds() {
		for (Build build : Build.builds) {
			saveBuild(build);
		}
	}
	
	
	
	/*
	 * 
	 * 
	 *  Builds configuration file
	 * 
	 * 
	 * 
	 * */
	
	
	
	  private FileConfiguration buildsFileConfiguration = null;
	  private File buildsFile = null;
	
	
	  public void reloadBuildsFile()
	  {
	    if (this.buildsFile == null) {
	      this.buildsFile = new File(Main.getMain().getDataFolder(), "builds.yml");
	    }
	    this.buildsFileConfiguration = YamlConfiguration.loadConfiguration(this.buildsFile);

	    InputStream defConfigStream = Main.getMain().getResource("builds.yml");
	    if (defConfigStream != null) {
	      @SuppressWarnings("deprecation")
		YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
	      this.buildsFileConfiguration.setDefaults(defConfig);
	    }
	  }
	  
	  

	  public FileConfiguration getBuildsFile() {
	    if (this.buildsFileConfiguration == null) {
	      reloadBuildsFile();
	    }
	    return this.buildsFileConfiguration;
	  }

	  
	  
	  public void saveBuildsFile() {
	    if ((this.buildsFileConfiguration == null) || (this.buildsFile == null))
	      return;
	    try
	    {
	      getBuildsFile().save(this.buildsFile);
	    } catch (IOException ex) {
	    	Bukkit.getLogger().log(Level.SEVERE, "Could not save config to " + this.buildsFile, ex);
	    }
	  }

	  
	  
	  public void saveDefaultBuildsFile() {
	    if (this.buildsFile == null) {
	      this.buildsFile = new File(Main.getMain().getDataFolder(), "builds.yml");
	    }
	    if (!this.buildsFile.exists())
	    	Main.getMain().saveResource("builds.yml", false);
	  }
}
