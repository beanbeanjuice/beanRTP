package com.beanbeanjuice.beanrtp.config;

import com.beanbeanjuice.beanrtp.BeanRTP;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.logging.Level;

public class Messages {

    private static File configFile;
    private static FileConfiguration config;

    public static void createConfig(BeanRTP plugin) {
        configFile = new File(plugin.getDataFolder(), "messages.yml");

        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            plugin.saveResource("messages.yml", false);
        }

        config = new YamlConfiguration();

        try { config.load(configFile); }
        catch (Exception e) { plugin.getLogger().log(Level.SEVERE, e.getMessage(), e); }
    }

    @NotNull
    public static FileConfiguration getConfig() {
        return config;
    }

    public static void reloadConfig(BeanRTP plugin) {
        try { config.load(configFile); }
        catch (Exception e) { plugin.getLogger().log(Level.SEVERE, e.getMessage(), e); }
    }

}
