package com.github.beanbeanjuice.beanrtp;

import com.github.beanbeanjuice.beanrtp.commands.TestCommand;
import com.github.beanbeanjuice.beanrtp.managers.configs.ConfigHandler;
import com.github.beanbeanjuice.beanrtp.utilities.GeneralHelper;
import com.github.beanbeanjuice.beanrtp.utilities.usages.CommandUsageHandler;
import org.bukkit.plugin.java.JavaPlugin;

public final class beanRTP extends JavaPlugin {

    private static GeneralHelper generalHelper;
    private static CommandUsageHandler commandHandler;
    private static ConfigHandler configHandler;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        // Instantiate Things
        generalHelper = new GeneralHelper(this);
        commandHandler = new CommandUsageHandler(this);
        configHandler = new ConfigHandler();

        getLogger().info("MainPlugin.jar has been enabled...");

        // Add Commands
        commandHandler.addCommand(new TestCommand());

        commandHandler.initializeCommands();

    }

    @Override
    public void onDisable() {
        getLogger().info("MainPlugin.jar has been disabled...");
    }

    public static GeneralHelper getHelper() {
        return generalHelper;
    }

    public static CommandUsageHandler getCommandUsageHandler() {
        return commandHandler;
    }
}
