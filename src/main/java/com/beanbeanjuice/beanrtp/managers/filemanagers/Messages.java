package com.beanbeanjuice.beanrtp.managers.filemanagers;

import com.beanbeanjuice.beanrtp.BeanRTP;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

/**
 * A class used for handling the messages config.
 *
 * @author beanbeanjuice
 */
public class Messages {

    private File configFile;
    private FileConfiguration config;
    private BeanRTP plugin;

    /**
     * Creates a new {@link Messages} object.
     * @param plugin The current {@link BeanRTP} plugin.
     */
    public Messages(BeanRTP plugin) {
        this.plugin = plugin;
        createConfig();
    }

    /**
     * Creates the {@link Messages} config.
     */
    private void createConfig() {
        configFile = new File(plugin.getDataFolder(), "messages.yml");
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            plugin.saveResource("messages.yml", false);
        }

        config = new YamlConfiguration();

        try {
            config.load(configFile);
        } catch (IOException | InvalidConfigurationException e) {
            plugin.getLogger().warning("Unable to create the messages.yml");
        }
    }

    /**
     * @return The current {@link Messages} config.
     */
    @NotNull
    public FileConfiguration getConfig() {
        return config;
    }

    /**
     * Reloads the {@link Messages} config.
     */
    public void reloadConfig() {
        try {
            config.load(configFile);
        } catch (Exception e) {
            plugin.getLogger().warning("Unable to reload the messages.yml file.");
        }
    }

}
