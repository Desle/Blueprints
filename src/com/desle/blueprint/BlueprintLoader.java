package com.desle.blueprint;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.desle.build.Build;
import com.desle.build.BuildManager;
import com.desle.main.Main;

public class BlueprintLoader {
	
	private static BlueprintLoader instance;
	public static BlueprintLoader getInstance() {
		if (instance == null) 
			instance = new BlueprintLoader();
		
		return instance;
	}
	
	public void loadBlueprints() {
		FileConfiguration f = getBlueprintFile();
		
		if (f.getConfigurationSection("blueprints") == null)
			return;
		
		for (String key : f.getConfigurationSection("blueprints").getKeys(false)) {
			String path = "blueprints." + key + ".";
			
			String name = key;
			int cooldown = f.getInt(path + "cooldown");
			int range = f.getInt(path + "range");
			String buildname = f.getString(path + "build");
			
			String title = f.getString(path + "item.title");
			List<String> description = f.getStringList(path + "item.description");
			String rawmaterial = f.getString(path + "item.material");
			byte data = (byte) f.getInt(path + "item.data");
			
			Material material = Material.valueOf(rawmaterial);
			
			@SuppressWarnings("deprecation")
			ItemStack item = new ItemStack(material, 1, (short) 0, data);
			ItemMeta itemmeta = item.getItemMeta();
			itemmeta.setLore(description);
			itemmeta.setDisplayName(title);
			item.setItemMeta(itemmeta);
			
			BuildManager bm = BuildManager.getInstance();
			Build build = bm.getBuildByName(buildname);
			
			if (build != null)
				new Blueprint(name, cooldown, range, build, item);
		}
	}
	
	public void saveBlueprint(Blueprint blueprint) {
		FileConfiguration f = getBlueprintFile();
		
		String path = "blueprints." + blueprint.getName().toLowerCase() + ".";
		
		f.set(path + "cooldown", blueprint.getCooldown());
		f.set(path + "range", blueprint.getRange());
		f.set(path + "build", blueprint.getBuild().getName().toLowerCase());
		
		saveBlueprintFile();
	}
	
	public void saveBlueprints() {
		for (Blueprint blueprint : Blueprint.blueprints) {
			saveBlueprint(blueprint);
		}
	}
	
	
	
	/*
	 * 
	 * 
	 *  blueprint configuration file
	 * 
	 * 
	 * 
	 * */
	
	
	
	  private FileConfiguration blueprintFileConfiguration = null;
	  private File blueprintFile = null;
	
	
	  public void reloadBlueprintFile()
	  {
	    if (this.blueprintFile == null) {
	      this.blueprintFile = new File(Main.getMain().getDataFolder(), "blueprints.yml");
	    }
	    this.blueprintFileConfiguration = YamlConfiguration.loadConfiguration(this.blueprintFile);

	    InputStream defConfigStream = Main.getMain().getResource("blueprints.yml");
	      @SuppressWarnings("deprecation")
		YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
	      this.blueprintFileConfiguration.setDefaults(defConfig);
	  }
	  
	  

	  public FileConfiguration getBlueprintFile() {
	    if (this.blueprintFileConfiguration == null) {
	      reloadBlueprintFile();
	    }
	    return this.blueprintFileConfiguration;
	  }

	  
	  
	  public void saveBlueprintFile() {
	    if ((this.blueprintFileConfiguration == null) || (this.blueprintFile == null))
	      return;
	    try
	    {
	      getBlueprintFile().save(this.blueprintFile);
	    } catch (IOException ex) {
	    	Bukkit.getLogger().log(Level.SEVERE, "Could not save config to " + this.blueprintFile, ex);
	    }
	  }

	  
	  
	  public void saveDefaultBlueprintFile() {
	    if (this.blueprintFile == null) {
	      this.blueprintFile = new File(Main.getMain().getDataFolder(), "blueprints.yml");
	    }
	    if (!this.blueprintFile.exists())
	    	Main.getMain().saveResource("blueprints.yml", false);
	  }
}
