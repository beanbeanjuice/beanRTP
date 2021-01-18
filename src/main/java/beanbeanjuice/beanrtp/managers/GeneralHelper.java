package beanbeanjuice.beanrtp.managers;

import beanbeanjuice.beanrtp.BeanRTP;
import beanbeanjuice.beanrtp.managers.filemanagers.Messages;

public class GeneralHelper {

    private static String prefix;
    private static String nopermission;

    public GeneralHelper(BeanRTP plugin) {
        prefix = translateColors(plugin.getConfig().getString("prefix")) + " ";
        nopermission = translateColors(Messages.getConfig().getString("no-permission"));
    }

    public static String translateColors(String string) {
        return string.replaceAll("&", "§");
    }

    public static String getConsolePrefix() {
        return "[beanRTP] ";
    }

    public static String getPrefix() {
        return prefix;
    }

    public static String getNoPermission() {
        return nopermission;
    }

}