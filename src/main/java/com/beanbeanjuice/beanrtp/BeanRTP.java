package com.beanbeanjuice.beanrtp;

import com.beanbeanjuice.beanrtp.command.RTPCommand;
import com.beanbeanjuice.beanrtp.config.Messages;
import com.beanbeanjuice.beanrtp.utility.CommandHandler;
import com.beanbeanjuice.beanrtp.utility.Helper;
import com.beanbeanjuice.beanrtp.utility.teleportation.TeleportationManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class BeanRTP extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Messages.createConfig(this);

        Helper.initialize(this);
        TeleportationManager.initialize(this);
        initializeCommands();

        getLogger().info("The plugin has been enabled.");
    }

    private void initializeCommands() {
        CommandHandler handler = new CommandHandler(this);

        handler.initializeCommand("rtp", new RTPCommand(this));
    }

    @Override
    public void onDisable() {
        getLogger().info("The plugin has been disabled.");
    }

}
