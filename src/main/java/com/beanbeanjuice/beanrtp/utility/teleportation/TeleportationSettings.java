package com.beanbeanjuice.beanrtp.utility.teleportation;

import com.beanbeanjuice.beanrtp.utility.Helper;
import com.beanbeanjuice.beanrtp.utility.config.Config;
import com.beanbeanjuice.beanrtp.utility.config.ConfigDataKey;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldBorder;

import java.util.AbstractMap;
import java.util.List;

@Getter
public class TeleportationSettings {

    private final int minimumDistanceFromBorderCenter;
    private final String[] allowedWorlds;
    private final int cooldownTime;
    private final int countdownTime;

    public TeleportationSettings() {
        Config config = Helper.getPlugin().getPluginConfig();
        minimumDistanceFromBorderCenter = (Integer) config.get(ConfigDataKey.MINIMUM_DISTANCE_FROM_BORDER_CENTER);

        allowedWorlds = ((List<String>) config.get(ConfigDataKey.ALLOWED_WORLDS)).toArray(new String[0]);

        cooldownTime = (Integer) config.get(ConfigDataKey.COOLDOWN_TIME);
        countdownTime = (Integer) config.get(ConfigDataKey.COUNTDOWN_TIME);
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
