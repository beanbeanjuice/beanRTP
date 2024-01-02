package com.beanbeanjuice.beanrtp.utility;

import com.beanbeanjuice.beanrtp.BeanRTP;
import org.bukkit.command.CommandExecutor;
import org.jetbrains.annotations.NotNull;

public class CommandHandler {

    private final BeanRTP plugin;

    public CommandHandler(@NotNull BeanRTP plugin) {
        this.plugin = plugin;
    }

    public void initializeCommand(@NotNull String name, @NotNull CommandExecutor command) {
        plugin.getCommand(name).setExecutor(command);
    }

}
