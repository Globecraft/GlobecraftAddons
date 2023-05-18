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
            "saddle",
            "horse armor",
            "prismarine",
            "nametags",
            "chainmail armor"
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

        ShapedRecipe ironHorseArmor = new ShapedRecipe(new NamespacedKey(this.addons, "iron_horse_armor"), new ItemStack(Material.IRON_HORSE_ARMOR, 1));
        ironHorseArmor.shape("MS ", "MMM", "MMM");
        ironHorseArmor.setIngredient('S', Material.SADDLE);
        ironHorseArmor.setIngredient('M', Material.IRON_INGOT);

        ShapedRecipe goldenHorseArmor = new ShapedRecipe(new NamespacedKey(this.addons, "golden_horse_armor"), new ItemStack(Material.GOLDEN_HORSE_ARMOR, 1));
        goldenHorseArmor.shape("MS ", "MMM", "MMM");
        goldenHorseArmor.setIngredient('S', Material.SADDLE);
        goldenHorseArmor.setIngredient('M', Material.GOLD_INGOT);

        ShapedRecipe diamondHorseArmor = new ShapedRecipe(new NamespacedKey(this.addons, "diamond_horse_armor"), new ItemStack(Material.DIAMOND_HORSE_ARMOR, 1));
        diamondHorseArmor.shape("MS ", "MMM", "MMM");
        diamondHorseArmor.setIngredient('S', Material.SADDLE);
        diamondHorseArmor.setIngredient('M', Material.DIAMOND);

        ShapedRecipe primarineCrystals1 = new ShapedRecipe(new NamespacedKey(this.addons, "prismarine_crystals_one"), new ItemStack(Material.PRISMARINE_CRYSTALS, 4));
        primarineCrystals1.shape("CK", "KC");
        primarineCrystals1.setIngredient('C', Material.CLAY_BALL);
        primarineCrystals1.setIngredient('K', Material.KELP);

        ShapedRecipe primarineCrystals2 = new ShapedRecipe(new NamespacedKey(this.addons, "prismarine_crystals_two"), new ItemStack(Material.PRISMARINE_CRYSTALS, 4));
        primarineCrystals2.shape("KC", "CK");
        primarineCrystals2.setIngredient('C', Material.CLAY_BALL);
        primarineCrystals2.setIngredient('K', Material.KELP);

        ShapedRecipe primarineShards1 = new ShapedRecipe(new NamespacedKey(this.addons, "prismarine_shards_one"), new ItemStack(Material.PRISMARINE_SHARD, 4));
        primarineShards1.shape("CCK", "CK ", "K  ");
        primarineShards1.setIngredient('C', Material.CLAY_BALL);
        primarineShards1.setIngredient('K', Material.KELP);

        ShapedRecipe primarineShards2 = new ShapedRecipe(new NamespacedKey(this.addons, "prismarine_shards_two"), new ItemStack(Material.PRISMARINE_SHARD, 4));
        primarineShards2.shape("  K", " KC", "KCC");
        primarineShards2.setIngredient('C', Material.CLAY_BALL);
        primarineShards2.setIngredient('K', Material.KELP);

        ShapedRecipe primarineShards3 = new ShapedRecipe(new NamespacedKey(this.addons, "prismarine_shards_three"), new ItemStack(Material.PRISMARINE_SHARD, 4));
        primarineShards3.shape("KCC", " KC", "  K");
        primarineShards3.setIngredient('C', Material.CLAY_BALL);
        primarineShards3.setIngredient('K', Material.KELP);

        ShapedRecipe primarineShards4 = new ShapedRecipe(new NamespacedKey(this.addons, "prismarine_shards_four"), new ItemStack(Material.PRISMARINE_SHARD, 4));
        primarineShards4.shape("K  ", "CK ", "CCK");
        primarineShards4.setIngredient('C', Material.CLAY_BALL);
        primarineShards4.setIngredient('K', Material.KELP);

        ShapedRecipe nameTag = new ShapedRecipe(new NamespacedKey(this.addons, "name_tag"), new ItemStack(Material.NAME_TAG, 1));
        nameTag.shape(" II", "LBI", "LL ");
        nameTag.setIngredient('I', Material.IRON_INGOT);
        nameTag.setIngredient('L', Material.LEATHER);
        nameTag.setIngredient('B', Material.WRITABLE_BOOK);

        ShapedRecipe chainMailBoots = new ShapedRecipe(new NamespacedKey(this.addons, "chainmail_boots"), new ItemStack(Material.CHAINMAIL_BOOTS, 1));
        chainMailBoots.shape("C C", "C C");
        chainMailBoots.setIngredient('C', Material.CHAIN);
        
        ShapedRecipe chainMailLeggings = new ShapedRecipe(new NamespacedKey(this.addons, "chainmail_leggings"), new ItemStack(Material.CHAINMAIL_LEGGINGS, 1));
        chainMailLeggings.shape("CCC", "C C", "C C");
        chainMailLeggings.setIngredient('C', Material.CHAIN);

        ShapedRecipe chainMailChestplate = new ShapedRecipe(new NamespacedKey(this.addons, "chainmail_chestplate"), new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1));
        chainMailChestplate.shape("C C", "CCC", "CCC");
        chainMailChestplate.setIngredient('C', Material.CHAIN);

        ShapedRecipe chainMailHelmet = new ShapedRecipe(new NamespacedKey(this.addons, "chainmail_helmet"), new ItemStack(Material.CHAINMAIL_HELMET, 1));
        chainMailHelmet.shape("CCC", "C C");
        chainMailHelmet.setIngredient('C', Material.CHAIN);

        Bukkit.addRecipe(ink);
        Bukkit.addRecipe(ink2);
        Bukkit.addRecipe(rails);
        Bukkit.addRecipe(saddle);
        Bukkit.addRecipe(ironHorseArmor);
        Bukkit.addRecipe(goldenHorseArmor);
        Bukkit.addRecipe(diamondHorseArmor);
        Bukkit.addRecipe(primarineCrystals1);
        Bukkit.addRecipe(primarineCrystals2);
        Bukkit.addRecipe(primarineShards1);
        Bukkit.addRecipe(primarineShards2);
        Bukkit.addRecipe(primarineShards3);
        Bukkit.addRecipe(primarineShards4);
        Bukkit.addRecipe(nameTag);
        Bukkit.addRecipe(chainMailBoots);
        Bukkit.addRecipe(chainMailLeggings);
        Bukkit.addRecipe(chainMailChestplate);
        Bukkit.addRecipe(chainMailHelmet);
    }

    @Override
    public void disable() {
        if(!this.loaded) return;
        this.loaded = false;

        Bukkit.removeRecipe(new NamespacedKey(this.addons, "coal_ink_sac"));
        Bukkit.removeRecipe(new NamespacedKey(this.addons, "coal_ink_sac2"));
        Bukkit.removeRecipe(new NamespacedKey(this.addons, "iron_powered_rail"));
        Bukkit.removeRecipe(new NamespacedKey(this.addons, "saddle"));
        Bukkit.removeRecipe(new NamespacedKey(this.addons, "iron_horse_armor"));
        Bukkit.removeRecipe(new NamespacedKey(this.addons, "golden_horse_armor"));
        Bukkit.removeRecipe(new NamespacedKey(this.addons, "diamond_horse_armor"));
        Bukkit.removeRecipe(new NamespacedKey(this.addons, "prismarine_crystals_one"));
        Bukkit.removeRecipe(new NamespacedKey(this.addons, "prismarine_crystals_two"));
        Bukkit.removeRecipe(new NamespacedKey(this.addons, "prismarine_shards_one"));
        Bukkit.removeRecipe(new NamespacedKey(this.addons, "prismarine_shards_two"));
        Bukkit.removeRecipe(new NamespacedKey(this.addons, "prismarine_shards_three"));
        Bukkit.removeRecipe(new NamespacedKey(this.addons, "prismarine_shards_four"));
        Bukkit.removeRecipe(new NamespacedKey(this.addons, "name_tag"));
        Bukkit.removeRecipe(new NamespacedKey(this.addons, "chainmail_boots"));
        Bukkit.removeRecipe(new NamespacedKey(this.addons, "chainmail_leggings"));
        Bukkit.removeRecipe(new NamespacedKey(this.addons, "chainmail_chestplate"));
        Bukkit.removeRecipe(new NamespacedKey(this.addons, "chainmail_helmet"));
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
