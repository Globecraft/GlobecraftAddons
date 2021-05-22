package xyz.globecraft.addons;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.RegionContainer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class Hypothermia implements AddonInstance, Listener {
	
	private static final String[] hypothermiaHelpScreen = {
			"-- Hypothermia --",
			"",
			"This is an added immersion to the map. When you get too high up a mountain,",
			"or you get in too cold of a biome, you start to take damage",
			"which gets higher the further up the mountain you go.",
			"",
			"A piece of leather armor removes 25% of the damage.",
			"While a piece of some other armor removes 12.5% of the damage."
	};
	
	private final AddonsPlugin addons;
	private int minY, layerY, regionDamage, underCoverLight, clock;
	private final boolean enabled;
	private boolean loaded;
	private final Set<UUID> killed;
	
	public Hypothermia(AddonsPlugin addons) {
		this.addons = addons;
		this.enabled = this.addons.getConfig().getBoolean("hypothermia.enabled", false);
		this.loaded = false;
		this.killed = new HashSet<UUID>();
		this.addons.getServer().getPluginManager().registerEvents(this, this.addons);
	}

	@Override
	public void enable() {
		if(this.loaded) return;
		this.loaded = true;

		this.minY = addons.getConfig().getInt("hypothermia.danger-altitude", 130);
		this.layerY = addons.getConfig().getInt("hypothermia.blocks-per-heart", 10);
		this.regionDamage = addons.getConfig().getInt("hypothermia.region-damage", 2);
		this.underCoverLight = addons.getConfig().getInt("hypothermia.under-cover-light", 10);

		int tickClock = addons.getConfig().getInt("hypothermia.ticks-per-damage", 40);

		this.clock = Bukkit.getScheduler().scheduleSyncRepeatingTask(addons, new Runnable() {
			public void run() {
				onTickClock();
			}
		}, tickClock, tickClock);
	}

	@Override
	public void disable() {
		if(!this.loaded) return;
		this.loaded = false;
		
		Bukkit.getScheduler().cancelTask(clock);
	}

	@Override
	public boolean enabled() {
		return this.enabled;
	}

	@Override
	public boolean loaded() {
		return this.loaded;
	}
	
	public void onTickClock() {
		int py, dmg;
		double mult, tdmg;
		boolean icy;
		for(Player p : addons.getServer().getOnlinePlayers()) {
			py = p.getLocation().getBlockY();
			icy = inIcyRegion(p);
			if((py >= minY || icy) && playerOutside(p) && !p.isDead()) {
				mult = 1;
				dmg = (py - minY) / (layerY / 2);
				for(ItemStack i : p.getInventory().getArmorContents()) {
					if(i != null) {
						switch(i.getType()) {
							case LEATHER_BOOTS:
							case LEATHER_CHESTPLATE:
							case LEATHER_LEGGINGS:
							case LEATHER_HELMET:
								mult -= 0.25;
								break;
							case AIR:
								break;
							default:
								mult -= 0.125;
						}
					}
				}
				if(icy) dmg += this.regionDamage;
				tdmg = mult * dmg;
				if(p.getHealth() <= tdmg) {
					this.killed.add(p.getUniqueId());
				}
				p.damage(tdmg);
			}
		}
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		if(!this.loaded) return;

		UUID uuid = event.getEntity().getUniqueId();
		if(this.killed.contains(uuid)) {
			this.killed.remove(uuid);
			event.setDeathMessage(event.getEntity().getDisplayName() + " died from hypothermia");
		}
	}

	public boolean playerOutside(Player player) {
		return player.getLocation().getBlock().getLightFromSky() > this.underCoverLight;
	}

	public boolean inIcyRegion(Player player) throws NullPointerException {
		BlockVector3 loc = BukkitAdapter.asBlockVector(player.getLocation());
		World world = BukkitAdapter.adapt(player.getWorld());
		LocalPlayer wgPlayer = WorldGuardPlugin.inst().wrapPlayer(player);

		RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
		RegionManager allRegions = container.get(world);

		if (allRegions == null) {
			return false;
		}

		ApplicableRegionSet playerRegions = allRegions.getApplicableRegions(loc);
		return playerRegions.testState(wgPlayer, AddonsPlugin.getHypothermiaFlag());
	}

	@Override
	public String getName() {
		return "Hypothermia";
	}

	@Override
	public String getDescription() {
		return "Take damage from the cold in cold places.";
	}

	@Override
	public String[] getHelpScreen() {
		return hypothermiaHelpScreen;
	}
}
