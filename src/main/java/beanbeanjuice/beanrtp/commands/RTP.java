package beanbeanjuice.beanrtp.commands;

import beanbeanjuice.beanrtp.BeanRTP;
import beanbeanjuice.beanrtp.managers.GeneralHelper;
import beanbeanjuice.beanrtp.managers.filemanagers.Messages;
import beanbeanjuice.beanrtp.managers.filemanagers.WorldSpawns;
import beanbeanjuice.beanrtp.managers.teleportation.TeleportCooldown;
import beanbeanjuice.beanrtp.managers.teleportation.TeleportManager;
import beanbeanjuice.beanrtp.managers.teleportation.TeleportTimer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RTP implements CommandExecutor {

    private BeanRTP plugin;

    public RTP(BeanRTP plugin) {
        this.plugin = plugin;
        plugin.getCommand("rtp").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            if (args.length == 1) {
                if (Bukkit.getPlayer(args[0]) != null) {
                    if (plugin.getConfig().getStringList("allowed-worlds").contains(Bukkit.getPlayer(args[0]).getWorld().getName())) {
                        new TeleportManager(Bukkit.getPlayer(args[0]), plugin).teleportPlayer();
                        sender.sendMessage(GeneralHelper.getConsolePrefix() + ("%s has been successfully teleported.").replace("%s", args[0]));
                        return true;
                    } else {
                        sender.sendMessage(GeneralHelper.getConsolePrefix() + ("%s is not in an allowed world.").replace("%s", args[0]));
                        return false;
                    }
                } else if (args[0].equalsIgnoreCase("reload")) {
                    sender.sendMessage(GeneralHelper.getConsolePrefix() + "The plugin has been successfully reloaded.");
                    plugin.reloadConfig();
                    return true;
                } else {
                    sender.sendMessage(GeneralHelper.getConsolePrefix() + ("%s is not a player.").replace("%s", args[0]));
                    return false;
                }
            } else {
                sender.sendMessage(GeneralHelper.getConsolePrefix() + "Only players can execute this command.");
                return false;
            }
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            if (player.hasPermission("beanRTP.use") || player.isOp()) {
                if (plugin.getConfig().getStringList("allowed-worlds").contains(player.getWorld().getName())) {
                    if (TeleportTimer.checkCooldown(player)) {
                        if (TeleportCooldown.checkCooldown(player) || player.hasPermission("beanRTP.bypass.cooldown") || player.isOp()) {
                            new TeleportManager(player, plugin).teleportPlayer();
                            return true;
                        }
                        player.sendMessage(GeneralHelper.getPrefix() + GeneralHelper.translateColors(Messages.getConfig().getString("cooldown")).replace("{seconds}", Integer.toString(TeleportCooldown.getCooldown(player))));
                        return false;
                    } else {
                        player.sendMessage(GeneralHelper.getPrefix() + GeneralHelper.translateColors(Messages.getConfig().getString("already-teleporting")));
                        return false;
                    }
                } else {
                    player.sendMessage(GeneralHelper.getPrefix() + GeneralHelper.translateColors(Messages.getConfig().getString("not-allowed-world")));
                    return false;
                }
            }
            player.sendMessage(GeneralHelper.getPrefix() + GeneralHelper.getNoPermission());
            return false;
        }

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("help")) {
                displayHelp(player);
                return true;
            } else if (args[0].equalsIgnoreCase("reload")) {
                if (player.hasPermission("beanRTP.reload") || player.isOp()) {
                    player.sendMessage(GeneralHelper.getPrefix() + GeneralHelper.translateColors("&aThe plugin has been successfully reloaded."));
                    plugin.reloadConfig();
                    return true;
                } else {
                    player.sendMessage(GeneralHelper.getPrefix() + GeneralHelper.getNoPermission());
                    return false;
                }
            } else if (args[0].equalsIgnoreCase("setspawn")) {
                if (player.hasPermission("beanRTP.setspawn") || player.isOp()) {
                    WorldSpawns.setWorldSpawn(player.getLocation());
                    String message = GeneralHelper.translateColors(Messages.getConfig().getString("spawn-set")).replace("{x}", Integer.toString(player.getLocation().getBlockX()));
                    message = message.replace("{z}", Integer.toString(player.getLocation().getBlockZ()));
                    message = message.replace("{world}", player.getWorld().getName());
                    player.sendMessage(GeneralHelper.getPrefix() + message);
                    return true;
                } else {
                    player.sendMessage(GeneralHelper.getPrefix() + GeneralHelper.getNoPermission());
                    return false;
                }
            } else if (Bukkit.getPlayer(args[0]) != null) {
                if (player.hasPermission("beanRTP.others") || player.isOp()) {
                    if (plugin.getConfig().getStringList("allowed-worlds").contains(Bukkit.getPlayer(args[0]).getWorld().getName())) {
                        new TeleportManager(Bukkit.getPlayer(args[0]), plugin).teleportPlayer();
                        return true;
                    }
                    else {
                        player.sendMessage(GeneralHelper.getPrefix() + GeneralHelper.translateColors(Messages.getConfig().getString("not-allowed-world-others").replace("{player}", Bukkit.getPlayer(args[0]).getName())));
                        return false;
                    }
                }
                player.sendMessage(GeneralHelper.getPrefix() + GeneralHelper.getNoPermission());
                return false;
            }
            player.sendMessage(GeneralHelper.getPrefix() + GeneralHelper.translateColors(Messages.getConfig().getString("unknown-player")).replace("{player}", args[0]));
            return false;
        }
        player.sendMessage(GeneralHelper.getPrefix() + GeneralHelper.translateColors(Messages.getConfig().getString("unknown-command")));
        return false;
    }

    void displayHelp(Player player) {
        player.sendMessage(GeneralHelper.getPrefix() + GeneralHelper.translateColors(Messages.getConfig().getString("help-message")));
        for (String string : Messages.getConfig().getStringList("help")) {
            player.sendMessage(GeneralHelper.translateColors(string));
        }
    }
}