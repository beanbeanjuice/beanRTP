package com.beanbeanjuice.beanrtp.command;

import com.beanbeanjuice.beanrtp.BeanRTP;
import com.beanbeanjuice.beanrtp.command.subcommand.HelpSubCommand;
import com.beanbeanjuice.beanrtp.command.subcommand.ReloadSubCommand;
import com.beanbeanjuice.beanrtp.command.subcommand.TeleportOthersSubCommand;
import com.beanbeanjuice.beanrtp.command.subcommand.TeleportSelfSubCommand;
import com.beanbeanjuice.beanrtp.utility.Helper;
import com.beanbeanjuice.beanrtp.utility.ISubCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;

public class RTPCommand implements CommandExecutor {

    private final BeanRTP plugin;
    private final HashMap<String, ISubCommand> subCommands = new HashMap<>() {{
        put("self", new TeleportSelfSubCommand());
        put("others", new TeleportOthersSubCommand());
        put("help", new HelpSubCommand());
        put("reload", new ReloadSubCommand());
    }};

    public RTPCommand(BeanRTP plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        getSubCommand(args).ifPresentOrElse(
                (subCommand) -> {
                    if (checkPermissions(commandSender, subCommand)) subCommand.handle(commandSender, args);
                    else Helper.sendNoPermission(commandSender);
                },
                () -> Helper.sendUnknownCommand(commandSender)
        );
        return true;
    }

    private Optional<ISubCommand> getSubCommand(String[] args) {
        if (args.length > 1) return Optional.empty();

        if (args.length == 0) return Optional.of(subCommands.get("self"));
        if (Bukkit.getPlayer(args[0]) != null) return Optional.of(subCommands.get("others"));
        if (subCommands.containsKey(args[0])) return Optional.of(subCommands.get(args[0]));

        return Optional.empty();
    }

    private boolean checkPermissions(CommandSender sender, ISubCommand command) {
        return sender.isOp() || sender.hasPermission(command.getPermission());
    }

}
