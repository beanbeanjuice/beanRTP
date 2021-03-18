package com.github.beanbeanjuice.beanrtp.managers.configs;

import java.util.ArrayList;

public class ConfigHandler {

    ArrayList<String> configs;
    private String config;
    private String messages;

    /**
     * Creates a new {@link ConfigHandler} object.
     */
    public ConfigHandler() {
        config = ("config.yml");
        messages = ("messages.yml");

        configs = new ArrayList<>();
        configs.add(config);
        configs.add(messages);
    }

    /**
     * Makes sure configs are up to date.
     * @param configName The name of that config.
     */
    public void checkConfig(String configName) {

        for (String string : configs) {
            if (string.equals(configName)) {
                // TODO: Check Config
                // Check version numbers
                // if different, fix.
            }
        }

    }

}
