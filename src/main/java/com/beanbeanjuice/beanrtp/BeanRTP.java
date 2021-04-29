package com.beanbeanjuice.beanrtp;

import com.beanbeanjuice.beanrtp.managers.filemanagers.Messages;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class BeanRTP extends JavaPlugin {

    private Messages messages;

    @Override
    public void onEnable() {
        // Setup Teleport Cooldown
        // Setup Teleport Timer
        saveDefaultConfig();
        // Setup WorldSpawns
        messages = new Messages(this);
        getLogger().fine("BeanRTP.jar has been enabled...");
        // Create a new TabCompletor
        // Create a new GeneralHelper
        // create a new RTP Command

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @NotNull
    public Messages getMessages() {
        return messages;
    }
}
