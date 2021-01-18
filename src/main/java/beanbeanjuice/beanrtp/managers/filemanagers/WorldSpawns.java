package beanbeanjuice.beanrtp.managers.filemanagers;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;

public class WorldSpawns {
    private static String filepath = ("plugins/BeanRTP");

    public static void setupWorldSpawns() {
        File file = new File(filepath + "/worldspawns.yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
                FileConfiguration f = YamlConfiguration.loadConfiguration(file);
                f.createSection("spawn-coords");
                f.set("spawn-coords.world.spawn-x-coordinate", 0);
                f.set("spawn-coords.world.spawn-z-coordinate", 0);
                f.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static FileConfiguration getWorldSpawnsConfig() {
        File file = new File(filepath + "/worldspawns.yml");
        return YamlConfiguration.loadConfiguration(file);
    }

    public static void setWorldSpawn(Location location) {
        int x = location.getBlockX();
        int z = location.getBlockZ();
        String world = location.getWorld().getName();
        FileConfiguration config = getWorldSpawnsConfig();
        config.set("spawn-coords." + world + ".spawn-z-coordinate", z);
        config.set("spawn-coords." + world + ".spawn-x-coordinate", x);
        try {
            config.save(new File(filepath + "/worldspawns.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}