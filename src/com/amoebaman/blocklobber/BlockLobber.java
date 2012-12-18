package com.amoebaman.blocklobber;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class BlockLobber extends JavaPlugin{
	
	private HashMap<String, Presets> presets;
	
	public void onEnable(){
		presets = new HashMap<String, Presets>();
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
		Player player = null;
		if(sender instanceof Player)
			player = (Player) sender;
		
		if(command.getName().equals("lob-block") && player != null)
		{
			Presets values = new Presets(presets.get(player.getName()));
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
		}

		if(command.getName().equals("lob-preset") && player != null)
		{
			Presets values = new Presets(presets.get(player.getName()));
                        if (args.length == 5)
                        {
                            if((args[0].equalsIgnoreCase("pos")) || (args[0].equalsIgnoreCase("position")))
                            {
                                 double x = args[1];
                                 double y = args[2];
                                 double z = args[3];
                                 String world = args[4];
                                 Location location = new Location(world, x, y, z);
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
                                     double x = args[1];
                                     double y = args[2];
                                     double z = args[3];
                                     String world = user.getWorld();
                                     Location location = new Location(world, x, y, z);
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
		}
		
		
		if(command.getName().equals("lob-clear") && player != null)
		{
			Presets values = new Presets(presets.get(player.getName()));
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
						player.sendMessage(ChatColor.DARK_RED + "Error:" + ChatColor.RED + " Invalid preset selected!");
			       			return false;
					}
				    }
				}
			    }
			}
			presets.put(player.getName(), values);
			player.sendMessage(ChatColor.YELLOW + "Block lobbing preset " + ChatColor.GREEN + args[0].toLowerCase() + ChatColor.YELLOW + " updated!");
			return true;
			}
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
