package com.beanbeanjuice.beanrtp.utility.config;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.dvs.versioning.BasicVersioning;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public abstract class Config {

    protected YamlDocument yamlConfig;
    private final File configFolder;
    protected final HashMap<ConfigDataKey, ConfigDataEntry> config;
    private final String fileName;

    public Config(String fileName, File configFolder) {
        this.fileName = fileName;
        this.configFolder = configFolder;
        config = new HashMap<>();
    }

    public void initialize() {
        try {
            yamlConfig = loadConfig();
            yamlConfig.update();
            yamlConfig.save();
            readConfig();
        } catch (IOException ignored) { }
    }

    public Object get(ConfigDataKey key) {
        return config.get(key).data();
    }

    abstract void readConfig() throws IOException;

    private YamlDocument loadConfig() throws IOException {
        return YamlDocument.create(
                new File(configFolder, fileName),
                Objects.requireNonNull(getClass().getResourceAsStream("/" + fileName)),
                GeneralSettings.DEFAULT,
                LoaderSettings.builder().setAutoUpdate(true).build(),
                DumperSettings.DEFAULT,
                UpdaterSettings.builder().setVersioning(new BasicVersioning("file-version"))
                        .setOptionSorting(UpdaterSettings.OptionSorting.SORT_BY_DEFAULTS).build()
        );
    }

    public void overwrite(ConfigDataKey key, ConfigDataEntry entry) {
        config.put(key, entry);
    }

}
