package beanbeanjuice.beanrtp.managers.teleportation;

import beanbeanjuice.beanrtp.BeanRTP;
import beanbeanjuice.beanrtp.managers.GeneralHelper;
import beanbeanjuice.beanrtp.managers.filemanagers.Messages;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class TeleportManager {

    private BeanRTP plugin;
    private Player player;

    public TeleportManager(Player player, BeanRTP plugin) {
        this.plugin = plugin;
        this.player = player;
    }

    public void teleportPlayer() {
        if (player.hasPermission("beanRTP.bypass.timer") || plugin.getConfig().getInt("countdown-time") == 0) {
            TeleportationLogic.teleportationSequence(player, plugin);
        } else {
            TeleportTimer.setCooldown(player, plugin.getConfig().getInt("countdown-time"));
            final int[] countdownTime = {plugin.getConfig().getInt("countdown-time")};
            plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
                public void run() {
                    if (countdownTime[0] != -1) {
                        if (countdownTime[0] != 0) {
                            player.sendTitle(GeneralHelper.getPrefix(), GeneralHelper.translateColors(Messages.getConfig().getString("starting-teleportation")).replace("{seconds}", Integer.toString((countdownTime[0]))), 0, 20, 20);
                            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 10, 1);
                            countdownTime[0]--;
                        } else {
                            TeleportationLogic.teleportationSequence(player, plugin);
                            TeleportationLogic.appliedEffects(player, plugin);
                            countdownTime[0]--;
                        }
                    }
                }
            }, 0L, 20L);
        }
    }
}