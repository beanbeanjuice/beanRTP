package com.beanbeanjuice.beanrtp.command.subcommand;

import com.beanbeanjuice.beanrtp.utility.Helper;
import com.beanbeanjuice.beanrtp.utility.ISubCommand;
import com.beanbeanjuice.beanrtp.utility.config.ConfigDataKey;
import org.bukkit.command.CommandSender;

public class ReloadSubCommand implements ISubCommand {

    @Override
    public boolean handle(CommandSender sender, String[] args) {
        if (args.length != 1) {
            Helper.sendUnknownCommand(sender);
            return false;
        }

        Helper.getPlugin().getPluginConfig().initialize();
        Helper.getPlugin().getMessageConfig().initialize();

        Helper.sendMessage(sender, (String) Helper.getPlugin().getMessageConfig().get(ConfigDataKey.SUCCESSFUL_RELOAD_MESSAGE));

        return true;
    }

    @Override
    public String getPermission() {
        return "beanRTP.reload";
    }

}
