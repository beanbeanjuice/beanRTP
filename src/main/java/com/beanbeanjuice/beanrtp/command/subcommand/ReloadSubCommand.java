package com.beanbeanjuice.beanrtp.command.subcommand;

import com.beanbeanjuice.beanrtp.utility.Helper;
import com.beanbeanjuice.beanrtp.utility.ISubCommand;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ReloadSubCommand implements ISubCommand {

    @Override
    public boolean handle(@NotNull CommandSender sender, @NotNull String[] args) {
        if (args.length != 1) {
            Helper.sendUnknownCommand(sender);
            return false;
        }

        Helper.sendMessage(sender, "Handling reload! WIP");
        return true;
    }

    @Override
    public String getPermission() {
        return "beanRTP.reload";
    }

}
