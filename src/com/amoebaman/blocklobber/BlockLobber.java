package com.amoebaman.blocklobber;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Arrow; 
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
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

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
		Player player = null;
		if(sender instanceof Player)
			player = (Player) sender;
		
		if(command.getName().equals("lob-block") && player != null)
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
				if(split.length > 1)
				{
					values.data = Byte.parseByte(split[1]);
				}
			}
			if(args.length > 1)
			{
				values.strength = Byte.parseByte(args[1]);
			}
				
			FallingBlock block = player.getWorld().spawnFallingBlock((values.loc == null ? player.getLocation() : values.loc), values.mat, values.data);
			block.setVelocity((values.dir == null ? player.getLocation().getDirection() : values.dir).clone().multiply(values.strength).multiply(0.2));
			block.setDropItem(player.getGameMode() == GameMode.CREATIVE);
			return true;
		}

		if(command.getName().equals("lob-preset") && player != null)
		{
			Presets values = new Presets(presets.get(player.getName()));
			if (args.length == 0)
			{
			   return false;	
			}
                        if (args.length == 5)
                        {
                            if((args[0].equalsIgnoreCase("pos")) || (args[0].equalsIgnoreCase("position")))
                            {
                                 double x = Double.parseDouble(args[1]);
                                 double y = Double.parseDouble(args[2]);
                                 double z = Double.parseDouble(args[3]);
                                 Location location = new Location(Bukkit.getWorld(args[4]), x, y, z);
                                 values.loc = location;
                                       
                            }
                            else
                            {
                                 player.sendMessage(ChatColor.DARK_RED + "Error:" + ChatColor.RED + " Invalid preset selected!");
			         return false;	
                            }
                        }
                        else
                        {
                             if (args.length == 4)
                             {
                                 if((args[0].equalsIgnoreCase("pos")) || (args[0].equalsIgnoreCase("position")))
                                 {
                                     double x = Double.parseDouble(args[1]);
                                     double y = Double.parseDouble(args[2]);
                                     double z = Double.parseDouble(args[3]);
                                     Location location = new Location(player.getWorld(), x, y, z);
                                     values.loc = location;	
                                 }
                                 else
                                 {
                                     player.sendMessage(ChatColor.DARK_RED + "Error:" + ChatColor.RED + " Invalid preset selected!");
			             return false;
                                 }
                             }
                             else
                             {
				if((args[0].equalsIgnoreCase("pos")) || (args[0].equalsIgnoreCase("position")))
				{
					values.loc = player.getLocation();
				}
				else
				{
				    if((args[0].equalsIgnoreCase("dir")) || (args[0].equalsIgnoreCase("direction")))
				    {
					    values.dir = player.getLocation().getDirection();
				    }
				    else
				    {
				        if((args[0].equalsIgnoreCase("mat")) || (args[0].equalsIgnoreCase("material")))
				        {
					        values.mat = getMat(args[1]);
				        }
				        else
				        {
				            if(args[0].equalsIgnoreCase("data"))
					    {
						    values.data = Byte.parseByte(args[1]);
					    }
					    else
					    {
						if((args[0].equalsIgnoreCase("str")) || (args[0].equalsIgnoreCase("strength")))
			        		{
							values.strength = Byte.parseByte(args[1]);
			        		}
			        		else
			        		{
			        			player.sendMessage(ChatColor.DARK_RED + "Error:" + ChatColor.RED + " Invalid preset selected!");
			        			return false;
			        		}
					    }
				        }
				    }
				}
                            }
                        }
			        
		    presets.put(player.getName(), values);
		    player.sendMessage(ChatColor.YELLOW + "Block lobbing preset " + ChatColor.GREEN + args[0].toLowerCase() + ChatColor.YELLOW + " updated!");
		    return true;
		}
		
		
		if(command.getName().equals("lob-clear") && player != null)
		{
			Presets values = new Presets(presets.get(player.getName()));
			if (args.length == 0)
			{
			   return false;	
			}
			if((args[0].equalsIgnoreCase("pos")) || (args[0].equalsIgnoreCase("position")))
			{
				values.loc = null;
			}
			else
			{
		            if((args[0].equalsIgnoreCase("dir")) || (args[0].equalsIgnoreCase("direction")))
		            {
				    values.dir = null;
			    }
			    else
			    {
				if((args[0].equalsIgnoreCase("mat")) || (args[0].equalsIgnoreCase("material")))
				{
					values.mat = null;
				}
				else
				{
				    if(args[0].equalsIgnoreCase("data"))
				    {
					    values.data = 0;
				    }
				    else
				    {
					if((args[0].equalsIgnoreCase("str")) || (args[0].equalsIgnoreCase("strength")))
					{
						values.strength = 0;
					}
					else
					{
					    if ((args[0].equalsIgnoreCase("all")) || (args[0].equalsIgnoreCase("everything")))
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
					}
				    }
				}
			    }
			}
		    presets.put(player.getName(), values);
		    player.sendMessage(ChatColor.YELLOW + "Block lobbing preset " + ChatColor.GREEN + args[0].toLowerCase() + ChatColor.YELLOW + " cleared!");
		    return true;
		}
		
		
		// ---- PROJECTILE SECTION ----
		if(command.getName().equals("lob-projectile") && player != null)
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
				values.projtype = args[0].toLowerCase();
			}
			//Arrow, Egg, EnderPearl, Fireball, Fish(?), LargeFireball, SmallFireball(?), Snowball, ThrownExpBottle(?), ThrownPotion, WitherSkull
			if (values.projtype.equals("arrow"))
			{
			    Arrow arrow = player.getWorld().spawn(values.projloc, Arrow.class);
			    arrow.setShooter(player);
			}
			else
			{
			    player.sendMessage(ChatColor.DARK_RED + "Error:" + ChatColor.RED + " Invalid projectile selected!");
			}
		}
		
		if(command.getName().equals("lob-projectile-preset") && player != null)
		{
			ProjPresets values = new ProjPresets(projpresets.get(player.getName()));
			if (args.length == 0)
			{
			   return false;	
			}
                        if (args.length == 5)
                        {
                            if((args[0].equalsIgnoreCase("pos")) || (args[0].equalsIgnoreCase("position")))
                            {
                                 double x = Double.parseDouble(args[1]);
                                 double y = Double.parseDouble(args[2]);
                                 double z = Double.parseDouble(args[3]);
                                 Location location = new Location(Bukkit.getWorld(args[4]), x, y, z);
                                 values.projloc = location;
                                       
                            }
                            else
                            {
                                 player.sendMessage(ChatColor.DARK_RED + "Error:" + ChatColor.RED + " Invalid preset selected!");
			         return false;	
                            }
                        }
                        else
                        {
                             if (args.length == 4)
                             {
                                 if((args[0].equalsIgnoreCase("pos")) || (args[0].equalsIgnoreCase("position")))
                                 {
                                     double x = Double.parseDouble(args[1]);
                                     double y = Double.parseDouble(args[2]);
                                     double z = Double.parseDouble(args[3]);
                                     Location location = new Location(player.getWorld(), x, y, z);
                                     values.projloc = location;	
                                 }
                                 else
                                 {
                                     player.sendMessage(ChatColor.DARK_RED + "Error:" + ChatColor.RED + " Invalid preset selected!");
			             return false;
                                 }
                             }
                             else
                             {
				if((args[0].equalsIgnoreCase("pos")) || (args[0].equalsIgnoreCase("position")))
				{
					values.projloc = player.getLocation();
				}
				else
				{
				    if((args[0].equalsIgnoreCase("dir")) || (args[0].equalsIgnoreCase("direction")))
				    {
					    values.projdir = player.getLocation().getDirection();
				    }
				    else
				    {
				        if(args[0].equalsIgnoreCase("type"))
				        {
					        values.projtype = args[1];
				        }
				        else
				        {
					    if((args[0].equalsIgnoreCase("str")) || (args[0].equalsIgnoreCase("strength")))
			        	    {
						values.projstrength = Byte.parseByte(args[1]);
			        	    }
			        	    else
			        	    {
			        		player.sendMessage(ChatColor.DARK_RED + "Error:" + ChatColor.RED + " Invalid preset selected!");
			        		return false;
			        	    }
				        }
				    }
				}
                            }
                        }
			        
		    projpresets.put(player.getName(), values);
		    player.sendMessage(ChatColor.YELLOW + "Projectile lobbing preset " + ChatColor.GREEN + args[0].toLowerCase() + ChatColor.YELLOW + " updated!");
		    return true;
		}
		
		if(command.getName().equals("lob-projectile-clear") && player != null)
		{
			ProjPresets values = new ProjPresets(projpresets.get(player.getName()));
			if (args.length == 0)
			{
			   return false;	
			}
			if((args[0].equalsIgnoreCase("pos")) || (args[0].equalsIgnoreCase("position")))
			{
				values.projloc = null;
			}
			else
			{
		            if((args[0].equalsIgnoreCase("dir")) || (args[0].equalsIgnoreCase("direction")))
		            {
				    values.projdir = null;
			    }
			    else
			    {
				if(args[0].equalsIgnoreCase("type"))
				{
					values.projtype = null;
				}
				else
				{
				    if((args[0].equalsIgnoreCase("str")) || (args[0].equalsIgnoreCase("strength")))
				    {
					values.projstrength = 0;
				    }
				    else
				    {
					if ((args[0].equalsIgnoreCase("all")) || (args[0].equalsIgnoreCase("everything")))
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
				    }
				}
			    }
			}
		    projpresets.put(player.getName(), values);
		    player.sendMessage(ChatColor.YELLOW + "Projectile lobbing preset " + ChatColor.GREEN + args[0].toLowerCase() + ChatColor.YELLOW + " cleared!");
		    return true;
		}
		
		
		// ---- ADMINSTRATION SECTION ----
		if(command.getName().equals("lob-reload"))
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
		if(mat == null)
		{
			try
			{ 
				mat = Material.getMaterial(Integer.parseInt(name));
			}
			catch(NumberFormatException e)
			{	
			}
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
