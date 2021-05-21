package xyz.globecraft.addons.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import xyz.globecraft.addons.AddonInstance;
import xyz.globecraft.addons.AddonsPlugin;

import java.util.Arrays;

public class GcAddons implements CommandExecutor {
    private final AddonsPlugin addons;

    public GcAddons(AddonsPlugin addons) {
        this.addons = addons;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        StringBuilder s = new StringBuilder();

        for(AddonInstance instance : this.addons.getAddons()) {
            if(instance.getName().equalsIgnoreCase(String.join(" ", Arrays.asList(args)))) {
                sender.sendMessage(s.append(ChatColor.YELLOW).append(String.join("\n", Arrays.asList(instance.getHelpScreen()))).toString());
                return true;
            }
        }

        s.append(ChatColor.GOLD).append(ChatColor.BOLD).append("Globecraft Addons");
        sender.sendMessage(s.toString());
        for(AddonInstance instance : this.addons.getAddons()) {
            s.setLength(0);
            if(instance.loaded()) s.append(ChatColor.AQUA);
            else s.append(ChatColor.RED);
            s.append(instance.getName());
            s.append(ChatColor.RESET).append(": ");
            s.append(instance.getDescription());
            sender.sendMessage(s.toString());
        }
        return true;
    }
}
