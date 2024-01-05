package com.beanbeanjuice.beanrtp.command.subcommand;

import com.beanbeanjuice.beanrtp.config.Messages;
import com.beanbeanjuice.beanrtp.utility.Helper;
import com.beanbeanjuice.beanrtp.utility.ISubCommand;
import com.beanbeanjuice.beanrtp.utility.teleportation.TeleportationManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TeleportOthersSubCommand implements ISubCommand {

    @Override
    public boolean handle(@NotNull CommandSender sender, @NotNull String[] args) {
        Player player = Bukkit.getPlayer(args[0]);

        if (TeleportationManager.teleport(player))
            Helper.sendMessage(sender, Messages.getConfig().getString("other-successful-teleportation")
                    .replace("{player}", player.getName()));
        else
            Helper.sendMessage(sender, Messages.getConfig().getString("other-unsuccessful-teleportation")
                    .replace("{player}", player.getName()));

        return true;
    }

    @Override
    public String getPermission() {
        return "beanRTP.others";
    }

}
