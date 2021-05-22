package xyz.globecraft.addons.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import xyz.globecraft.addons.AddonInstance;
import xyz.globecraft.addons.AddonsPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TabCompleter implements org.bukkit.command.TabCompleter {
    AddonsPlugin addons;

    public TabCompleter(AddonsPlugin addons) {
        this.addons = addons;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> addons = new ArrayList<String>();
        String name;
        for(AddonInstance instance : this.addons.getAddons()) {
            name = instance.getName();
            if(name.toLowerCase().startsWith(String.join(" ", Arrays.asList(args)).toLowerCase())) addons.add(name);
        }
        return addons;
    }
}
