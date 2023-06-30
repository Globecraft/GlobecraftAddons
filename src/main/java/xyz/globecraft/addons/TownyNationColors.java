package xyz.globecraft.addons;

import com.palmergames.bukkit.towny.event.NewNationEvent;
import com.palmergames.bukkit.towny.object.Nation;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class TownyNationColors implements AddonInstance, Listener {
    private static final String[] townyNationColorsHelpScreen = {
            "-- TownyNationColors --",
            "",
            "Force default color on new nations"
    };
    private final AddonsPlugin addons;
    private final boolean enabled;
    private boolean loaded;
    private String color;

    public TownyNationColors(AddonsPlugin addons) {
        this.addons = addons;
        this.enabled = this.addons.getConfig().getBoolean("townynationcolors.enabled", false);
        this.loaded = false;
        this.addons.getServer().getPluginManager().registerEvents(this, addons);
    }

    @Override
    public void enable() {
        if(this.loaded) return;
        this.loaded = true;
        this.color = this.addons.getConfig().getString("townynationcolors.color");
    }

    @Override
    public void disable() {
        if(!this.loaded) return;
        this.loaded = false;
    }

    @EventHandler
    public void onEntityDeath(NewNationEvent e) {
        Nation n = e.getNation();
        n.setMapColorHexCode(this.color);
        n.save();
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
        return "TownyNationColors";
    }

    @Override
    public String getDescription() {
        return "Force default color on new nations";
    }

    @Override
    public String[] getHelpScreen() {
        return townyNationColorsHelpScreen;
    }
}
