package com.beanbeanjuice.beanrtp.commands;

import com.beanbeanjuice.beanrtp.BeanRTP;
import com.beanbeanjuice.beanrtp.managers.commands.ICommand;
import com.beanbeanjuice.beanrtp.managers.commands.Usage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RandomTeleportCommand implements ICommand {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {

        if (!checkArgs(this, sender, args)) {
            return false;
        }

        sender.sendMessage("It worked!");
        return true;
    }

    @Override
    public String getName() {
        return "random-teleport";
    }

    @Override
    public ArrayList<String> getPermissions() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("randomteleport");
        arrayList.add("rtp");
        arrayList.add("randomtp");
        return arrayList;
    }

    @Override
    public Usage getCommandUsage() {
        return new Usage();
    }

    @Override
    public boolean checkArgs(ICommand command, CommandSender sender, String[] arguments) {
        return BeanRTP.getCommandHandler().checkArguments(command, sender, arguments);
    }
}
