package beanbeanjuice.beanrtp.managers;

import beanbeanjuice.beanrtp.BeanRTP;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class TabCompletor implements TabCompleter {

    private BeanRTP plugin;

    public TabCompletor(BeanRTP plugin) {
        this.plugin = plugin;
        plugin.getCommand("rtp").setTabCompleter(this);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        if (args.length == 1) {
            ArrayList<String> tabList = new ArrayList<>();
            tabList.add("reload");
            tabList.add("setspawn");
            tabList.add("help");
            return tabList;
        }
        return null;
    }
}
