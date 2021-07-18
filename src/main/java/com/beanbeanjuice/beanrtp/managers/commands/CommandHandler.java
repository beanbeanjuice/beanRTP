package com.beanbeanjuice.beanrtp.managers.commands;

import com.beanbeanjuice.beanrtp.BeanRTP;
import com.beanbeanjuice.beanrtp.managers.commands.object.ArgumentAmount;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.logging.Level;

public class CommandHandler {

    private final BeanRTP plugin;
    private final ArrayList<ICommand> commands;

    /**
     * Create a new {@link CommandHandler} object.
     * @param plugin The actual {@link org.bukkit.plugin.Plugin Plugin} used.
     */
    public CommandHandler(BeanRTP plugin) {
        this.plugin = plugin;
        commands = new ArrayList<>();
    }

    /**
     * Add a new command to be handled.
     * @param command The {@link ICommand command} to be added.
     */
    public void addCommand(ICommand command) {
        commands.add(command);
    }

    /**
     * Makes sure that all commands are initialized and properly being handled.
     */
    public void initializeCommands() {
        for (ICommand command : commands) {
            Bukkit.getLogger().log(Level.INFO, "Adding Command");
            Bukkit.getLogger().log(Level.INFO, command.getName());
            plugin.getCommand(command.getName()).setExecutor(command);
        }
    }

    /**
     * Checks the arguments for each command.
     * @param command The {@link ICommand command} being send.
     * @param sender The object that sent the command.
     * @param arguments The arguments for that particular {@link ICommand command} entered.
     * @return Whether or not the {@link ICommand command} can be sent.
     */
    public boolean checkArguments(ICommand command, CommandSender sender, String[] arguments) {
        for (ICommand commandOriginal : commands) {
            if (command.equals(commandOriginal)) {

                ArgumentAmount argumentAmount = commandOriginal.getCommandUsage().checkTotal(arguments);

                switch (argumentAmount) {
                    case CORRECT_AMOUNT: {
                        break;
                    }

                    case NOT_ENOUGH:

                    case TOO_MANY: {
                        sender.sendMessage(BeanRTP.getHelper().getPrefix() +
                                argumentAmount.getMessage());
                        return false;
                    }
                }

                int length = arguments.length;
                for (int i = 0; i < length; i++) {
                    boolean allowed = commandOriginal.getCommandUsage().checkUsage(arguments[i], i);
                    if (!allowed) {
                        String message = BeanRTP.getHelper().getPrefix() +
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
