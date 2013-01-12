package com.amoebaman.blocklobber;

import java.util.*;
import java.util.logging.Logger;
import org.bukkit.command.*;
import org.bukkit.event.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;

public class FBlockListener extends JavaPlugin {
 
    @Override
    public void onEnable()
    {

        getServer().getPluginManager().registerEvents(new Listener()
        {
 
            @EventHandler
            public void fallenBlock(EntityChangeBlockEvent event) 
            {
                // insert debug test here
                getLogger().info("Who calls entity a freaking 'what'" + what);
            }
        }, this);
    }
}
