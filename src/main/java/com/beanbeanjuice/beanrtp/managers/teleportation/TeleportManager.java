package com.beanbeanjuice.beanrtp.managers.teleportation;

import com.beanbeanjuice.beanrtp.BeanRTP;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

/**
 * A class used for teleporting {@link Player}s.
 *
 * @author beanbeanjuice
 */
public class TeleportManager {

    private BeanRTP plugin;
    private Player player;

    /**
     * Creates a new {@link TeleportManager} object.
     * @param player The {@link Player} to be teleported.
     */
    public TeleportManager(@NotNull Player player) {
        this.player = player;
        plugin = BeanRTP.getHelper().getPlugin();
    }

    /**
     * Teleports the {@link Player}.
     */
    public void teleportPlayer() {
        if (player.hasPermission("beanRTP.bypass.timer") || plugin.getConfig().getInt("countdown-time") == 0) {
            teleportationSequence();
        } else {
            BeanRTP.getTeleportTimer().setTimerCooldown(player, plugin.getConfig().getInt("countdown-time"));
            final int[] countdownTime = {plugin.getConfig().getInt("countdown-time")};
            plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
                public void run() {
                    if (countdownTime[0] != -1) {
                        if (countdownTime[0] != 0) {
                            player.sendTitle(BeanRTP.getHelper().getPrefix(), BeanRTP.getHelper().translateColors(BeanRTP.getMessages().getConfig().getString("starting-teleportation")).replace("{seconds}", Integer.toString((countdownTime[0]))), 0, 20, 20);
                            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 10, 1);
                            countdownTime[0]--;
                        } else {
                            teleportationSequence();
                            applyEffects();
                            countdownTime[0]--;
                        }
                    }
                }
            }, 0L, 20L);
        }
    }

    /**
     * Initiates the teleportation sequence.
     */
    private void teleportationSequence() {
        int attempts = 0;
        Location playerLocation = player.getLocation();

        double playerX = playerLocation.getBlockX();
        double playerZ = playerLocation.getBlockZ();
        String playerworld = player.getWorld().getName();

        int spawnX = 0;
        int spawnZ = 0;
        if (BeanRTP.getWorldSpawns().getConfig().getString("spawn-coords." + playerworld) != null) {
            spawnX = BeanRTP.getWorldSpawns().getConfig().getInt("spawn-coords." + playerworld + ".spawn-x-coordinate");
            spawnZ = BeanRTP.getWorldSpawns().getConfig().getInt("spawn-coords." + playerworld + ".spawn-z-coordinate");
        }
        double newX = 0;
        double newZ = 0;
        int newY = 0;

        World world = player.getWorld();
        Location newLocation;

        boolean search = true;
        while (search && attempts++ < 100) {
            newX = getRandomCoord(playerX, spawnX);
            newZ = getRandomCoord(playerZ, spawnZ);
            newY = getTopBlock(newX, newZ);

            newLocation = new Location(world, newX, newY, newZ);
            search = !isSafe(newLocation, newY);
        }

        if (attempts != 100) {
            newLocation = new Location(world, newX + 0.5, newY + 2, newZ + 0.5);
            player.teleport(newLocation);
            player.sendTitle(BeanRTP.getHelper().getPrefix().replace(" ", ""), BeanRTP.getHelper().translateColors(BeanRTP.getMessages().getConfig().getString("successful-teleportation")), 40, 45, 20);
            BeanRTP.getTeleportCooldown().setCooldown(player, plugin.getConfig().getInt("cooldown-time"));
        } else {
            player.sendMessage(BeanRTP.getHelper().getPrefix() + BeanRTP.getHelper().translateColors(BeanRTP.getMessages().getConfig().getString("unsuccessful-teleportation").replace("{player}", player.getName())));
        }
    }

    /**
     * Gets a random coord in accordance with the config.
     * @param playerCoord The current location of the player.
     * @param spawnCoord The current location of the spawn.
     * @return The random coord.
     */
    @NotNull
    private Integer getRandomCoord(@NotNull Double playerCoord, @NotNull Integer spawnCoord) {
        int radiusFromSpawn = plugin.getConfig().getInt("world-border-radius");
        Random random = new Random();

        int minDistFromPlayer = plugin.getConfig().getInt("minimum-distance-from-player");
        int minDistFromSpawn = plugin.getConfig().getInt("minimum-distance-from-spawn");

        int newCoord = random.nextInt(radiusFromSpawn * 2) - radiusFromSpawn;
        if (Math.abs(newCoord - playerCoord) < minDistFromPlayer || Math.abs(newCoord - spawnCoord) < minDistFromSpawn) {
            while (Math.abs(newCoord - playerCoord) < minDistFromPlayer || Math.abs(newCoord - spawnCoord) < minDistFromSpawn) {
                newCoord = random.nextInt(radiusFromSpawn * 2) - radiusFromSpawn;
            }
        }
        return newCoord + spawnCoord;
    }

    /**
     * @param location The {@link Location} to be checked.
     * @param y The Y value to be checked.
     * @return Whether or not it is safe to teleport.
     */
    @NotNull
    private Boolean isSafe(@NotNull Location location, @NotNull Integer y) {

        // If the y value is below bedrock, then it is not safe.
        // TODO: Update this for upcoming 1.17
        if (y == -1) {
            return false;
        }

        World world = location.getWorld();
        double x = location.getX();
        double z = location.getZ();

        // Checks for materials 1 and 2 blocks above.
        Material blockTypeAtY = location.getBlock().getType();

        if (!((blockTypeAtY != Material.LAVA) && (blockTypeAtY != Material.CACTUS) && (blockTypeAtY != Material.MAGMA_BLOCK) && (blockTypeAtY != Material.FIRE) && (blockTypeAtY != Material.WATER) && (y != -1))) {
            return false;
        }

        if (isNether(location.getWorld())) {
            Material blockTypeAboveY = new Location(world, x, y + 1, z).getBlock().getType();
            Material blockTypeAboveY2 = new Location(world, x, y + 2, z).getBlock().getType();

            if (blockTypeAboveY != Material.AIR) {
                return false;
            }

            if (blockTypeAboveY2 != Material.AIR) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param world The {@link World} to be checked.
     * @return Whether or not the {@link World} is the nether.
     */
    @NotNull
    private Boolean isNether(@NotNull World world) {
        return plugin.getConfig().getStringList("nether-worlds").contains(world.getName());
    }

    /**
     * @param x The x coord.
     * @param z The y coord.
     * @return The top coordinate for the provided x and z values.
     */
    @NotNull
    private Integer getTopBlock(@NotNull Double x, @NotNull Double z) {
        int y = -1;
        // TODO: This will need to change for the 1.17 update
        // Logic for Nether
        if (isNether(player.getWorld())) {
            int max = 120;

            for (int i = 0; i < max; i++) {
                Location location = new Location(player.getWorld(), x, i, z);
                y = i-1;
                if (location.getBlock().getType() == Material.AIR) {
                    break;
                }
            }
        } else { // Logic for Overworld
            int max = 256;

            for (int i = 0; i < max; i++) {
                Location location = new Location(player.getWorld(), x, i, z);
                if (location.getBlock().getType() != Material.AIR) {
                    y = i;
                }
            }
        }
        return y;
    }

    /**
     * Effects to apply after teleportation.
     */
    private void applyEffects() {
        if (isNether(player.getWorld())) {
            player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
            player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 600, 0));
        }
        player.removePotionEffect(PotionEffectType.SLOW_FALLING);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 300, 0));
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 10));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40, 10));
        player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_FLAP, 10, 1);
        player.playSound(player.getLocation(), Sound.ENTITY_BAT_DEATH, 0.5F, 1);
    }

}
