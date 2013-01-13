package com.amoebaman.blocklobber;

import java.lang.Object;
import java.util.*;
import java.util.logging.Logger;
import org.bukkit.command.*;
import org.bukkit.event.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;

public class FBlockListener implements Listener {
 
    @EventHandler(priority = EventPriority.NORMAL)
    public void fallenBlock(EntityChangeBlockEvent event) 
    {
        // insert debug test here
        getLogger().info("Who calls entity a freaking 'what'" + event.getEntityType().toString());
    }
}
