package beanbeanjuice.beanrtp;

import beanbeanjuice.beanrtp.commands.RTP;
import beanbeanjuice.beanrtp.managers.GeneralHelper;
import beanbeanjuice.beanrtp.managers.TabCompletor;
import beanbeanjuice.beanrtp.managers.filemanagers.Messages;
import beanbeanjuice.beanrtp.managers.filemanagers.WorldSpawns;
import beanbeanjuice.beanrtp.managers.teleportation.TeleportCooldown;
import beanbeanjuice.beanrtp.managers.teleportation.TeleportTimer;
import org.bukkit.plugin.java.JavaPlugin;

public final class BeanRTP extends JavaPlugin {

    @Override
    public void onEnable() {
        TeleportCooldown.setupCooldown();
        TeleportTimer.setupCooldown();
        saveDefaultConfig();
        WorldSpawns.setupWorldSpawns();
        Messages.createConfig(this);
        getLogger().info("BeanRTP.jar has been enabled...");
        new TabCompletor(this);
        new GeneralHelper(this);
        new RTP(this);
    }

    @Override
    public void onDisable() {
        getLogger().info("BeanRTP.jar has been disabled...");
    }
}
