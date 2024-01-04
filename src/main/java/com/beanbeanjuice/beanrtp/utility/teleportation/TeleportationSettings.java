package com.beanbeanjuice.beanrtp.utility.teleportation;

import com.beanbeanjuice.beanrtp.BeanRTP;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

import java.util.AbstractMap;

public class TeleportationSettings {

    private final int minimumDistanceFromBorderCenter;

    private final String[] allowedWorlds;

    private final int cooldownTime;
    private final int countdownTime;

    public TeleportationSettings(@NotNull BeanRTP plugin) {
        FileConfiguration config = plugin.getConfig();

        minimumDistanceFromBorderCenter = config.getInt("minimum-distance-from-border-center");

        allowedWorlds = config.getStringList("allowed-worlds").toArray(new String[0]);

        cooldownTime = config.getInt("cooldown-time");
        countdownTime = config.getInt("countdown-time");
    }

    public int getMinimumDistanceFromBorderCenter() {
        return minimumDistanceFromBorderCenter;
    }

    public String[] getAllowedWorlds() {
        return allowedWorlds;
    }

    public int getCooldownTime() {
        return cooldownTime;
    }

    public int getCountdownTime() {
        return countdownTime;
    }

    public AbstractMap.SimpleEntry<Location, Location> getPoints(World world) {
        WorldBorder border = world.getWorldBorder();

        Location center = border.getCenter();
        double size = border.getSize() / 2;

        Location location1 = center.clone().add(size, 0, size);
        Location location2 = center.clone().add(-size, 0, -size);

        return new AbstractMap.SimpleEntry<>(location1, location2);
    }

}
