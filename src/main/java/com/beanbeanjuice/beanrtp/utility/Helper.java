package com.beanbeanjuice.beanrtp.utility;

import com.beanbeanjuice.beanrtp.BeanRTP;
import com.beanbeanjuice.beanrtp.config.Messages;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class Helper {

    private static String prefix;
    private static String noPermission;

    public static void initialize(BeanRTP plugin) {
        prefix = translateColors(plugin.getConfig().getString("prefix"));
        noPermission = translateColors(Messages.getConfig().getString("no-permission"));
    }

    @NotNull
    public static String translateColors(@NotNull String string) {
        return string.replaceAll("&", "§");
    }

    @NotNull
    public static String getMessageConfig(@NotNull String string) {
        return translateColors(Messages.getConfig().getString(string));
    }

    public static String getPrefix() {
        return prefix;
    }

    public static String getNoPermission() {
        return noPermission;
    }

    public static void sendUnknownCommand(@NotNull CommandSender sender) {
        sendMessage(sender, getMessageConfig("unknown-command"));
    }

    public static void sendMessage(@NotNull CommandSender sender, @NotNull String message) {
        sender.sendMessage(getPrefix() + translateColors(message));
    }

}
