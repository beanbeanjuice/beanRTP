package com.beanbeanjuice.beanrtp.command;

import com.beanbeanjuice.beanrtp.BeanRTP;
import com.beanbeanjuice.beanrtp.command.subcommand.TeleportOthersSubCommand;
import com.beanbeanjuice.beanrtp.command.subcommand.TeleportSelfSubCommand;
import com.beanbeanjuice.beanrtp.utility.Helper;
import com.beanbeanjuice.beanrtp.utility.ISubCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;

public class RTPCommand implements CommandExecutor {

    private final BeanRTP plugin;
    private final HashMap<String, ISubCommand> subCommands = new HashMap<>() {{
        put("self", new TeleportSelfSubCommand());
        put("others", new TeleportOthersSubCommand());
    }};

    public RTPCommand(BeanRTP plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (args.length > 1) {
            Helper.sendUnknownCommand(commandSender);
            return false;
        }

        if (args.length == 0) return subCommands.get("self").handle(commandSender, args);
        if (Bukkit.getPlayer(args[0]) != null) return subCommands.get("others").handle(commandSender, args);
        if (subCommands.containsKey(args[0])) return subCommands.get(args[0]).handle(commandSender, args);

        return false;
    }

}
