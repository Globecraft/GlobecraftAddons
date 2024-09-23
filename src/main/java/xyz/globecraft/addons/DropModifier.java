package xyz.globecraft.addons;

import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class DropModifier implements AddonInstance, Listener {
    private static final String[] dropModifierHelpScreen = {
            "-- DropModifier --",
            "",
            "Customise mob drops to discourage automatic farming.",
            "Set the iron golem iron drop rate.",
            "And disallow gold dropping from nether mobs.",
            "Disable fortune on gold ores."
    };

    private final AddonsPlugin addons;
    private final boolean enabled;
    private boolean loaded;
    private int imin, imax;
    private boolean nogold;
    Random r;

    public DropModifier(AddonsPlugin addons) {
        this.enabled = addons.getConfig().getBoolean("dropmodifier.enabled", false);
        this.loaded = false;
        this.addons = addons;
        this.addons.getServer().getPluginManager().registerEvents(this, addons);
    }

    @Override
    public void enable() {
        if(this.loaded) return;
        this.loaded = true;

        this.imin = this.addons.getConfig().getInt("dropmodifier.iron.min", 1);
        this.imax = this.addons.getConfig().getInt("dropmodifier.iron.max", 2);
        this.nogold = this.addons.getConfig().getBoolean("dropmodifier.nogold", true);

        if(this.imin > this.imax) {
            this.imin = this.imax;
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

        List<ItemStack> drops = e.getDrops();

        switch (e.getEntityType()) {
            case IRON_GOLEM:
                drops.clear(); 
                drops.add(new ItemStack(Material.IRON_INGOT, r.nextInt(this.imax - this.imin + 1) + this.imin));
                break;
            case PIGLIN:
            case PIGLIN_BRUTE:
            case ZOMBIFIED_PIGLIN:
            case DROWNED:
                if(this.nogold) {
                    for (int i = 0; i < drops.size(); i++) {
                        Material drop = drops.get(i).getType();
                        if (Stream.of(
                                Material.GOLD_INGOT, Material.GOLD_NUGGET, Material.GOLDEN_SWORD, Material.GOLDEN_AXE,
                                Material.GOLDEN_HELMET, Material.GOLDEN_CHESTPLATE, Material.GOLDEN_LEGGINGS, Material.GOLDEN_BOOTS
                        ).anyMatch(drop::equals)) {
                            drops.remove(i);
                            i--;
                        }
                    }
                }
                break;
            default:
        }
    }

    @EventHandler
    public void onBlockItemDrop(BlockDropItemEvent e) {
        if(!this.loaded) return;
        
        List<Item> drops = e.getItems();

        // too lazy to give this an option in config lmao
        switch (e.getBlockState().getType()) {
            case GOLD_ORE:
            case DEEPSLATE_GOLD_ORE:
                if (drops.size() > 0) {
                    Item drop = drops.get(0);
                    ItemStack stack = drop.getItemStack();
                    stack.setAmount(1);
                }
                break;
            case NETHER_GOLD_ORE:
                drops.clear();
                break;
            default:
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
        return "DropModifier";
    }

    @Override
    public String getDescription() {
        return "Modify drop rates.";
    }

    @Override
    public String[] getHelpScreen() {
        return dropModifierHelpScreen;
    }
}
