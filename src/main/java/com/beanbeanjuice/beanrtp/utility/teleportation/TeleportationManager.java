package com.beanbeanjuice.beanrtp.utility.teleportation;

import com.beanbeanjuice.beanrtp.BeanRTP;
import com.beanbeanjuice.beanrtp.utility.Helper;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.stream.Stream;

public class TeleportationManager {

    private static BeanRTP plugin;
    private static TeleportationSettings settings;

    private static final int MAX_LOCATION_COUNT = 20;

    private static final Material[] unsafeMaterials = new Material[] {
            Material.AIR,
            Material.LAVA,
            Material.WATER
    };

    private static final Material[] ignoredMaterials = new Material[] {
            Material.AIR,
            Material.CAVE_AIR,
            Material.VOID_AIR,
            Material.ACACIA_LEAVES,
            Material.AZALEA_LEAVES,
            Material.BIRCH_LEAVES,
            Material.CHERRY_LEAVES,
            Material.JUNGLE_LEAVES,
            Material.DARK_OAK_LEAVES,
            Material.MANGROVE_LEAVES,
            Material.FLOWERING_AZALEA_LEAVES,
            Material.OAK_LEAVES,
            Material.SPRUCE_LEAVES
    };

    private static HashMap<World, Vector<Location>> safeLocations = new HashMap<>();

    private TeleportationManager() { }

    public static void initialize(@NotNull BeanRTP beanRTP) {
        plugin = beanRTP;
        settings = new TeleportationSettings(plugin);

        plugin.getLogger().log(Level.INFO, "Populating RTP locations...");
        populateLocations(settings.getAllowedWorlds());
    }

    private static void populateLocations(String[] allowedWorlds) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            Stream<World> worlds = Arrays.stream(allowedWorlds).map(Bukkit::getWorld);

            worlds.forEach((world) -> safeLocations.putIfAbsent(world, new Vector<>()));

            safeLocations.forEach((world, locations) -> {
                while (locations.size() != MAX_LOCATION_COUNT) {
                    try {
                        locations.add(getNewLocation(world));
                        plugin.getLogger().log(Level.INFO, "Adding RTP location for " + world + ": " + locations.size() + "/" + MAX_LOCATION_COUNT);
                    } catch (InterruptedException ignored) { }
                }
            });
        });
    }

    public static boolean teleport(Player player) {
        World world = player.getWorld();

        if (safeLocations.get(world).isEmpty()) {
            Helper.sendMessage(player, "RTP locations are currently loading...");
            return false;
        }

        Location location = safeLocations.get(world).removeFirst();
        player.teleport(location);

        if (safeLocations.get(world).isEmpty())
            populateLocations(settings.getAllowedWorlds());

        return true;
    }

    private static int getRandomInteger(int minimum, int maximum) {
        return minimum + (int) (Math.random() * ((maximum - minimum) + 1));
    }

    private static Location getNewLocation(World world) throws InterruptedException {
        AbstractMap.SimpleEntry<Location, Location> borders = settings.getPoints(world);

        Location worldBorderCenter = world.getWorldBorder().getCenter();
        Location newLocation = world.getWorldBorder().getCenter();

        do {
            int x1 = borders.getKey().getBlockX();
            int x2 = borders.getValue().getBlockX();

            int z1 = borders.getKey().getBlockZ();
            int z2 = borders.getValue().getBlockZ();

            int x = getRandomInteger(x1, x2);
            int z = getRandomInteger(z1, z2);

            newLocation = new Location(worldBorderCenter.getWorld(), x, -256, z);
            newLocation = getTopBlock(newLocation);
            newLocation.add(0.5, 0.5, 0.5);
            Thread.sleep(Duration.ofSeconds(5));
        } while (worldBorderCenter.distance(newLocation) < settings.getMinimumDistanceFromBorderCenter() && !isSafe(newLocation));

        return newLocation.add(0, 0.5, 0);
    }

    private static boolean isSafe(Location location) {
        Material material1 = location.getBlock().getType();
        Material material2 = location.clone().subtract(0, 1, 0).getBlock().getType();
        Material material3 = location.clone().add(0, 1, 0).getBlock().getType();

        var unsafeArray = Arrays.asList(unsafeMaterials);
        return !unsafeArray.contains(material1) &&
                !unsafeArray.contains(material2) &&
                !unsafeArray.contains(material3);
    }

    private static Location getTopBlock(Location location) {
        Block block = location.getWorld().getHighestBlockAt(location);
        return block.getLocation();
    }

}
