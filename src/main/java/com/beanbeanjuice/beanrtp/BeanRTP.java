package com.beanbeanjuice.beanrtp;

import com.beanbeanjuice.beanrtp.managers.teleportation.TeleportCooldown;
import com.beanbeanjuice.beanrtp.managers.teleportation.TeleportTimer;
import org.bukkit.plugin.java.JavaPlugin;

public final class BeanRTP extends JavaPlugin {

    private static TeleportCooldown teleportCooldown;
    private static TeleportTimer teleportTimer;

    @Override
    public void onEnable() {
        teleportCooldown = new TeleportCooldown();
        teleportTimer = new TeleportTimer();
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

    public static TeleportCooldown getTeleportCooldown() {
        return teleportCooldown;
    }

    public static TeleportTimer getTeleportTimer() {
        return teleportTimer;
    }
}
