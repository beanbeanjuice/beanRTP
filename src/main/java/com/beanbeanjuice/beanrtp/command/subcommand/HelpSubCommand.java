package com.beanbeanjuice.beanrtp.command.subcommand;

import com.beanbeanjuice.beanrtp.config.Messages;
import com.beanbeanjuice.beanrtp.utility.Helper;
import com.beanbeanjuice.beanrtp.utility.ISubCommand;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

/**
 * Displays the help command to the user.
 *
 * @author beanbeanjuice
 * @since v3.0.0
 */
public class HelpSubCommand implements ISubCommand {

    @Override
    public boolean handle(@NotNull CommandSender sender, @NotNull String[] args) {
        Helper.sendMessage(sender, String.join("\n", Messages.getConfig().getStringList("help")));
        return true;
    }

    @Override
    public String getPermission() {
        return "beanRTP.help";
    }

}
