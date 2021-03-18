package com.github.beanbeanjuice.beanrtp.utilities.interfaces;

import com.github.beanbeanjuice.beanrtp.beanRTP;
import com.github.beanbeanjuice.beanrtp.utilities.usages.CommandUsage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public interface CommandInterface extends CommandExecutor {

    /**
     * The thing that runs when the command is called.
     * @param sender The person/object that sent the command.
     * @param cmd The command itself.
     * @param label The command label.
     * @param args The arguments of the command.
     * @return Whether the command has run or not.
     */
    @Override
    boolean onCommand(CommandSender sender, Command cmd, String label, String[] args);

    /**
     * @return The name of the {@link CommandInterface Command}.
     */
    String getName();

    /**
     * Gets the permissions that should be had in the {@link CommandInterface command}.
     * @return The {@link ArrayList <String>} of permissions.
     */
    ArrayList<String> getPermissions();

    /**
     * Returns the command usage for the command.
     * @return {@link CommandUsage}.
     */
    CommandUsage getCommandUsage();

    /**
     * Checks the command arguments, making sure it can be run with no problems.
     * @param command The {@link CommandInterface}.
     * @param sender The person who sent the command.
     * @param arguments The command arguments.
     * @return Whether or not the command can be run or not.
     */
    default boolean checkArgs(CommandInterface command, CommandSender sender, String[] arguments) {
        return beanRTP.getCommandUsageHandler().checkArguments(command, sender, arguments);
    }

}
