package com.beanbeanjuice.beanrtp.command.subcommand;

import com.beanbeanjuice.beanrtp.utility.Helper;
import com.beanbeanjuice.beanrtp.utility.ISubCommand;
import com.beanbeanjuice.beanrtp.utility.teleportation.TeleportationManager;
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
        if (!canBypassCooldown(player) && TeleportationManager.getCooldownManager().isInCooldown(player)) {
            int secondsLeft = TeleportationManager.getCooldownManager().getCooldownInSeconds(player);
            Helper.sendMessage(player, Helper.getMessageConfig("cooldown").replace("{seconds}", String.valueOf(secondsLeft)));
            return false;
        }

        if (TeleportationManager.teleport(player)) {
            TeleportationManager.getCooldownManager().addCooldown(player);
            Helper.sendMessage(player, "&c&lTeleporting self!");
            return true;
        }
        return true;
    }

    private boolean canBypassCooldown(Player player) {
        return player.isOp() || player.hasPermission("beanRTP.bypass.cooldown");
    }

}
