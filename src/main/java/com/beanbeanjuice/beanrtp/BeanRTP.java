package com.beanbeanjuice.beanrtp;

import org.bukkit.plugin.java.JavaPlugin;

public final class BeanRTP extends JavaPlugin {

    @Override
    public void onEnable() {
        // Setup Teleport Cooldown
        // Setup Teleport Timer
        saveDefaultConfig();
        // Setup WorldSpawns
        // Setup Messages and create the config
        getLogger().fine("BeanRTP.jar has been enabled...");
        // Create a new TabCompletor
        // Create a new GeneralHelper
        // create a new RTP Command

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
