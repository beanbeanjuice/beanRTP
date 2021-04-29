package com.beanbeanjuice.beanrtp.managers.teleportation;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.UUID;

/**
 * A class used for {@link TeleportCooldown}.
 *
 * @author beanbeanjuice
 */
public class TeleportCooldown {

    private HashMap<UUID, Double> cooldowns;

    /**
     * Creates a new {@link TeleportCooldown} object.
     */
    public TeleportCooldown() {
        cooldowns = new HashMap<>();
    }

    /**
     * Sets the teleport cooldown for the specified {@link Player}.
     * @param player The {@link Player} to set the cooldown for.
     * @param seconds The length of the cooldown.
     */
    public void setCooldown(@NotNull Player player, @NotNull Integer seconds) {
        double delay = System.currentTimeMillis() + (seconds*1000);
        cooldowns.put(player.getUniqueId(), delay);
    }

    /**
     * Gets the cooldown for the specified {@link Player}.
     * @param player The {@link Player} to get the cooldown for.
     * @return The remaining time in seconds for the cooldown.
     */
    @NotNull
    public Integer getCooldown(@NotNull Player player) {
        return Math.toIntExact(Math.round((cooldowns.get(player.getUniqueId()) - (System.currentTimeMillis()))/1000));
    }

    /**
     * Checks the cooldown for the specified {@link Player}.
     * @param player The {@link Player} to check the cooldown for.
     * @return Whether or not the {@link Player} is still on cooldown.
     */
    @NotNull
    public Boolean checkCooldown(@NotNull Player player) {
        return !cooldowns.containsKey(player.getUniqueId()) || cooldowns.get(player.getUniqueId()) <= System.currentTimeMillis();
    }

}
