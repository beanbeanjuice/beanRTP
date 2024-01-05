package com.beanbeanjuice.beanrtp.command.subcommand;

import com.beanbeanjuice.beanrtp.utility.Helper;
import com.beanbeanjuice.beanrtp.utility.ISubCommand;
import com.beanbeanjuice.beanrtp.utility.countdown.CountdownDisplay;
import com.beanbeanjuice.beanrtp.utility.countdown.CountdownManager;
import com.beanbeanjuice.beanrtp.utility.countdown.CountdownTimer;
import com.beanbeanjuice.beanrtp.utility.teleportation.TeleportationManager;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
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

    private boolean handlePlayer(Player player) {
        if (!TeleportationManager.inAllowedWorld(player)) {
            Helper.sendMessage(player, Helper.getMessageConfig("not-allowed-world"));
            return false;
        }

        if (!canBypassCooldown(player) && TeleportationManager.getCooldownManager().isInCooldown(player)) {
            int secondsLeft = TeleportationManager.getCooldownManager().getCooldownInSeconds(player);
            Helper.sendMessage(player, Helper.getMessageConfig("cooldown").replace("{seconds}", String.valueOf(secondsLeft)));
            return false;
        }

        int countdownTime = Helper.getPlugin().getConfig().getInt("countdown-time");

        CountdownDisplay displayFunction = (timeLeft) -> {
            player.sendTitle(Helper.getPrefix(), Helper.getMessageConfig("starting-teleportation").replace("{seconds}", Integer.toString(timeLeft)), 0, 20, 20);
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 10, 1);
        };

        Callable<Void> completedFunction = () -> {
            if (TeleportationManager.teleport(player)) {
                applyEffects(player);
                TeleportationManager.getCooldownManager().addCooldown(player);
                player.sendTitle(Helper.getPrefix(), Helper.getMessageConfig("successful-teleportation"), 0, 20, 20);
            }
            return null;
        };

        Callable<Void> failedFunction = () -> {
            player.sendTitle(Helper.getPrefix(), Helper.getMessageConfig("moved-during-teleport"), 0, 20, 20);
            player.playSound(player.getLocation(), Sound.ENTITY_GHAST_DEATH, 10, 1);
            return null;
        };

        CountdownTimer timer = new CountdownTimer(
                countdownTime, player, player.getLocation(),
                displayFunction, completedFunction, failedFunction
        );

        if (CountdownManager.isCounting(player)) {
            Helper.sendMessage(player, Helper.getMessageConfig("already-teleporting"));
            return false;
        }

        if (canBypassCountdown(player)) timer.setCountdownTime(0);
        CountdownManager.addCountdown(player, timer).start(Helper.getPlugin());

        return true;
    }

    private void applyEffects(Player player) {
        // Running 2 ticks later to prevent errors.
        Bukkit.getScheduler().runTaskLater(Helper.getPlugin(), () -> {
            player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_FLAP, 10, 1);
            player.playSound(player.getLocation(), Sound.ENTITY_BAT_DEATH, 0.5F, 1);
            if (player.getWorld().getEnvironment().equals(World.Environment.NETHER)) {
                player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
                player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 600, 0));
            }
            player.removePotionEffect(PotionEffectType.SLOW_FALLING);
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 300, 0));
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 10));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40, 10));
        }, 2);
    }

    private boolean canBypassCooldown(Player player) {
        return player.isOp() || player.hasPermission("beanRTP.bypass.cooldown");
    }

    private boolean canBypassCountdown(Player player) {
        return player.isOp() || player.hasPermission("beanRTP.bypass.timer");
    }

}
