package xyz.globecraft.addons;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class VoteSkipNight implements AddonInstance, CommandExecutor {
    private static final String[] voteSkipNightHelpScreen = {
            "-- VoteSkipNight --",
            "",
            "Makes it possible to skip night,",
            "by using commands to vote.",
            "Not everyone needs to vote."
    };

    private final AddonsPlugin addons;
    private final boolean enabled;
    private boolean loaded;
    private int clock;
    boolean nightTime;
    int goal;
    World world = null;
    ArrayList<Player> players = new ArrayList<>();

    public VoteSkipNight(AddonsPlugin addons) {
        this.addons = addons;
        this.enabled = this.addons.getConfig().getBoolean("voteskipnight.enabled", false);
        this.loaded = false;
        this.addons.getCommand("vday").setExecutor(this);
    }

    @Override
    public void enable() {
        if(this.loaded) return;
        this.loaded = true;

        this.clock = this.addons.getServer().getScheduler().scheduleSyncRepeatingTask(this.addons, new Runnable() {
            public void run() {
                if (VoteSkipNight.this.world != null && VoteSkipNight.this.nightTime) {
                    long t = VoteSkipNight.this.world.getTime() % 24000L;
                    if (t < 13000L) {
                        VoteSkipNight.this.nightTime = false;
                        VoteSkipNight.this.players.clear();
                        VoteSkipNight.this.goal = 0;
                    }
                }
            }
        },  0L, 1000L);
    }

    @Override
    public void disable() {
        if(!this.loaded) return;
        this.loaded = false;
        Bukkit.getScheduler().cancelTask(clock);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!this.loaded) {
            sender.sendMessage("This addon is disabled!");
            return true;
        }

        if (args.length == 0)
            if (sender instanceof Player) {
                Player p = (Player)sender;
                long time = p.getWorld().getTime() % 24000L;
                if (time > 13000L) {
                    if (!this.nightTime) {
                        this.world = p.getWorld();
                        this.goal = (int)Math.ceil((this.addons.getServer().getOnlinePlayers().size() * this.getPercent() / 100f));
                        this.addons.getServer().broadcastMessage(this.localeVoteStart(this.goal));
                        this.players.add(p);
                        this.nightTime = true;
                    } else if (!this.players.contains(p)) {
                        this.players.add(p);
                        this.addons.getServer().broadcastMessage(this.localeVotes(p.getName(), this.players.size(), this.goal));
                    } else {
                        p.sendMessage(this.localeDoubles());
                    }
                    if (this.players.size() >= this.goal) {
                        p.getWorld().setTime(0L);
                        this.players.clear();
                        this.nightTime = false;
                    }
                } else {
                    p.sendMessage(this.localeNotNight());
                }
            } else {
                sender.sendMessage("This command cannot be echoed from console, for it needs a world name");
            }
        return true;
    }

    public String localeVotes(String name, int cur, int goal) {
        return String.format(this.addons.getConfig().getString("voteskipnight.msg.pref") + " " + this.addons.getConfig().getString("voteskipnight.msg.votes"), name, cur, goal);
    }

    public String localeDoubles() {
        return this.addons.getConfig().getString("voteskipnight.msg.pref") + " " + this.addons.getConfig().getString("voteskipnight.msg.doubles");
    }

    public String localeVoteStart(int goal) {
        return String.format(this.addons.getConfig().getString("voteskipnight.msg.pref") + " " + this.addons.getConfig().getString("voteskipnight.msg.votestart"), goal);
    }


    public String localeNotNight() {
        return this.addons.getConfig().getString("voteskipnight.msg.pref") + " " + this.addons.getConfig().getString("voteskipnight.msg.notnight");
    }

    public int getPercent() {
        return this.addons.getConfig().getInt("voteskipnight.plugin.percent");
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
        return "VoteSkipNight";
    }

    @Override
    public String getDescription() {
        return "Skip a night without sleeping.";
    }

    @Override
    public String[] getHelpScreen() {
        return voteSkipNightHelpScreen;
    }
}
