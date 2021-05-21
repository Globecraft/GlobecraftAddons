package xyz.globecraft.addons;

public class VoteSkipNight implements AddonInstance {
    private static final String[] voteSkipNightHelpScreen = {
            "-- VoteSkipNight --",
            "",
            "Makes it possible to skip night,",
            "by using commands to vote.",
            "Not everyone needs to vote."
    };

    private final AddonsPlugin addons;
    private boolean enabled, loaded;

    public VoteSkipNight(AddonsPlugin addons) {
        this.addons = addons;
        this.enabled = this.addons.getConfig().getBoolean("voteskipnight.enabled", false);
        this.loaded = false;
    }

    @Override
    public void enable() {
        if(this.loaded) return;
        this.loaded = true;
        // TODO: implement addon
    }

    @Override
    public void disable() {
        if(!this.loaded) return;
        this.loaded = false;

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
        return this.voteSkipNightHelpScreen;
    }
}
