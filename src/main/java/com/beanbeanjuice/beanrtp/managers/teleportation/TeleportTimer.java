package com.beanbeanjuice.beanrtp.managers.teleportation;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class TeleportTimer {

    public HashMap<UUID, Double> timerCooldowns;

    public TeleportTimer() {
        timerCooldowns = new HashMap<>();
    }

    public void setTimerCooldown(Player player, int seconds) {
        double delay = System.currentTimeMillis() + (seconds*1000);
        timerCooldowns.put(player.getUniqueId(), delay);
    }

    public boolean checkCooldown(Player player) {
        return !timerCooldowns.containsKey(player.getUniqueId()) || timerCooldowns.get(player.getUniqueId()) <= System.currentTimeMillis();
    }

}
