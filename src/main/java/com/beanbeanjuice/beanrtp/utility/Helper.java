package com.beanbeanjuice.beanrtp.utility;

import com.beanbeanjuice.beanrtp.BeanRTP;
import com.beanbeanjuice.beanrtp.utility.config.ConfigDataKey;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Helper {

    @Getter private static BeanRTP plugin;

    public static void initialize(BeanRTP beanRTP) {
        plugin = beanRTP;
    }

    public static String translateColors(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static void sendNoPermission(CommandSender sender) {
        sendMessage(sender, plugin.getMessageConfig().getAsString(ConfigDataKey.NO_PERMISSION_MESSAGE));
    }

    public static void sendUnknownCommand(CommandSender sender) {
        sendMessage(sender, plugin.getMessageConfig().getAsString(ConfigDataKey.UNKNOWN_COMMAND_MESSAGE));
    }

    public static void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(plugin.getPluginConfig().getAsString(ConfigDataKey.PREFIX) + message);
    }

}
