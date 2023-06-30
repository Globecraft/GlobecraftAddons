package xyz.globecraft.addons.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import xyz.globecraft.addons.AddonInstance;
import xyz.globecraft.addons.AddonsPlugin;

import java.util.Arrays;

public class GcReload implements CommandExecutor {
    private final AddonsPlugin addons;

    public GcReload(AddonsPlugin addons) {
        this.addons = addons;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        StringBuilder s = new StringBuilder();
        this.addons.reloadConfig();

        for(AddonInstance instance : this.addons.getAddons()) {
            if(instance.getName().equalsIgnoreCase(String.join(" ", Arrays.asList(args)))) {
                instance.disable();
                instance.enable();
                s.append(ChatColor.LIGHT_PURPLE).append("Addon ");
                s.append(ChatColor.DARK_PURPLE).append(instance.getName());
                s.append(ChatColor.LIGHT_PURPLE).append(" has been ");
                s.append(ChatColor.YELLOW).append("reloaded.");
                sender.sendMessage(s.toString());
                return true;
            }
        }

        s.append(ChatColor.RED).append("No addon found with that name!");
        sender.sendMessage(s.toString());
        return true;
    }
}
