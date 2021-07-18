package com.beanbeanjuice.beanrtp.managers.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public interface ICommand extends CommandExecutor {

    /**
     * The thing that runs when the command is called.
     * @param sender The person/object that sent the command.
     * @param cmd The command itself.
     * @param label The command label.
     * @param args The arguments of the command.
     * @return Whether the command has run or not.
     */
    @Override
    boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args);

    /**
     * @return The name of the {@link ICommand Command}.
     */
    String getName();

    /**
     * Gets the permissions that should be had in the {@link ICommand command}.
     * @return The {@link ArrayList <String>} of permissions.
     */
    ArrayList<String> getPermissions();

    /**
     * Returns the command usage for the command.
     * @return {@link CommandUsage}.
     */
    Usage getCommandUsage();

    /**
     * Checks the command arguments, making sure it can be run with no problems.
     * @param command The {@link ICommand}.
     * @param sender The person who sent the command.
     * @param arguments The command arguments.
     * @return Whether or not the command can be run or not.
     */
    boolean checkArgs(ICommand command, CommandSender sender, String[] arguments);

}
