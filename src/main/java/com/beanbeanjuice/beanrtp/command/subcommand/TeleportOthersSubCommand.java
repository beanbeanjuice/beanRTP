package com.beanbeanjuice.beanrtp.command.subcommand;

import com.beanbeanjuice.beanrtp.utility.Helper;
import com.beanbeanjuice.beanrtp.utility.ISubCommand;
import com.beanbeanjuice.beanrtp.utility.config.Config;
import com.beanbeanjuice.beanrtp.utility.config.ConfigDataKey;
import com.beanbeanjuice.beanrtp.utility.teleportation.TeleportationManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportOthersSubCommand implements ISubCommand {

    @Override
    public boolean handle(CommandSender sender, String[] args) {
        Player player = Bukkit.getPlayer(args[0]);

        Config messageConfig = Helper.getPlugin().getMessageConfig();

        if (!TeleportationManager.inAllowedWorld(player)) {
            Helper.sendMessage(sender, (String) messageConfig.get(ConfigDataKey.NOT_ALLOWED_WORLD_OTHERS_MESSAGE));
            return false;
        }

        if (TeleportationManager.teleport(player))
            Helper.sendMessage(sender, ((String) messageConfig.get(ConfigDataKey.OTHER_SUCCESSFUL_TELEPORTATION_MESSAGE))
                    .replace("{player}", player.getName()));
        else
            Helper.sendMessage(sender, ((String) messageConfig.get(ConfigDataKey.OTHER_UNSUCCESSFUL_TELEPORTATION_MESSAGE))
                    .replace("{player}", player.getName()));

        return true;
    }

    @Override
    public String getPermission() {
        return "beanRTP.others";
    }

}
