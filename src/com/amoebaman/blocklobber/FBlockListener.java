package com.amoebaman.blocklobber;

import java.util.*;
import java.util.logging.Logger;
import org.bukkit.command.*;
import org.bukkit.event.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class FBlockListener extends JavaPlugin {
 
    @Override
    public void onEnable()
    {

        getServer().getPluginManager().registerEvents(new Listener()
        {
 
            @EventHandler(priority = EventPriority.NORMAL)
            public void fallenBlock(EntityChangeBlockEvent event) 
            {
                // insert debug test here
                getLogger().info("Who calls entity a freaking 'what'" + what);
            }
        }, this);
    }
}
