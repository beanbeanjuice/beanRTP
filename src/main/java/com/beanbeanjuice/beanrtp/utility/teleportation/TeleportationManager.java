package com.beanbeanjuice.beanrtp.utility.teleportation;

import com.beanbeanjuice.beanrtp.BeanRTP;
import com.beanbeanjuice.beanrtp.utility.cooldown.CooldownManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.logging.Level;
import java.util.stream.Stream;

public class TeleportationManager {

    private static BeanRTP plugin;
    private static TeleportationSettings settings;
    private static CooldownManager cooldownManager;

    private static final int MAX_LOCATIONS_PER_PLAYER = 3;
    private static final int MIN_LOCATIONS_PER_PLAYER = 1;

    private static final Material[] unsafeMaterials = new Material[] {
            Material.LAVA,
            Material.WATER,
            Material.KELP,
            Material.KELP_PLANT,
            Material.SEAGRASS,
            Material.SEA_PICKLE,
            Material.FIRE,
            Material.MAGMA_BLOCK
    };

    private static final Material[] ignoredMaterials = new Material[] {
            Material.LAVA,
            Material.WATER,

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
            Material.SPRUCE_LEAVES,
            Material.SNOW,
            Material.SHORT_GRASS,
            Material.TALL_GRASS
    };

    private static HashMap<World, Vector<Location>> safeLocations = new HashMap<>();

    private TeleportationManager() { }

    public static void initialize(@NotNull BeanRTP beanRTP) {
        plugin = beanRTP;
        settings = new TeleportationSettings(plugin);

        cooldownManager = new CooldownManager(plugin.getConfig().getInt("cooldown-time"));

        plugin.getLogger().log(Level.INFO, "Populating RTP locations...");
        populateLocations(settings.getAllowedWorlds());
    }

    private static void populateLocations(String[] allowedWorlds) {
        Stream<World> worlds = Arrays.stream(allowedWorlds).map(Bukkit::getWorld);

        // Using a vector to remain thread safe.
        worlds.forEach((world) -> safeLocations.putIfAbsent(world, new Vector<>()));
        safeLocations.forEach(TeleportationManager::createAsyncPopulationTask);
    }

    private static void createAsyncPopulationTask(World world, Vector<Location> locations) {
        if (locations.size() >= getMinLocationsCount()) return;

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            while (locations.size() < getMaxLocationsCount()) {
                try {
                    locations.add(getNewLocation(world));
                    plugin.getLogger().log(
                            Level.INFO,
                            "Adding RTP location for %s: %d/%d".formatted(
                                    world.getName(), locations.size(), getMaxLocationsCount()
                            )
                    );
                } catch (InterruptedException ignored) { }
            }
        });
    }

    public static boolean teleport(Player player) {
        World world = player.getWorld();

        if (safeLocations.get(world).isEmpty()) return false;

        Location location = safeLocations.get(world).removeFirst();

        // Used to stop console spam of "MOVED TOO QUICKLY"
        Bukkit.getScheduler().runTaskLater(
                plugin,
                () -> player.teleport(location, PlayerTeleportEvent.TeleportCause.PLUGIN),
                1
        );

        if (safeLocations.get(world).size() <= getMinLocationsCount())
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
        } while (worldBorderCenter.distance(newLocation) < settings.getMinimumDistanceFromBorderCenter() || !isSafe(newLocation));

        return newLocation.add(0, 0.5, 0);
    }

    private static boolean isSafe(Location location) {
        // -2 must not be in unsafe or ignored.
        // -1 must not be in unsafe.
        // 0 must not be in unsafe.
        // +1 must not be in unsafe.
        // +2 must not be in unsafe.

        var unsafeArray = Arrays.asList(unsafeMaterials);
        var ignoredArray = Arrays.asList(ignoredMaterials);

        Material materialSub2 = location.clone().subtract(0, 2, 0).getBlock().getType();
        if (unsafeArray.contains(materialSub2) || ignoredArray.contains(materialSub2))
            return false;

        Material materialSub1 = location.clone().subtract(0, 1, 0).getBlock().getType();
        if (unsafeArray.contains(materialSub1))
            return false;

        Material material = location.clone().getBlock().getType();
        if (unsafeArray.contains(material))
            return false;

        Material materialAdd1 = location.clone().add(0, 1, 0).getBlock().getType();
        if (unsafeArray.contains(materialAdd1))
            return false;

        Material materialAdd2 = location.clone().add(0, 2, 0).getBlock(). getType();
        if (unsafeArray.contains(materialAdd2))
            return false;

        return true;

    }

    private static Location getTopBlock(Location location) {
        double MAX_BUILD_HEIGHT = 320.5;
        double MAX_BUILD_HEIGHT_NETHER = 127.5;
        double MIN_BUILD_HEIGHT = -64.5;

        if (location.getWorld().getEnvironment().equals(World.Environment.NETHER)) location.setY(MAX_BUILD_HEIGHT_NETHER);
        else location.setY(MAX_BUILD_HEIGHT);

        Stack<Material> materials = new Stack<>();

        while (location.getY() >= MIN_BUILD_HEIGHT) {
            materials.push(location.getBlock().getType());

            if (checkMaterialStack(materials)) {
                location.add(0, 1, 0);
                return location;
            }

            location.subtract(0, 1, 0);
        }

        location.setY(-100);  // Error
        return location;
    }

    private static boolean checkMaterialStack(Stack<Material> materials) {
        if (materials.size() < 3) return false;

        Material first = materials.pop();
        Material second = materials.pop();
        Material third = materials.pop();

        if (Arrays.asList(ignoredMaterials).contains(first)) {
            materials.push(third);
            materials.push(second);
            materials.push(first);
            return false;
        }

        if (!(Arrays.asList(ignoredMaterials).contains(second) && Arrays.asList(ignoredMaterials).contains(third))) {
            materials.push(third);
            materials.push(second);
            materials.push(first);
            return false;
        }

        return true;
    }

    private static int getMinLocationsCount() {
        int onlinePlayers = Bukkit.getOnlinePlayers().size();
        if (onlinePlayers == 0) return MIN_LOCATIONS_PER_PLAYER;

        return onlinePlayers * MIN_LOCATIONS_PER_PLAYER;
    }

    private static int getMaxLocationsCount() {
        int onlinePlayers = Bukkit.getOnlinePlayers().size();
        if (onlinePlayers == 0) return MAX_LOCATIONS_PER_PLAYER;

        return onlinePlayers * MAX_LOCATIONS_PER_PLAYER;
    }

    public static CooldownManager getCooldownManager() {
        return cooldownManager;
    }

}
