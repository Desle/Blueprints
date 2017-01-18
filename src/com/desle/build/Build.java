package com.desle.build;

import java.util.ArrayList;
import java.util.List;

public class Build {
	
	public static List<Build> builds = new ArrayList<Build>();
	
	private String name;
	private List<BuildBlock> blocks;
	private boolean canFloat;
	private int timeToBuild;
	
	
	public Build(String name, List<BuildBlock> blocks, boolean canFloat, int timeToBuild) {
		this.name = name;
		this.blocks = blocks;
		this.canFloat = canFloat;
		this.timeToBuild = timeToBuild;
		
		builds.add(this);
	};
	
	public List<BuildBlock> getBuildBlocks() {
		return this.blocks;
	}
	
	public boolean canFloat() {
		return this.canFloat;
	}
	
	public int getTimeToBuild() {
		return this.timeToBuild;
	}
	
	public String getName() {
		return this.name;
	}
}
