package com.beanbeanjuice.beanrtp.managers.filemanagers;

import com.beanbeanjuice.beanrtp.BeanRTP;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

/**
 * A class used for handling world spawns.
 *
 * @author beanbeanjuice
 */
public class WorldSpawns {

    private final String FILE_PATH = ("plugins/BeanRTP");
    private BeanRTP plugin;

    /**
     * Creates a new {@link WorldSpawns} object.
     * @param plugin The current {@link BeanRTP} plugin.
     */
    public WorldSpawns(BeanRTP plugin) {
        this.plugin = plugin;

        File file = new File(FILE_PATH + "/worldspawns.yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
                FileConfiguration f = YamlConfiguration.loadConfiguration(file);
                f.createSection("spawn-coords");
                f.set("spawn-coords.world.spawn-x-coordinate", 0);
                f.set("spawn-coords.world.spawn-z-coordinate", 0);
                f.save(file);
            } catch (IOException e) {
               plugin.getLogger().warning("Unable to create the worldspawns.yml");
            }
        }
    }

    /**
     * @return The current {@link WorldSpawns} config.
     */
    @NotNull
    public FileConfiguration getConfig() {
        File file = new File(FILE_PATH + "/worldspawns.yml");
        return YamlConfiguration.loadConfiguration(file);
    }

    /**
     * Sets the world spawn.
     * @param location The {@link Location} of the world spawn.
     */
    public void setWorldSpawn(@NotNull Location location) {
        int x = location.getBlockX();
        int z = location.getBlockZ();
        String world = location.getWorld().getName();
        FileConfiguration config = getConfig();
        config.set("spawn-coords." + world + ".spawn-z-coordinate", z);
        config.set("spawn-coords." + world + ".spawn-x-coordinate", x);
        try {
            config.save(new File(FILE_PATH + "/worldspawns.yml"));
        } catch (IOException e) {
            plugin.getLogger().warning("Unable to set spawn in the config.");
        }
    }

}
