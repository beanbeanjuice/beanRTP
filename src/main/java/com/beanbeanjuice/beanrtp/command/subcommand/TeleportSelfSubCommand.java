package com.beanbeanjuice.beanrtp.command.subcommand;

import com.beanbeanjuice.beanrtp.utility.Helper;
import com.beanbeanjuice.beanrtp.utility.ISubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TeleportSelfSubCommand implements ISubCommand {

    @Override
    public boolean handle(@NotNull CommandSender sender, @NotNull String[] args) {
        if (!(sender instanceof Player)) return handleConsole(sender);

        return handlePlayer((Player) sender);
    }

    @Override
    public String getPermission() {
        return "beanRTP.use";
    }

    private boolean handleConsole(CommandSender sender) {
        Helper.sendUnknownCommand(sender);
        return true;
    }

    private boolean handlePlayer(Player player) {
        Helper.sendMessage(player, "&c&lTeleporting self!");
        return true;
    }

}
