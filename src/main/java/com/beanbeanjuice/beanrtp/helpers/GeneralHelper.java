package com.beanbeanjuice.beanrtp.helpers;

import com.beanbeanjuice.beanrtp.BeanRTP;
import org.jetbrains.annotations.NotNull;

/**
 * A general helper class.
 *
 * @author beanbeanjuice
 */
public class GeneralHelper {

    private BeanRTP plugin;

    /**
     * Creates a new {@link GeneralHelper} object.
     * @param plugin The current {@link BeanRTP} plugin.
     */
    public GeneralHelper(BeanRTP plugin) {
        this.plugin = plugin;
    }

    /**
     * @return The current prefix for the {@link BeanRTP} plugin.
     */
    @NotNull
    public String getPrefix() {
        return translateColors(plugin.getConfig().getString("prefix")) + " ";
    }

    /**
     * Translates colors according to color code.
     * @param string The {@link String} to be translated.
     * @return The translated {@link String}.
     */
    @NotNull
    public String translateColors(@NotNull String string) {
        return string.replaceAll("&", "§");
    }

    /**
     * @return The current {@link BeanRTP} plugin.
     */
    @NotNull
    public BeanRTP getPlugin() {
        return plugin;
    }

    /**
     * @return The "No Permission" {@link String}.
     */
    @NotNull
    public String getNoPermission() {
        return translateColors(BeanRTP.getMessages().getConfig().getString("no-permission"));
    }

}
