package com.beanbeanjuice.beanrtp;

import com.beanbeanjuice.beanrtp.helpers.GeneralHelper;
import com.beanbeanjuice.beanrtp.managers.filemanagers.WorldSpawns;
import com.beanbeanjuice.beanrtp.managers.filemanagers.Messages;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class BeanRTP extends JavaPlugin {

    private static WorldSpawns worldSpawns;
    private static Messages messages;
    private static GeneralHelper generalHelper;

    @Override
    public void onEnable() {
        // Setup Teleport Cooldown
        // Setup Teleport Timer
        saveDefaultConfig();
        worldSpawns = new WorldSpawns(this);
        messages = new Messages(this);
        getLogger().fine("BeanRTP.jar has been enabled...");
        // Create a new TabCompletor
        generalHelper = new GeneralHelper(this);
        // create a new RTP Command

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @NotNull
    public static WorldSpawns getWorldSpawns() {
        return worldSpawns;
    }

    @NotNull
    public static Messages getMessages() {
        return messages;
    }

    @NotNull
    public static GeneralHelper getGeneralHelper() {
        return generalHelper;
    }
}
