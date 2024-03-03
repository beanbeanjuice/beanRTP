package com.beanbeanjuice.beanrtp.utility.config;

import com.beanbeanjuice.beanrtp.utility.Helper;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class MessageConfig extends Config {

    public MessageConfig(File configFolder) {
        super("messages.yml", configFolder);
    }

    void readConfig() {
        config.put(ConfigDataKey.NO_PERMISSION_MESSAGE, new ConfigDataEntry(Helper.translateColors(yamlConfig.getString("no-permission"))));
        config.put(ConfigDataKey.STARTING_TELEPORTATION_MESSAGE, new ConfigDataEntry(Helper.translateColors(yamlConfig.getString("starting-teleportation"))));
        config.put(ConfigDataKey.SUCCESSFUL_TELEPORTATION_MESSAGE, new ConfigDataEntry(Helper.translateColors(yamlConfig.getString("successful-teleportation"))));
        config.put(ConfigDataKey.UNSUCCESSFUL_TELEPORTATION_MESSAGE, new ConfigDataEntry(Helper.translateColors(yamlConfig.getString("unsuccessful-teleportation"))));
        config.put(ConfigDataKey.OTHER_SUCCESSFUL_TELEPORTATION_MESSAGE, new ConfigDataEntry(Helper.translateColors(yamlConfig.getString("other-successful-teleportation"))));
        config.put(ConfigDataKey.OTHER_UNSUCCESSFUL_TELEPORTATION_MESSAGE, new ConfigDataEntry(Helper.translateColors(yamlConfig.getString("other-unsuccessful-teleportation"))));
        config.put(ConfigDataKey.MOVED_DURING_TELEPORT_MESSAGE, new ConfigDataEntry(Helper.translateColors(yamlConfig.getString("moved-during-teleport"))));
        config.put(ConfigDataKey.COOLDOWN_MESSAGE, new ConfigDataEntry(Helper.translateColors(yamlConfig.getString("cooldown"))));
        config.put(ConfigDataKey.SUCCESSFUL_RELOAD_MESSAGE, new ConfigDataEntry(Helper.translateColors(yamlConfig.getString("successful-reload"))));
        config.put(ConfigDataKey.UNKNOWN_COMMAND_MESSAGE, new ConfigDataEntry(Helper.translateColors(yamlConfig.getString("unknown-command"))));
        config.put(ConfigDataKey.NOT_ALLOWED_WORLD_MESSAGE, new ConfigDataEntry(Helper.translateColors(yamlConfig.getString("not-allowed-world"))));
        config.put(ConfigDataKey.NOT_ALLOWED_WORLD_OTHERS_MESSAGE, new ConfigDataEntry(Helper.translateColors(yamlConfig.getString("not-allowed-world-others"))));
        config.put(ConfigDataKey.ALREADY_TELEPORTING_MESSAGE, new ConfigDataEntry(Helper.translateColors(yamlConfig.getString("already-teleporting"))));

        List<String> helpList = yamlConfig.getStringList("help").stream().map(Helper::translateColors).collect(Collectors.toList());

        config.put(ConfigDataKey.HELP_MESSAGE, new ConfigDataEntry(helpList));
    }
}
