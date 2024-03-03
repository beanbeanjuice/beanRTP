package com.beanbeanjuice.beanrtp;

import com.beanbeanjuice.beanrtp.command.RTPCommand;
import com.beanbeanjuice.beanrtp.utility.CommandHandler;
import com.beanbeanjuice.beanrtp.utility.Helper;
import com.beanbeanjuice.beanrtp.utility.config.Config;
import com.beanbeanjuice.beanrtp.utility.config.MessageConfig;
import com.beanbeanjuice.beanrtp.utility.config.PluginConfig;
import com.beanbeanjuice.beanrtp.utility.teleportation.TeleportationManager;
import lombok.Getter;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class BeanRTP extends JavaPlugin {

    private Config pluginConfig;
    private Config messageConfig;

    @Override
    public void onEnable() {
        pluginConfig = new PluginConfig(this.getDataFolder());
        messageConfig = new MessageConfig(this.getDataFolder());

        pluginConfig.initialize();
        messageConfig.initialize();

        Helper.initialize(this);
        TeleportationManager.initialize(this);
        initializeCommands();

        int pluginId = 21207;
        Metrics metrics = new Metrics(this, pluginId);
        getLogger().info("bStats logging has been enabled.");

        getLogger().info("The plugin has been enabled.");
    }

    private void initializeCommands() {
        CommandHandler handler = new CommandHandler(this);

        handler.initializeCommand("rtp", new RTPCommand());
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);
        getLogger().info("The plugin has been disabled.");
    }

}
