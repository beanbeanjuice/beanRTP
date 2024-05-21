package com.beanbeanjuice.beanrtp.utility.cooldown;

import com.beanbeanjuice.beanrtp.utility.Helper;
import com.beanbeanjuice.beanrtp.utility.config.ConfigDataKey;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class CooldownManager {

    private final HashMap<UUID, Long> cooldowns;

    public CooldownManager() {
        this.cooldowns = new HashMap<>();
    }

    public void addCooldown(Player player) {
        cooldowns.put(player.getUniqueId(), System.currentTimeMillis());
    }

    public int getCooldownInSeconds(Player player) {
        UUID uuid = player.getUniqueId();
        if (!cooldowns.containsKey(uuid)) return 0;

        long elapsedTimeInMilliseconds = System.currentTimeMillis() - cooldowns.get(uuid);
        long elapsedTimeInSeconds = TimeUnit.MILLISECONDS.toSeconds(elapsedTimeInMilliseconds);

        long timeLeft = Helper.getPlugin().getPluginConfig().getAsInt(ConfigDataKey.COOLDOWN_TIME) - elapsedTimeInSeconds;
        if (timeLeft < 0) return 0;

        return (int) timeLeft;
    }

    public boolean isInCooldown(Player player) {
        return getCooldownInSeconds(player) > 0;
    }

}
