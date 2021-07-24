package com.beanbeanjuice.beanrtp;

import com.beanbeanjuice.beanrtp.commands.RandomTeleportCommand;
import com.beanbeanjuice.beanrtp.managers.commands.CommandHandler;
import com.beanbeanjuice.beanrtp.helpers.GeneralHelper;
import com.beanbeanjuice.beanrtp.managers.filemanagers.Messages;
import com.beanbeanjuice.beanrtp.managers.filemanagers.WorldSpawns;
import com.beanbeanjuice.beanrtp.managers.teleportation.TeleportCooldown;
import com.beanbeanjuice.beanrtp.managers.teleportation.TeleportManager;
import com.beanbeanjuice.beanrtp.managers.teleportation.TeleportTimer;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class BeanRTP extends JavaPlugin {

    private static GeneralHelper helper;
    private static CommandHandler commandHandler;
    private static TeleportManager teleportManager;
    private static TeleportCooldown teleportCooldown;
    private static TeleportTimer teleportTimer;
    private static WorldSpawns worldSpawns;
    private static Messages messages;

    @Override
    public void onEnable() {
        helper = new GeneralHelper(this);
        teleportCooldown = new TeleportCooldown();
        teleportTimer = new TeleportTimer();
        teleportManager = new TeleportManager();
        saveDefaultConfig();
        worldSpawns = new WorldSpawns(this);
        messages = new Messages(this);
        commandHandler = new CommandHandler(this);
        getLogger().info("BeanRTP.jar has been enabled...");
        commandHandler.addCommand(new RandomTeleportCommand());

        // Initialize the commands after you have added all the commands.
        commandHandler.initializeCommands();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @NotNull
    public static GeneralHelper getHelper() {
        return helper;
    }

    @NotNull
    public static CommandHandler getCommandHandler() {
        return commandHandler;
    }

    @NotNull
    public static TeleportCooldown getTeleportCooldown() {
        return teleportCooldown;
    }

    @NotNull
    public static TeleportTimer getTeleportTimer() {
        return teleportTimer;
    }

    @NotNull
    public static TeleportManager getTeleportManager() {
        return teleportManager;
    }

    @NotNull
    public static WorldSpawns getWorldSpawns() {
        return worldSpawns;
    }

    @NotNull
    public static Messages getMessages() {
        return messages;
    }
}
