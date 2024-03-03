package com.beanbeanjuice.beanrtp.utility.config;

import com.beanbeanjuice.beanrtp.utility.Helper;

import java.io.File;

public class PluginConfig extends Config {

    public PluginConfig(File configFolder) {
        super("config.yml", configFolder);
    }

    void readConfig() {
        config.put(ConfigDataKey.PREFIX, new ConfigDataEntry(Helper.translateColors(yamlConfig.getString("prefix"))));
        config.put(ConfigDataKey.MINIMUM_DISTANCE_FROM_BORDER_CENTER, new ConfigDataEntry(yamlConfig.getInt("minimum-distance-from-border-center")));
        config.put(ConfigDataKey.ALLOWED_WORLDS, new ConfigDataEntry(yamlConfig.getStringList("allowed-worlds")));
        config.put(ConfigDataKey.COOLDOWN_TIME, new ConfigDataEntry(yamlConfig.getInt("cooldown-time")));
        config.put(ConfigDataKey.COUNTDOWN_TIME, new ConfigDataEntry(yamlConfig.getInt("countdown-time")));
    }
}
