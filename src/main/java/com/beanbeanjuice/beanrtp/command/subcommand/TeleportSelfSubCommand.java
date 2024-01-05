package com.beanbeanjuice.beanrtp.command.subcommand;

import com.beanbeanjuice.beanrtp.utility.Helper;
import com.beanbeanjuice.beanrtp.utility.ISubCommand;
import com.beanbeanjuice.beanrtp.utility.cooldown.CooldownManager;
import com.beanbeanjuice.beanrtp.utility.countdown.CountdownDisplay;
import com.beanbeanjuice.beanrtp.utility.countdown.CountdownManager;
import com.beanbeanjuice.beanrtp.utility.countdown.CountdownTimer;
import com.beanbeanjuice.beanrtp.utility.teleportation.TeleportationManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Callable;

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

    // TODO: Start countdown.
    // TODO: Don't let player RTP again while in countdown.
    // TODO: If player moves, stop countdown.
    private boolean handlePlayer(Player player) {
        if (!canBypassCooldown(player) && TeleportationManager.getCooldownManager().isInCooldown(player)) {
            int secondsLeft = TeleportationManager.getCooldownManager().getCooldownInSeconds(player);
            Helper.sendMessage(player, Helper.getMessageConfig("cooldown").replace("{seconds}", String.valueOf(secondsLeft)));
            return false;
        }

        int countdownTime = Helper.getPlugin().getConfig().getInt("countdown-time");

        CountdownDisplay displayFunction = (timeLeft) -> {
            Helper.sendMessage(player, "You have " + timeLeft + " seconds left.");
        };

        Callable<Void> completedFunction = () -> {
            if (TeleportationManager.teleport(player)) {
                TeleportationManager.getCooldownManager().addCooldown(player);
                Helper.sendMessage(player, "&c&lTeleporting self!");
            }
            return null;
        };

        Callable<Void> failedFunction = () -> {
            Helper.sendMessage(player, "You moved!");  // TODO: Custom message.
            return null;
        };

        CountdownTimer timer = new CountdownTimer(
                countdownTime, player, player.getLocation(),
                displayFunction, completedFunction, failedFunction
        );

        if (CountdownManager.isCounting(player)) {
            Helper.sendMessage(player, "YOU ALREADY STARTED");  // TODO: Custom message
            return false;
        }

        if (canBypassCountdown(player)) timer.setCountdownTime(0);
        CountdownManager.addCountdown(player, timer).start(Helper.getPlugin());

        return true;
    }

    private boolean canBypassCooldown(Player player) {
        return player.isOp() || player.hasPermission("beanRTP.bypass.cooldown");
    }

    private boolean canBypassCountdown(Player player) {
        return player.isOp() || player.hasPermission("beanRTP.bypass.timer");
    }

}
