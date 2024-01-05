package com.beanbeanjuice.beanrtp.utility;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class CooldownManager {

    private final HashMap<UUID, Long> cooldowns;
    private final int cooldownInSeconds;

    public CooldownManager(int cooldownInSeconds) {
        this.cooldowns = new HashMap<>();
        this.cooldownInSeconds = cooldownInSeconds;
    }

    public void addCooldown(Player player) {
        cooldowns.put(player.getUniqueId(), System.currentTimeMillis());
    }

    public int getCooldownInSeconds(Player player) {
        UUID uuid = player.getUniqueId();
        if (!cooldowns.containsKey(uuid)) return 0;

        long elapsedTimeInMilliseconds = System.currentTimeMillis() - cooldowns.get(uuid);
        long elapsedTimeInSeconds = TimeUnit.MILLISECONDS.toSeconds(elapsedTimeInMilliseconds);

        long timeLeft = cooldownInSeconds - elapsedTimeInSeconds;
        if (timeLeft < 0) return 0;

        return (int) timeLeft;
    }

    public boolean isInCooldown(Player player) {
        return getCooldownInSeconds(player) > 0;
    }

}
