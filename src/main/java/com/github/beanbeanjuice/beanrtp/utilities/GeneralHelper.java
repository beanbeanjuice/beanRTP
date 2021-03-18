package com.github.beanbeanjuice.beanrtp.utilities;

import com.github.beanbeanjuice.beanrtp.beanRTP;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class GeneralHelper {

    private final String prefix;
    private final String noPermission;
    private final beanRTP plugin;

    /**
     * Create a new {@link GeneralHelper} object.
     * @param plugin Returns the actual {@link org.bukkit.plugin.Plugin Plugin}.
     */
    public GeneralHelper(beanRTP plugin) {
        this.plugin = plugin;
        prefix = translateColors(plugin.getConfig().getString("prefix")) + " ";
        noPermission = translateColors(plugin.getConfig().getString("no-permission"));
    }

    /**
     * Translate Minecraft color codes.
     * @param string The {@link String} message you want to convert.
     * @return The converted message {@link String}.
     */
    public String translateColors(String string) {
        return string.replaceAll("&", "§");
    }

    /**
     * @return The current {@link org.bukkit.plugin.Plugin Plugin}.
     */
    public beanRTP getPlugin() {
        return plugin;
    }

    /**
     * @return The current, parsed, prefix.
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * @return The "no-permission" message from the config.
     */
    public String getNoPermission() {
        return noPermission;
    }

    /**
     * @param player The name of the {@link Player}.
     * @return The "player-not-found" {@link String} in the config.
     */
    public String playerNotFound(String player) {
        return getConfigString("player-not-found").replace("%player%", player);
    }

    /**
     * Gets a particular, parsed, string from the main config.
     * @param identifier The identifier used for YML files.
     * @return The {@link String} message found in the config.
     */
    public String getConfigString(String identifier) {
        return translateColors(plugin.getConfig().getString(identifier));
    }

    /**
     * Gets a particular integer value from the main config.
     * @param identifier The identifier used for YML files.
     * @return The {@link Integer} found in the config.
     */
    public int getConfigInt(String identifier) {
        return plugin.getConfig().getInt(identifier);
    }

    /**
     * Gets a particular boolean value from the main config.
     * @param identifier The identifier used for YML files.
     * @return The {@link Boolean} found in the config.
     */
    public boolean getConfigBoolean(String identifier) {
        return plugin.getConfig().getBoolean(identifier);
    }

    /**
     * Gets a particular {@link ArrayList<String>} from the main config.
     * @param identifier The identifier used for YML files.
     * @return The {@link ArrayList<String>} found in the config.
     */
    public ArrayList<String> getConfigStringList(String identifier) {
        ArrayList<String> arrayList = new ArrayList<>();

        for (String string : plugin.getConfig().getStringList(identifier)) {
            arrayList.add(translateColors(string));
        }
        return arrayList;
    }

    public boolean hasPermission(Player player, String permission) {
        return player.hasPermission(permission);
    }

}
