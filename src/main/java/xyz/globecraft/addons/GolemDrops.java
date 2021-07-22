package xyz.globecraft.addons;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import java.util.List;
import java.util.Random;

public class GolemDrops implements AddonInstance, Listener {
    private static final String[] golemDropsHelpScreen = {
            "-- GolemDrops --",
            "",
            "Reduce iron drops from iron golems",
            "to discourage iron farms.",
    };

    private final AddonsPlugin addons;
    private final boolean enabled;
    private boolean loaded;
    private int min, max;
    Random r;

    public GolemDrops(AddonsPlugin addons) {
        this.enabled = addons.getConfig().getBoolean("golemdrops.enabled", false);
        this.loaded = false;
        this.addons = addons;
        this.addons.getServer().getPluginManager().registerEvents(this, addons);
    }

    @Override
    public void enable() {
        if(this.loaded) return;
        this.loaded = true;

        this.min = this.addons.getConfig().getInt("golemdrops.min", 1);
        this.max = this.addons.getConfig().getInt("golemdrops.max", 2);

        if(this.min > this.max) {
            this.min = this.max;
        }

        this.r = new Random();
    }

    @Override
    public void disable() {
        if(!this.loaded) return;
        this.loaded = false;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        if(!this.loaded) return;

        if(e.getEntity().getType() == EntityType.IRON_GOLEM) {
            List<ItemStack> drops = e.getDrops();
            drops.clear();
            ItemStack newDrops = new ItemStack(Material.IRON_INGOT, r.nextInt(this.max - this.min + 1) + this.min);
            drops.add(newDrops);
        }
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
        return "GolemDrops";
    }

    @Override
    public String getDescription() {
        return "Reduce iron drops.";
    }

    @Override
    public String[] getHelpScreen() {
        return golemDropsHelpScreen;
    }
}
