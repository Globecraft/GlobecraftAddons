package xyz.globecraft.addons;

public class ItemNamedBy implements AddonInstance {
    private static final String[] itemNamedByHelpScreen = {
            "-- ItemNamedBy --",
            "",
            "Track the user who named an item.",
            "Very useful for authentic currencies,",
            "checks and signed items."
    };

    private final AddonsPlugin addons;
    private boolean enabled, loaded;

    public ItemNamedBy(AddonsPlugin addons) {
        this.addons = addons;
        this.enabled = this.addons.getConfig().getBoolean("itemnamedby.enabled", false);
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
        return "ItemNamedBy";
    }

    @Override
    public String getDescription() {
        return "Track the one who named an item.";
    }

    @Override
    public String[] getHelpScreen() {
        return this.itemNamedByHelpScreen;
    }
}
