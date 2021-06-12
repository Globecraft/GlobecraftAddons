package xyz.globecraft.addons;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import org.guilds.plugin.territory.GMeta;

import java.util.List;
import java.util.Random;

public class RandomSpawn implements AddonInstance, Listener, CommandExecutor {
    private static final String[] randomSpawnHelpScreen = {
            "-- RandomSpawn --",
            "",
            "Randomly spawn on your first log in.",
            "And if you haven't settled down yet,",
            "respawn close to your death point."
    };

    private final AddonsPlugin addons;
    private final boolean enabled;
    private boolean loaded;

    private int minX, maxX, minZ, maxZ;
    private int respawnRadius, tries;
    private List<String> safeBlocks;
    private String worldName;
    private World world;
    private Random random = new Random();

    public RandomSpawn(AddonsPlugin addons) {
        this.addons = addons;
        this.enabled = this.addons.getConfig().getBoolean("randomspawn.enabled", false);
        this.loaded = false;
        this.addons.getServer().getPluginManager().registerEvents(this, addons);
        this.addons.getCommand("rtp").setExecutor(this);
    }

    @Override
    public void enable() {
        if(this.loaded) return;
        this.loaded = true;

        this.minX = addons.getConfig().getInt("randomspawn.min-x");
        this.maxX = addons.getConfig().getInt("randomspawn.max-x");
        this.minZ = addons.getConfig().getInt("randomspawn.min-z");
        this.maxZ = addons.getConfig().getInt("randomspawn.max-z");
        this.worldName = addons.getConfig().getString("randomspawn.world");
        this.respawnRadius = addons.getConfig().getInt("randomspawn.respawn-radius");
        this.tries = addons.getConfig().getInt("randomspawn.tries");
        this.safeBlocks = addons.getConfig().getStringList("randomspawn.safe-blocks");
        this.world = addons.getServer().getWorld(this.worldName);
    }

    @Override
    public void disable() {
        if(!this.loaded) return;
        this.loaded = false;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if(!this.loaded) return;

        if (!this.addons.getServer().getOfflinePlayer(event.getPlayer().getUniqueId()).hasPlayedBefore()) {
            event.getPlayer().teleportAsync(randomSpawn(new Location(this.world, 0, 0, 0), this.minX, this.maxX, this.minZ, this.maxZ));
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        if(!this.loaded) return;

        if(event.getPlayer().getBedSpawnLocation() == null && event.getPlayer().getLocation().getWorld().getEnvironment().toString().equalsIgnoreCase("normal")) {
            event.setRespawnLocation(randomSpawn(event.getPlayer().getLocation(), -respawnRadius, respawnRadius, -respawnRadius, respawnRadius));
        }
    }

    private Location randomSpawn(Location loc, int minX, int maxX, int minZ, int maxZ) {
        minX = minX + (int) loc.getX();
        maxX = maxX + (int) loc.getX();
        minZ = minZ + (int) loc.getZ();
        maxZ = maxZ + (int) loc.getZ();
        for(int i = 0; i < this.tries; i++) {
            int x = this.random.nextInt(maxX - minX) + minX;
            int z = this.random.nextInt(maxZ - minZ) + minZ;
            // TODO: async?
            Block landing = this.world.getHighestBlockAt(x, z);
            if(this.safeBlocks.contains(landing.getType().toString().toLowerCase())) {
                GMeta meta = GMeta.getClaim(landing.getLocation().getChunk());
                if(meta == null || meta.isEmpty()) {
                    Location target = landing.getLocation();
                    target.add(0.5, 1, 0.5);
                    return target;
                }
            }
        }
        return this.world.getSpawnLocation();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!this.loaded) {
            sender.sendMessage("This addon is disabled!");
            return true;
        }

        sender.sendMessage("Teleporting randomly...");
        ((Player) sender).teleportAsync(randomSpawn(new Location(this.world, 0, 0, 0), this.minX, this.maxX, this.minZ, this.maxZ));
        return true;
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
        return "RandomSpawn";
    }

    @Override
    public String getDescription() {
        return "Spawn new players randomly on the map.";
    }

    @Override
    public String[] getHelpScreen() {
        return randomSpawnHelpScreen;
    }
}
