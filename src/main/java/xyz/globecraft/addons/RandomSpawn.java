package xyz.globecraft.addons;

public class RandomSpawn implements AddonInstance {
    private static final String[] randomSpawnHelpScreen = {
            "-- RandomSpawn --",
            "",
            "Randomly spawn on your first log in.",
            "If you haven't settled down yet,",
            "respawn close to your deathpoint."
    };

    private final AddonsPlugin addons;
    private boolean enabled, loaded;

    public RandomSpawn(AddonsPlugin addons) {
        this.addons = addons;
        this.enabled = this.addons.getConfig().getBoolean("randomspawn.enabled", false);
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
        return "RandomSpawn";
    }

    @Override
    public String getDescription() {
        return "Spawn new players randomly on the map.";
    }

    @Override
    public String[] getHelpScreen() {
        return this.randomSpawnHelpScreen;
    }
}
