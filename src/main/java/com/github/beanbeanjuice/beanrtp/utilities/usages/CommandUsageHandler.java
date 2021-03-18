package com.github.beanbeanjuice.beanrtp.utilities.usages;

import com.github.beanbeanjuice.beanrtp.beanRTP;
import com.github.beanbeanjuice.beanrtp.utilities.interfaces.CommandInterface;
import com.github.beanbeanjuice.beanrtp.utilities.usages.enums.ArgumentAmount;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public class CommandUsageHandler {

    private final beanRTP plugin;
    private ArrayList<CommandInterface> commands;

    /**
     * Create a new {@link CommandUsageHandler} object.
     * @param plugin The actual {@link org.bukkit.plugin.Plugin Plugin} used.
     */
    public CommandUsageHandler(beanRTP plugin) {
        this.plugin = plugin;
        commands = new ArrayList<>();
    }

    /**
     * Add a new command to be handled.
     * @param command The {@link CommandInterface command} to be added.
     */
    public void addCommand(CommandInterface command) {
        commands.add(command);
    }

    /**
     * Makes sure that all commands are initialized and properly being handled.
     */
    public void initializeCommands() {
        for (CommandInterface command : commands) {
            plugin.getCommand(command.getName()).setExecutor(command);
        }
    }

    /**
     * Checks the arguments for each command.
     * @param command The {@link CommandInterface command} being send.
     * @param sender The object that sent the command.
     * @param arguments The arguments for that particular {@link CommandInterface command} entered.
     * @return Whether or not the {@link CommandInterface command} can be sent.
     */
    public boolean checkArguments(CommandInterface command, CommandSender sender, String[] arguments) {
        for (CommandInterface commandOriginal : commands) {
            if (command.equals(commandOriginal)) {

                ArgumentAmount argumentAmount = commandOriginal.getCommandUsage().checkTotal(arguments);

                switch (argumentAmount) {
                    case CORRECT_AMOUNT: {
                        break;
                    }

                    case NOT_ENOUGH:

                    case TOO_MANY: {
                        sender.sendMessage(beanRTP.getHelper().getPrefix() +
                                argumentAmount.getMessage());
                        return false;
                    }
                }

                int length = arguments.length;
                for (int i = 0; i < length; i++) {
                    boolean allowed = commandOriginal.getCommandUsage().checkUsage(arguments[i], i);
                    if (!allowed) {
                        String message = beanRTP.getHelper().getPrefix() +
                                commandOriginal.getCommandUsage().getUsages().get(i)
                                        .getUsageType().getMessage().replaceAll("%argument%", arguments[i]);

                        message = message.replaceAll("%help%", command.getCommandUsage().getUsages().get(i).getHelp());
                        sender.sendMessage(message);
                        return false;
                    }
                }

                return true;
            }
        }
        return false;
    }

}
