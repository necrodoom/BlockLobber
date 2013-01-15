package com.amoebaman.blocklobber;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.util.Vector;

public class Presets implements Cloneable{

	public Material mat;
	public byte data, strength;
	public Location loc;
	public Vector dir;
	
	public String projtype;
	public byte projstrength;
	public Location projloc;
	public Vector projdir;
	

	public Presets(Presets other){
		if(other != null){
			mat = other.mat;
			data = other.data;
			strength = other.strength;
			loc = other.loc;
			dir = other.dir;
			
			projtype = other.projtype;
			projstrength = other.projstrength;
			projloc = other.projloc;
			projdir = other.projdir;
		}
	}
	
}
