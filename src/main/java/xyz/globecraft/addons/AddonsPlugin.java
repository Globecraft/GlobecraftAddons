package xyz.globecraft.addons;

import java.util.ArrayList;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;

import org.bukkit.plugin.java.JavaPlugin;

import xyz.globecraft.addons.commands.*;

public class AddonsPlugin extends JavaPlugin {
	private final ArrayList<AddonInstance> instances = new ArrayList<AddonInstance>();

	private static StateFlag HYPOTHERMIA_FLAG;
	public static StateFlag getHypothermiaFlag() {
		return HYPOTHERMIA_FLAG;
	}

	public static AddonsPlugin instance;
	public static AddonsPlugin getInstance() {
		return instance;
	}

	@Override
	public void onEnable() {
		instance = this;
		saveDefaultConfig();

		instances.add(new Hypothermia(this));
		instances.add(new CustomRecipes(this));
		instances.add(new ItemNamedBy(this));
		instances.add(new RandomSpawn(this));
		instances.add(new VoteSkipNight(this));

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
		FlagRegistry registry = WorldGuard.getInstance().getFlagRegistry();
		try {
			StateFlag flag = new StateFlag("hypothermia", false);
			registry.register(flag);
			HYPOTHERMIA_FLAG = flag;
		} catch (FlagConflictException e) {
			Flag<?> existing = registry.get("hypothermia");
			if (existing instanceof StateFlag) {
				HYPOTHERMIA_FLAG = (StateFlag) existing;
				getLogger().warning("Using pre-existing hypothermia WG flag.");
			} else {
				getLogger().warning("Error creating hypothermia WG Flag:\n" + e.getMessage());
			}
		}
	}

	@Override
	public void onDisable() {
		for(AddonInstance instance : instances) {
			instance.disable();
		}
	}

	public ArrayList<AddonInstance> getAddons() {
		return instances;
	}
}
