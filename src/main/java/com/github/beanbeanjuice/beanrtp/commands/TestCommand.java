package com.github.beanbeanjuice.beanrtp.commands;

import com.github.beanbeanjuice.beanrtp.utilities.interfaces.CommandInterface;
import com.github.beanbeanjuice.beanrtp.utilities.usages.CommandUsage;
import com.github.beanbeanjuice.beanrtp.utilities.usages.enums.UsageType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class TestCommand implements CommandInterface {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!checkArgs(this, sender, args)) {
            return false;
        }

        if (sender instanceof Player) {
            Player player = (Player) sender;
            player.sendMessage("test complete");
            return true;
        }

        return false;
    }

    @Override
    public String getName() {
        return "testcommand";
    }

    @Override
    public ArrayList<String> getPermissions() {
        ArrayList<String> permissionsArrayList = new ArrayList<>();
        permissionsArrayList.add("testplugin.testcommand");
        return permissionsArrayList;
    }

    @Override
    public CommandUsage getCommandUsage() {
        CommandUsage usage = new CommandUsage();
        usage.addUsage("test", UsageType.NUMBER, true);
        usage.addUsage("test2", UsageType.NUMBER, false);
        return usage;
    }
}
