package com.beanbeanjuice.beanrtp.managers.commands;

import com.beanbeanjuice.beanrtp.managers.commands.object.ArgumentAmount;
import com.beanbeanjuice.beanrtp.managers.commands.object.UsageType;
import org.bukkit.Bukkit;

import java.util.ArrayList;

public class Usage {

    /**
     * Each argument for the command will have its own {@link CommandUsage}.
     */
    private ArrayList<CommandUsage> usages;

    /**
     * Create a new {@link CommandUsage} object.
     * This {@link CommandUsage} object will contain
     * an {@link ArrayList<CommandUsage>} which contains all
     * of the {@link CommandUsage usages}.
     */
    public Usage() {
        usages = new ArrayList<>();
    }

    /**
     * Add a {@link CommandUsage}.
     * @param argumentName The name of the argument to be used. It is arbitrary.
     * @param usageType The {@link UsageType} to be used.
     * @param required Whether or not this particular {@link CommandUsage} is required.
     */
    public void addUsage(String argumentName, UsageType usageType, boolean required) {
        usages.add(new CommandUsage(argumentName, usageType, required));
    }

    /**
     * @return Gets the {@link ArrayList<CommandUsage>} for this particular {@link ICommand command}.
     */
    public ArrayList<CommandUsage> getUsages() {
        return usages;
    }

    /**
     * Gets the total amount of required arguments.
     * @param arguments The arguments found in the {@link ICommand command}.
     * @return The {@link ArgumentAmount} enum.
     */
    public ArgumentAmount checkTotal(String[] arguments) {
        int totalRequired = 0;

        for (CommandUsage value : usages) {
            if (value.isRequired()) {
                totalRequired++;
            }
        }

        if (arguments.length > usages.size()) {
            return ArgumentAmount.TOO_MANY;
        }

        if (arguments.length < totalRequired) {
            return ArgumentAmount.NOT_ENOUGH;
        }

        return ArgumentAmount.CORRECT_AMOUNT;
    }

    /**
     * Checks the {@link CommandUsage} for that particular argument.
     * @param argument The argument that the {@link org.bukkit.entity.Player} has entered.
     * @param index The index of that particular {@link ICommand command} argument.
     * @return Whether or not the {@link CommandUsage} for that particular argument checks out.
     */
    public boolean checkUsage(String argument, int index) {

        CommandUsage usage = usages.get(index);
        switch (usage.getUsageType().getName()) {
            case ("text"):

            case ("other"): {
                return true;
            }

            case ("number"): {

                if (!usage.isRequired() && argument.isEmpty()) {
                    return true;
                }

                return parseNumber(argument);
            }

            case ("player"): {

                if (!usage.isRequired() && argument.isEmpty()) {
                    return true;
                }

                return parsePlayer(argument);
            }

            case ("world"): {

                if (!usage.isRequired() && argument.isEmpty()) {
                    return true;
                }

                return parseWorld(argument);
            }

            default: {
                return false;
            }

        }
    }

    /**
     * Used for checking if the argument name is a {@link org.bukkit.World World}.
     * @param argument The {@link org.bukkit.World World} name.
     * @return Whether or not the argument is a {@link org.bukkit.World World}.
     */
    private boolean parseWorld(String argument) {
        return Bukkit.getWorld(argument) != null;
    }

    /**
     * Used for checking if the argument name is a {@link org.bukkit.entity.Player Player}.
     * @param argument The {@link org.bukkit.entity.Player Player} name.
     * @return Whether or not the argument is a {@link org.bukkit.entity.Player Player}.
     */
    private boolean parsePlayer(String argument) {
        return Bukkit.getPlayer(argument) != null;
    }

    /**
     * Used for checking if the argument name is a {@link Integer}.
     * @param argument The {@link Integer} number used.
     * @return Whether or not the argument is a {@link Integer}.
     */
    private boolean parseNumber(String argument) {
        if (argument == null) {
            return false;
        }

        try {
            double d = Double.parseDouble(argument);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
