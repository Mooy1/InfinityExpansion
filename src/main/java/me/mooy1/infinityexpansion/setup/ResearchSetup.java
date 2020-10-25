package me.mooy1.infinityexpansion.setup;

import io.github.thebusybiscuit.slimefun4.core.researching.Research;
import me.mooy1.infinityexpansion.InfinityExpansion;
import me.mooy1.infinityexpansion.lists.Items;
import org.bukkit.NamespacedKey;

import javax.annotation.Nonnull;

public final class ResearchSetup {

    private ResearchSetup() {
    }

    public static void setup(@Nonnull InfinityExpansion plugin) {
        new Research(new NamespacedKey(plugin, "strainers"),
                32367, "strainers", 6)
                .addItems(Items.STRAINER_BASE, Items.BASIC_STRAINER, Items.ADVANCED_STRAINER, Items.REINFORCED_STRAINER)
                .register();
        new Research(new NamespacedKey(plugin, "basic_generators"),
                32368, "basic_generators", 12)
                .addItems(Items.HYDRO_GENERATOR, Items.BASIC_PANEL, Items.ADVANCED_PANEL, Items.GEOTHERMAL_GENERATOR)
                .register();
        new Research(new NamespacedKey(plugin, "advanced_generators"),
                32369, "advanced_generators", 24)
                .addItems(Items.REINFORCED_HYDRO_GENERATOR, Items.CELESTIAL_PANEL, Items.VOID_PANEL, Items.REINFORCED_GEOTHERMAL_GENERATOR)
                .register();
        new Research(new NamespacedKey(plugin, "infinity_materials"),
                32370, "infinity_materials", 24)
                .addItems(Items.COPPER_SINGULARITY, Items.ALUMINUM_SINGULARITY, Items.COAL_SINGULARITY, Items.QUARTZ_SINGULARITY,
                        Items.DIAMOND_SINGULARITY, Items.NETHERITE_SINGULARITY, Items.GOLD_SINGULARITY, Items.IRON_SINGULARITY,
                        Items.MAGNESIUM_SINGULARITY, Items.LEAD_SINGULARITY, Items.MAGNESIUM_SINGULARITY, Items.SILVER_SINGULARITY,
                        Items.EMERALD_SINGULARITY, Items.LAPIS_SINGULARITY, Items.REDSTONE_SINGULARITY, Items.TIN_SINGULARITY,
                        Items.METAL_SINGULARITY, Items.MAGIC_SINGULARITY, Items.FORTUNE_SINGULARITY, Items.EARTH_SINGULARITY)
                .register();
        new Research(new NamespacedKey(plugin, "machine_materials"),
                32371, "machine_materials", 12)
                .addItems(Items.MACHINE_CIRCUIT, Items.MACHINE_CORE, Items.MACHINE_PLATE, Items.MAGSTEEL_PLATE)
                .register();
        new Research(new NamespacedKey(plugin, "basic_materials"),
                32372, "basic_materials", 6)
                .addItems(Items.TITANIUM, Items.ADAMANTITE, Items.MYTHRIL, Items.MAGNONIUM, Items.MAGSTEEL, Items.ENDER_FLAME, Items.END_ESSENCE)
                .register();
        new Research(new NamespacedKey(plugin, "infinity"),
                32373, "infinity", 36)
                .addItems(Items.INFINITE_INGOT, Items.VOID_INGOT, Items.VOID_DUST, Items.VOID_BIT)
                .register();
        new Research(new NamespacedKey(plugin, "basic_machines"),
                32374, "basic_machines", 6)
                .addItems(Items.BASIC_COBBLE_GEN, Items.BASIC_TREE_GROWER, Items.BASIC_VIRTUAL_FARM, Items.ITEM_UPDATER)
                .register();
    }
}
