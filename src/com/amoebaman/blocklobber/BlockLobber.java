package com.amoebaman.blocklobber;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.util.Vector;
import org.bukkit.plugin.java.JavaPlugin;

public class BlockLobber extends JavaPlugin{
	
	private HashMap<String, Presets> presets;
	private HashMap<String, ProjPresets> projpresets;
	
	public void onEnable(){
		presets = new HashMap<String, Presets>();
		projpresets = new HashMap<String, ProjPresets>();
		this.saveDefaultConfig();
		getServer().getPluginManager().registerEvents(new FBlockListener(this), this);
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		String args0 = "";
		if (args.length > 0)
		{
		    args0 = args[0].toLowerCase();
		}
		
		Player player = null;
		if(sender instanceof Player)
		{
			player = (Player) sender;
		}
		
		if(command.getName().equals("lob") && player != null)
		{
			Presets values = new Presets(presets.get(player.getName()));
			if(args.length == 0)
			{
				if (values.mat == null || values.strength == 0)
				{
					return false;
				}
			}
			
			if(args.length > 0)
			{
				String[] split = args[0].split(":");
				values.mat = getMat(split[0]);
				if (values.mat == null || !values.mat.isBlock())
				{
					player.sendMessage(ChatColor.DARK_RED + "Error:" + ChatColor.RED + " Invalid Material selected!");
					return true;
				}
				
				if(split.length > 1)
				{
					try
					{
						values.data = Byte.parseByte(split[1]);
					}
					catch(Exception e)
					{
						player.sendMessage(ChatColor.DARK_RED + "Error:" + ChatColor.RED + " Invalid value inserted!");
						return true;
					}
				}
			}
			if(args.length > 1)
			{
				try
				{
					values.strength = Byte.parseByte(args[1]);
				}
				catch(Exception e)
				{
					player.sendMessage(ChatColor.DARK_RED + "Error:" + ChatColor.RED + " Invalid value inserted!");
					return true;
				}
			}
			
			if (values.mat == null)
			{
				player.sendMessage(ChatColor.DARK_RED + "Error:" + ChatColor.RED + " Invalid Material selected!");
				return true;
			}
			
			if(values.loc == null)
			{
				values.loc = player.getLocation();
			}
			
			if(values.dir == null)
			{
				values.dir = player.getLocation().getDirection();
			}
			
			double speed = values.strength / 5.0;
			
			try
			{
				final Vector direction = values.dir.multiply(speed);
				FallingBlock block = values.loc.getWorld().spawnFallingBlock(values.loc, values.mat, values.data);
				block.setVelocity(direction);
				block.setDropItem(player.getGameMode() == GameMode.CREATIVE);
				return true;
			}
			catch(Exception e)
			{
				player.sendMessage(ChatColor.DARK_RED + "Error:" + ChatColor.RED + " Falling Block spawn failed, recheck your input");
				return false;
			}
		}

		if(command.getName().equals("lobs") && player != null)
		{
			Presets values = new Presets(presets.get(player.getName()));
			if (args.length == 0)
			{
			   	return false;	
			}
			
                        if (args.length == 5 && args0.equals("pos") || args0.equals("position"))
                        {
                            	double x = Double.parseDouble(args[1]);
                            	double y = Double.parseDouble(args[2]);
                             	double z = Double.parseDouble(args[3]);
                             	Location location = new Location(Bukkit.getWorld(args[4]), x, y, z);
                             	values.loc = location;
                        }
                        
                        else if (args.length == 4 && args0.equals("pos") || args0.equals("position"))
                        {
                            	double x = Double.parseDouble(args[1]);
                            	double y = Double.parseDouble(args[2]);
                            	double z = Double.parseDouble(args[3]);
                           	Location location = new Location(player.getWorld(), x, y, z);
                            	values.loc = location;
                        }
                        
                        else if (args0.equals("pos") || args0.equals("position"))
			{
				values.loc = player.getLocation();
			}
			
			else if(args0.equals("dir") || args0.equals("direction"))
			{
				values.dir = player.getLocation().getDirection();
			}
			
			else if(args0.equals("mat") || args0.equals("material"))
			{
				values.mat = getMat(args[1]);
				if (values.mat == null || !values.mat.isBlock())
				{
					player.sendMessage(ChatColor.DARK_RED + "Error:" + ChatColor.RED + " Invalid Material selected!");
				}
			}
			
			else if(args0.equals("data"))
			{
				try
				{
					values.data = Byte.parseByte(args[1]);
				}
				catch(Exception e)
				{
					player.sendMessage(ChatColor.DARK_RED + "Error:" + ChatColor.RED + " Invalid value inserted!");
				}
			}
			
			else if(args0.equals("str") || args0.equals("strength"))
			{
				try
				{
					values.strength = Byte.parseByte(args[1]);
				}
				catch(Exception e)
				{
					player.sendMessage(ChatColor.DARK_RED + "Error:" + ChatColor.RED + " Invalid value inserted!");
				}
			}
			
			else
			{
				player.sendMessage(ChatColor.DARK_RED + "Error:" + ChatColor.RED + " Invalid preset selected!");
			        return false;
			}
			        
		    presets.put(player.getName(), values);
		    player.sendMessage(ChatColor.YELLOW + "Block lobbing preset " + ChatColor.GREEN + args[0].toLowerCase() + ChatColor.YELLOW + " updated!");
		    return true;
		}
		
		
		if(command.getName().equals("lobc") && player != null)
		{
			Presets values = new Presets(presets.get(player.getName()));
			if (args.length == 0)
			{
			   return false;	
			}
			
			if(args0.equals("pos") || args0.equals("position"))
			{
				values.loc = null;
			}
			
			else if(args0.equals("dir") || args0.equals("direction"))
		        {
				values.dir = null;
			}
			
			else if(args0.equals("mat") || args0.equals("material"))
			{
				values.mat = null;
			}
			
			else if(args0.equals("data"))
			{
				values.data = 0;
			}
			
			else if(args0.equals("str") || args0.equals("strength"))
			{
				values.strength = 0;
			}
			
			else if (args0.equals("all") || args0.equals("everything") || args0.equals("*"))
			{
				values.loc = null;
				values.dir = null;
				values.mat = null;
				values.data = 0;
				values.strength = 0;
			}
			
			else
			{
				player.sendMessage(ChatColor.DARK_RED + "Error:" + ChatColor.RED + " Invalid preset selected!");
			       	return false;
			}
			
		    presets.put(player.getName(), values);
		    player.sendMessage(ChatColor.YELLOW + "Block lobbing preset " + ChatColor.GREEN + args0 + ChatColor.YELLOW + " cleared!");
		    return true;
		}
		
		
		// ---- PROJECTILE SECTION ----
		if(command.getName().equals("lobp") && player != null)
		{
		  	ProjPresets values = new ProjPresets(projpresets.get(player.getName()));
			if(args.length == 0)
			{
				if (values.projtype == null)
				{
					return false;
				}
			}
			
			if(args.length > 0)
			{
				values.projtype = args0;
			}
			
			if (args.length > 1)
			{
				try
				{
					values.projstrength = Byte.parseByte(args[1]);
				}
				catch(Exception e)
				{
					player.sendMessage(ChatColor.DARK_RED + "Error:" + ChatColor.RED + " Invalid value inserted!");
				}
			}
			
			if (values.projloc == null)
			{
				values.projloc = player.getLocation().add(0, 1, 0);
			}
			
			if (values.projdir == null)
			{
				values.projdir = player.getEyeLocation().getDirection();
			}
			
		        Class<? extends Entity> type;
		        Projectile projectile;
		        double speed = (values.projstrength / 5.0);
		        
			if (values.projtype.equals("fireball") || values.projtype.equals("smallfireball"))
			{
				type = SmallFireball.class;
			}
			
			else if(values.projtype.equals("largefireball") || values.projtype.equals("bigfireball"))
			{
				type = LargeFireball.class;
			}
			
			else if (values.projtype.equals("arrow"))
			{
				type = Arrow.class;
			}
			
			else if (values.projtype.equals("skull"))
			{
				type = WitherSkull.class;
			}
			
			else if (values.projtype.equals("egg"))
			{
				type = Egg.class;
			}
			
			else if(values.projtype.equals("snowball"))
			{
				type = Snowball.class;
			}
			
			else if(values.projtype.equals("expbottle") || values.projtype.equals("xpbottle"))
			{
				type = ThrownExpBottle.class;
			}
			
			else
			{
				player.sendMessage(ChatColor.DARK_RED + "Error:" + ChatColor.RED + " Invalid projectile type selected!");
				player.sendMessage(ChatColor.YELLOW + "Allowed projectiles:" + ChatColor.GOLD + " arrow, skull, egg, snowball, expbottle, fireball, largefireball");
				return true;
			}
			
			try
			{
				final Vector direction = values.projdir.multiply(speed);
				projectile = (Projectile)values.projloc.getWorld().spawn(values.projloc.add(direction.getX(), direction.getY(), direction.getZ()), type);
				projectile.setShooter(player);
				projectile.setVelocity(direction);
				return true;
			}
			catch(Exception e)
			{
				player.sendMessage(ChatColor.DARK_RED + "Error:" + ChatColor.RED + " Projectile spawn failed, recheck your input");
				return false;
			}
			
		}
		
		if(command.getName().equals("lobps") && player != null)
		{
			ProjPresets values = new ProjPresets(projpresets.get(player.getName()));
			if (args.length == 0)
			{
			   	return false;	
			}
			
                        if (args.length == 5 && args0.equals("pos") || args0.equals("position"))
                        {
                                 double x = Double.parseDouble(args[1]);
                                 double y = Double.parseDouble(args[2]);
                                 double z = Double.parseDouble(args[3]);
                                 Location location = new Location(Bukkit.getWorld(args[4]), x, y, z);
                                 values.projloc = location;
                        }
                        
                        else if (args.length == 4 && args0.equals("pos") || args0.equals("position"))
                        {
                                 double x = Double.parseDouble(args[1]);
                                 double y = Double.parseDouble(args[2]);
                                 double z = Double.parseDouble(args[3]);
                                 Location location = new Location(player.getWorld(), x, y, z);
                                 values.projloc = location;	
                        }
                        
                        else if(args0.equals("pos") || args0.equals("position"))
			{
				values.projloc = player.getLocation();
			}
			
			else if(args0.equals("dir") || args0.equals("direction"))
			{
				values.projdir = player.getLocation().getDirection();
			}
			
			else if(args0.equals("type"))
			{
				values.projtype = args[1].toLowerCase();
			}
			
			else if(args0.equals("str") || args0.equals("strength"))
			{
				try
				{
					values.projstrength = Byte.parseByte(args[1]);
				}
				catch(Exception e)
				{
					player.sendMessage(ChatColor.DARK_RED + "Error:" + ChatColor.RED + " Invalid value inserted!");
				}
			}
			
			else
			{
			        player.sendMessage(ChatColor.DARK_RED + "Error:" + ChatColor.RED + " Invalid preset selected!");
			        return false;
			}
			        
		    projpresets.put(player.getName(), values);
		    player.sendMessage(ChatColor.YELLOW + "Projectile lobbing preset " + ChatColor.GREEN + args[0].toLowerCase() + ChatColor.YELLOW + " updated!");
		    return true;
		}
		
		if(command.getName().equals("lobpc") && player != null)
		{
			ProjPresets values = new ProjPresets(projpresets.get(player.getName()));
			if (args.length == 0)
			{
			   return false;	
			}
			
			if(args0.equals("pos") || args0.equals("position"))
			{
				values.projloc = null;
			}
			
			else if(args0.equals("dir") || args0.equals("direction"))
		        {
				values.projdir = null;
			}
			
			else if(args0.equals("type"))
			{
				values.projtype = null;
			}
			
			else if(args0.equals("str") || args0.equals("strength"))
			{
				values.projstrength = 0;
			}
			
			else if (args0.equals("all") || args0.equals("everything") || args0.equals("*"))
			{
				values.projloc = null;
				values.projdir = null;
				values.projtype = null;
				values.projstrength = 0;
			}
			else
			{
				player.sendMessage(ChatColor.DARK_RED + "Error:" + ChatColor.RED + " Invalid preset selected!");
			       	return false;
			}

		    projpresets.put(player.getName(), values);
		    player.sendMessage(ChatColor.YELLOW + "Projectile lobbing preset " + ChatColor.GREEN + args0 + ChatColor.YELLOW + " cleared!");
		    return true;
		}
		
		
		// ---- MISC SECTION ----
		if(command.getName().equals("lobsv") && player != null)
		{
		    Presets values = new Presets(presets.get(player.getName()));
		    ProjPresets projvalues = new ProjPresets(projpresets.get(player.getName()));
		    if(player.hasPermission("blocklobber.preset.view.block"))
		    {
		    		player.sendMessage(ChatColor.YELLOW + "Block Presets:");
		    	if(values.loc == null)
		    	{
		    		player.sendMessage(ChatColor.YELLOW + "Location: " + ChatColor.RED + "Unset");
		    	}
		    	else
		    	{
		    		player.sendMessage(ChatColor.YELLOW + "Location: " + ChatColor.GREEN + (int)(values.loc.getX()) + ", " + (int)(values.loc.getY()) + ", " + (int)(values.loc.getZ()) + ChatColor.YELLOW + " At world: " + ChatColor.GREEN + values.loc.getWorld().getName());
		    	}
		   	if(values.dir == null)
		    	{
		    		player.sendMessage(ChatColor.YELLOW + "Direction: " + ChatColor.RED + "Unset");
		    	}
		    	else
		    	{
		    		player.sendMessage(ChatColor.YELLOW + "Direction: " + ChatColor.GREEN + values.dir.getBlockX() + ", " + values.dir.getBlockY() + ", " + values.dir.getBlockZ());
		    	}
	    	    	if(values.mat == null)
	    	    	{
	    	    		player.sendMessage(ChatColor.YELLOW + "Material: " + ChatColor.RED + "Unset");
	    	    	}
	    	   	else
	    	   	{
	    	   	 	player.sendMessage(ChatColor.YELLOW + "Material: " + ChatColor.GREEN + values.mat.toString().toLowerCase());
	    	   	}
		    	player.sendMessage(ChatColor.YELLOW + "Damage value: " + ChatColor.GREEN + values.data);
		    	player.sendMessage(ChatColor.YELLOW + "Strength: " + ChatColor.GREEN + values.strength);
		    }
		    
		    if(player.hasPermission("blocklobber.preset.view.block") && player.hasPermission("blocklobber.preset.view.projectile"))
		    {
		    	player.sendMessage("");
		    }
		    
		    if(player.hasPermission("blocklobber.preset.view.projectile"))
		    {
		    	player.sendMessage(ChatColor.YELLOW + "Projectile Presets:");
		    	if(projvalues.projloc == null)
		    	{
		    		player.sendMessage(ChatColor.YELLOW + "Location: " + ChatColor.RED + "Unset");
		    	}
		    	else
		    	{
		    		player.sendMessage(ChatColor.YELLOW + "Location: " + ChatColor.GREEN + (int)(projvalues.projloc.getX()) + ", " + (int)(projvalues.projloc.getY()) + ", " + (int)(projvalues.projloc.getZ()) + ChatColor.YELLOW + " At world: " + ChatColor.GREEN + projvalues.projloc.getWorld().getName());
		    	}
		    	if(projvalues.projdir == null)
		    	{
		    		player.sendMessage(ChatColor.YELLOW + "Direction: " + ChatColor.RED + "Unset");
		    	}
		    	else
		    	{
		    		player.sendMessage(ChatColor.YELLOW + "Direction: " + ChatColor.GREEN + projvalues.projdir.getBlockX() + ", " + projvalues.projdir.getBlockY() + ", " + projvalues.projdir.getBlockZ());
		    	}
	    	    	if(projvalues.projtype == null)
	    	   	{
	    	   	 	player.sendMessage(ChatColor.YELLOW + "Type: " + ChatColor.RED + "Unset");
	    	    	}
	    	    	else
	    	    	{
	    	    		player.sendMessage(ChatColor.YELLOW + "Type: " + ChatColor.GREEN + projvalues.projtype.toLowerCase());
	    	    	}
		    	player.sendMessage(ChatColor.YELLOW + "Strength: " + ChatColor.GREEN + projvalues.projstrength);
		    }
		}
		
		
		// ---- ADMINSTRATION SECTION ----
		if(command.getName().equals("lobr"))
		{
		    this.reloadConfig();
		    return true;
		}
		return true;
	}


	public static Material getMat(String name)
	{
		name = name.toLowerCase();
		Material mat = null;
		try
		{ 
			mat = Material.getMaterial(Integer.parseInt(name));
		}
		catch(NumberFormatException e)
		{	
		}
		
		if(mat == null)
		{
			mat = Material.matchMaterial(name);
		}
		
		if((name.contains("spawn") || name.contains("mob")) && name.contains("egg"))
		{
			mat = Material.MONSTER_EGG;
		}
		return mat;
	}

}
