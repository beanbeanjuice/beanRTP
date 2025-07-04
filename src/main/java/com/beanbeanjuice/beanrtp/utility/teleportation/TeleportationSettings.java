package com.beanbeanjuice.beanrtp.utility.teleportation;

import com.beanbeanjuice.beanrtp.utility.config.Config;
import com.beanbeanjuice.beanrtp.utility.config.ConfigDataKey;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldBorder;

import java.util.AbstractMap;

public class TeleportationSettings {

    private final Config config;

    public TeleportationSettings(Config config) {
        this.config = config;
    }

    public int getMinimumDistanceFromBorderCenter() {
        return config.getAsInt(ConfigDataKey.MINIMUM_DISTANCE_FROM_BORDER_CENTER);
    }

    public String[] getAllowedWorlds() {
        return config.getAsStringList(ConfigDataKey.ALLOWED_WORLDS).toArray(new String[0]);
    }

    public World getFallbackWorld() {
        return Bukkit.getWorld(config.getAsString(ConfigDataKey.FALLBACK_WORLD));
    }

    public int getCooldownTime() {
        return config.getAsInt(ConfigDataKey.COOLDOWN_TIME);
    }

    public int getCountdownTime() {
        return config.getAsInt(ConfigDataKey.COUNTDOWN_TIME);
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
