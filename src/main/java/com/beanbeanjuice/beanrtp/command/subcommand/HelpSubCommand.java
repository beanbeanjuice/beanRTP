package com.beanbeanjuice.beanrtp.command.subcommand;

import com.beanbeanjuice.beanrtp.config.Messages;
import com.beanbeanjuice.beanrtp.utility.Helper;
import com.beanbeanjuice.beanrtp.utility.ISubCommand;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class HelpSubCommand implements ISubCommand {

    @Override
    public boolean handle(@NotNull CommandSender sender, @NotNull String[] args) {
        Helper.sendMessage(sender, Helper.getMessageConfig("help-message"));

        Messages.getConfig()
                .getStringList("help")
                .forEach((string) -> Helper.sendMessage(sender, Helper.translateColors(string)));
        return true;
    }

    @Override
    public String getPermission() {
        return "beanRTP.help";
    }

}
