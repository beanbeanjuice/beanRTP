package beanbeanjuice.beanrtp.managers.teleportation;

import beanbeanjuice.beanrtp.BeanRTP;
import beanbeanjuice.beanrtp.managers.GeneralHelper;
import beanbeanjuice.beanrtp.managers.filemanagers.Messages;
import beanbeanjuice.beanrtp.managers.filemanagers.WorldSpawns;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class TeleportationLogic {

    public static void teleportationSequence(Player player, BeanRTP plugin) {
        int attempts = 0;
        Location playerLocation = player.getLocation();

        double playerX = playerLocation.getBlockX();
        double playerZ = playerLocation.getBlockZ();
        String playerworld = player.getWorld().getName();

        int spawnX = 0;
        int spawnZ = 0;
        if (WorldSpawns.getWorldSpawnsConfig().getString("spawn-coords." + playerworld) != null) {
            spawnX = WorldSpawns.getWorldSpawnsConfig().getInt("spawn-coords." + playerworld + ".spawn-x-coordinate");
            spawnZ = WorldSpawns.getWorldSpawnsConfig().getInt("spawn-coords." + playerworld + ".spawn-z-coordinate");
        }
        double newX = 0;
        double newZ = 0;
        int newY = 0;

        World world = player.getWorld();
        Location newLocation;

        boolean search = true;
        while (search && attempts++ < 100) {
            newX = getRandomCoord(playerX, spawnX, plugin);
            newZ = getRandomCoord(playerZ, spawnZ, plugin);
            newY = getTopBlock(newX, newZ, player, plugin);

            newLocation = new Location(world, newX, newY, newZ);
            search = !isSafe(newLocation, newY, plugin);
        }

        if (attempts != 100) {
            newLocation = new Location(world, newX + 0.5, newY + 2, newZ + 0.5);
            player.teleport(newLocation);
            player.sendTitle(GeneralHelper.getPrefix().replace(" ", ""), GeneralHelper.translateColors(Messages.getConfig().getString("successful-teleportation")), 40, 45, 20);
            TeleportCooldown.setCooldown(player, plugin.getConfig().getInt("cooldown-time"));
        } else {
            player.sendMessage(GeneralHelper.getPrefix() + GeneralHelper.translateColors(Messages.getConfig().getString("unsuccessful-teleportation").replace("{player}", player.getName())));
        }
    }

    public static double getRandomCoord(double playerCoord, int spawnCoord, BeanRTP plugin) {
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

    public static boolean isSafe(Location location, int y, BeanRTP plugin) {

        if (y == -1) {
            return false;
        }

        World world = location.getWorld();
        double x = location.getX();
        double z = location.getZ();

        Material blockTypeAtY = location.getBlock().getType();

        if (!((blockTypeAtY != Material.LAVA) && (blockTypeAtY != Material.CACTUS) && (blockTypeAtY != Material.MAGMA_BLOCK) && (blockTypeAtY != Material.FIRE) && (blockTypeAtY != Material.WATER) && (y != -1))) {
            return false;
        }

        if (isNether(location.getWorld(), plugin)) {
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

    public static int getTopBlock(double x, double z, Player player, BeanRTP plugin) {
        int y = -1;

        if (isNether(player.getWorld(), plugin)) {
            int max = 120;

            for (int i = 0; i < max; i++) {
                Location location = new Location(player.getWorld(), x, i, z);
                y = i-1;
                if (location.getBlock().getType() == Material.AIR) {
                    break;
                }
            }
        } else {
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

    public static boolean isNether(World world, BeanRTP plugin) {
        return plugin.getConfig().getStringList("nether-worlds").contains(world.getName());
    }


    public static void appliedEffects(Player player, BeanRTP plugin) {
        if (isNether(player.getWorld(), plugin)) {
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