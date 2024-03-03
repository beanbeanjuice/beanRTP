package com.beanbeanjuice.beanrtp.utility;

import com.beanbeanjuice.beanrtp.BeanRTP;
import org.bukkit.command.CommandExecutor;

public class CommandHandler {

    private final BeanRTP plugin;

    public CommandHandler(BeanRTP plugin) {
        this.plugin = plugin;
    }

    public void initializeCommand(String name, CommandExecutor command) {
        plugin.getCommand(name).setExecutor(command);
    }

}
