package com.beanbeanjuice.beanrtp.utility;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public interface ISubCommand {

    boolean handle(CommandSender sender, String[] args);
    String getPermission();

}
