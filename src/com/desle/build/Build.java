package com.desle.build;

import java.util.ArrayList;
import java.util.List;

public class Build {
	
	public static List<Build> builds = new ArrayList<Build>();
	
	private int id;
	private List<BuildBlock> blocks;
	private boolean canFloat;
	private int timeToBuild;
	
	
	public Build(int id, List<BuildBlock> blocks, boolean canFloat, int timeToBuild) {
		this.id = id;
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
	
	public int getId() {
		return this.id;
	}
}
