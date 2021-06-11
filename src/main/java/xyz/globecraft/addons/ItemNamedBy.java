package xyz.globecraft.addons;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class ItemNamedBy implements AddonInstance, Listener {
    private static final String[] itemNamedByHelpScreen = {
            "-- ItemNamedBy --",
            "",
            "Track the user who named an item.",
            "Very useful for authentic currencies,",
            "contracts and signed items."
    };

    private final boolean enabled;
    private boolean loaded;

    public ItemNamedBy(AddonsPlugin addons) {
        this.enabled = addons.getConfig().getBoolean("itemnamedby.enabled", false);
        this.loaded = false;
        addons.getServer().getPluginManager().registerEvents(this, addons);
    }

    @Override
    public void enable() {
        if(this.loaded) return;
        this.loaded = true;
    }

    @Override
    public void disable() {
        if(!this.loaded) return;
        this.loaded = false;
    }

    @EventHandler
    public void onPlayerNameItem(PrepareAnvilEvent event) {
        if(!this.loaded) return;
        try {
            if (event.getResult().getType() != Material.AIR) {
                String oldName = "";
                if(event.getInventory().getItem(0).getItemMeta().hasDisplayName())
                    oldName = ((TextComponent) event.getInventory().getItem(0).getItemMeta().displayName()).content();
                String newName = "";
                if(event.getResult().getItemMeta().hasDisplayName())
                    newName = ((TextComponent) event.getResult().getItemMeta().displayName()).content();
                if (event.getViewers().get(0) != null && !oldName.equals(newName)) {
                    ItemStack output = event.getResult();
                    ItemMeta meta = output.getItemMeta();
                    meta.lore(Collections.singletonList(Component.text("Named by " + event.getViewers().get(0).getName()).color(NamedTextColor.GRAY)));
                    output.setItemMeta(meta);
                    event.setResult(output);
                }
            }
        } catch(NullPointerException ignored) { }
    }

    @Override
    public boolean enabled() {
        return this.enabled;
    }

    @Override
    public boolean loaded() {
        return this.loaded;
    }

    @Override
    public String getName() {
        return "ItemNamedBy";
    }

    @Override
    public String getDescription() {
        return "Track the one who named an item.";
    }

    @Override
    public String[] getHelpScreen() {
        return itemNamedByHelpScreen;
    }
}
