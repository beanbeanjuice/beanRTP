package com.beanbeanjuice.beanrtp.utility;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public interface ISubCommand {

    boolean handle(@NotNull CommandSender sender, @NotNull String[] args);

}
