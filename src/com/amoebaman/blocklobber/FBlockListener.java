package com.amoebaman.blocklobber;

import java.lang.Object;
import java.util.*;
import java.util.logging.Logger;
import org.bukkit.command.*;
import org.bukkit.event.*;
import org.bukkit.entity.EntityType;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;

public class FBlockListener implements Listener {

    Logger log;
    private final BlockLobber instance;
    public FBlockListener (BlockLobber instance)
    {
	this.instance = instance;
	log = instance.getLogger();
    }
 
	   @EventHandler(priority = EventPriority.NORMAL)
	   public void fallenBlock(EntityChangeBlockEvent event)
	   {
	   	Material To = event.getTo();
		if (event.getEntityType() == EntityType.FALLING_BLOCK)
		{
		  if ((To == Material.SAND) || (To == Material.GRAVEL) || (To == Material.ANVIL))
		  {
		  	if (instance.getConfig().getBoolean("deny-natural"))
		  	{
		  		event.setCancelled(true);
		  	}
		  }
		  else
		  {
		  	if (instance.getConfig().getBoolean("deny-all"))
		  	{
		  		event.setCancelled(true);
		  	}
		  }
		  
		}
		      
	   }
}
