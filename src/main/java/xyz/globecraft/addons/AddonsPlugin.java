package xyz.globecraft.addons;

import java.util.ArrayList;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.globecraft.addons.commands.*;

public class AddonsPlugin extends JavaPlugin {
	private final ArrayList<AddonInstance> instances = new ArrayList<AddonInstance>();

	public static AddonsPlugin instance;
	public static AddonsPlugin getInstance() {
		return instance;
	}

	@Override
	public void onEnable() {
		instance = this;
		saveDefaultConfig();

		instances.add(new ItemNamedBy(this));
		instances.add(new RandomSpawn(this));
		instances.add(new TownyNationColors(this));
		instances.add(new DropModifier(this));

		for(AddonInstance instance : instances) {
			if(instance.enabled()) {
				instance.enable();
				getLogger().info(instance.getName() + " enabled!");
			}
		}

		getCommand("gcaddons").setExecutor(new GcAddons(this));
		getCommand("gcreload").setExecutor(new GcReload(this));
		getCommand("gcdisable").setExecutor(new GcDisable(this));
		getCommand("gcenable").setExecutor(new GcEnable(this));

		TabCompleter tab = new TabCompleter(this);
		getCommand("gcaddons").setTabCompleter(tab);
		getCommand("gcreload").setTabCompleter(tab);
		getCommand("gcdisable").setTabCompleter(tab);
		getCommand("gcenable").setTabCompleter(tab);
	}

	@Override
	public void onLoad() {
		getLogger().info("Loading GlobecraftAddons...");
	}

	@Override
	public void onDisable() {
		for(AddonInstance instance : instances) {
			instance.disable();
		}
		getLogger().info("Shutting Down GlobecraftAddons...");
	}

	public ArrayList<AddonInstance> getAddons() {
		return instances;
	}
}
