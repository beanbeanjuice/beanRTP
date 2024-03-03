package com.beanbeanjuice.beanrtp.command.subcommand;

import com.beanbeanjuice.beanrtp.utility.Helper;
import com.beanbeanjuice.beanrtp.utility.ISubCommand;
import com.beanbeanjuice.beanrtp.utility.config.ConfigDataKey;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * Displays the help command to the user.
 *
 * @author beanbeanjuice
 * @since v3.0.0
 */
public class HelpSubCommand implements ISubCommand {

    @Override
    public boolean handle(CommandSender sender, String[] args) {
        Helper.sendMessage(sender, String.join("\n", (List<String>) Helper.getPlugin().getMessageConfig().get(ConfigDataKey.HELP_MESSAGE)));
        return true;
    }

    @Override
    public String getPermission() {
        return "beanRTP.help";
    }

}
