package xyz.globecraft.addons;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;


public class CustomRecipes implements AddonInstance {

    private static final String[] customRecipesHelpScreen = {
            "-- CustomRecipes --",
            "",
            "Custom recipes for Globecraft",
            "",
            "Recipes:",
            "coal to ink sac",
            "charcoal to ink sac",
            "powered rails with iron",
            "saddle"
    };

    private final AddonsPlugin addons;
    private final boolean enabled;
    private boolean loaded;

    public CustomRecipes(AddonsPlugin addons) {
        this.addons = addons;
        this.enabled = this.addons.getConfig().getBoolean("customrecipes.enabled", false);
        this.loaded = false;
    }

    @Override
    public void enable() {
        if(this.loaded) return;
        this.loaded = true;

        ShapelessRecipe ink = new ShapelessRecipe(new NamespacedKey(this.addons, "coal_ink_sac"), new ItemStack(Material.INK_SAC, 1));
        ink.addIngredient(Material.COAL);

        ShapelessRecipe ink2 = new ShapelessRecipe(new NamespacedKey(this.addons, "coal_ink_sac2"), new ItemStack(Material.INK_SAC, 1));
        ink2.addIngredient(Material.CHARCOAL);

        ShapedRecipe rails = new ShapedRecipe(new NamespacedKey(this.addons, "iron_powered_rail"), new ItemStack(Material.POWERED_RAIL, 16));
        rails.shape("I I", "ISI", "IRI");
        rails.setIngredient('I', Material.IRON_INGOT);
        rails.setIngredient('S', Material.STICK);
        rails.setIngredient('R', Material.REDSTONE);

        ShapedRecipe saddle = new ShapedRecipe(new NamespacedKey(this.addons, "saddle"), new ItemStack(Material.SADDLE, 1));
        saddle.shape("LLL", "LIL", "S S");
        saddle.setIngredient('L', Material.LEATHER);
        saddle.setIngredient('I', Material.IRON_INGOT);
        saddle.setIngredient('S', Material.STRING);

        Bukkit.addRecipe(ink);
        Bukkit.addRecipe(ink2);
        Bukkit.addRecipe(rails);
        Bukkit.addRecipe(saddle);
    }

    @Override
    public void disable() {
        if(!this.loaded) return;
        this.loaded = false;

        Bukkit.removeRecipe(new NamespacedKey(this.addons, "coal_ink_sac"));
        Bukkit.removeRecipe(new NamespacedKey(this.addons, "coal_ink_sac2"));
        Bukkit.removeRecipe(new NamespacedKey(this.addons, "iron_powered_rail"));
        Bukkit.removeRecipe(new NamespacedKey(this.addons, "saddle"));
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
        return "CustomRecipes";
    }

    @Override
    public String getDescription() {
        return "Adds some custom recipes";
    }

    @Override
    public String[] getHelpScreen() {
        return customRecipesHelpScreen;
    }
}
