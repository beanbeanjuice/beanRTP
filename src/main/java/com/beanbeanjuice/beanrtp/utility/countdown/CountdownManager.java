package com.beanbeanjuice.beanrtp.utility.countdown;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class CountdownManager {

    private static HashMap<UUID, CountdownTimer> countdowns = new HashMap<>();

    public static CountdownTimer addCountdown(Player player, CountdownTimer timer) {
        countdowns.put(player.getUniqueId(), timer);
        return timer;
    }

    public static boolean isCounting(Player player) {
        if (!countdowns.containsKey(player.getUniqueId())) return false;

        return countdowns.get(player.getUniqueId()).isRunning();
    }

}
