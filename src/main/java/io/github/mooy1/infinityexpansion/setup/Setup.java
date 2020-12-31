package io.github.mooy1.infinityexpansion.setup;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinityexpansion.implementation.gear.EnderFlame;
import io.github.mooy1.infinityexpansion.implementation.gear.InfinityArmor;
import io.github.mooy1.infinityexpansion.implementation.gear.InfinityMatrix;
import io.github.mooy1.infinityexpansion.implementation.gear.InfinityTool;
import io.github.mooy1.infinityexpansion.implementation.gear.VeinMinerRune;
import io.github.mooy1.infinityexpansion.implementation.blocks.AdvancedAnvil;
import io.github.mooy1.infinityexpansion.implementation.blocks.InfinityWorkbench;
import io.github.mooy1.infinityexpansion.implementation.blocks.Strainer;
import io.github.mooy1.infinityexpansion.implementation.blocks.StrainerBase;
import io.github.mooy1.infinityexpansion.implementation.machines.ConversionMachine;
import io.github.mooy1.infinityexpansion.implementation.generators.EnergyGenerator;
import io.github.mooy1.infinityexpansion.implementation.machines.GearTransformer;
import io.github.mooy1.infinityexpansion.implementation.generators.InfinityReactor;
import io.github.mooy1.infinityexpansion.implementation.machines.ItemUpdater;
import io.github.mooy1.infinityexpansion.implementation.machines.MaterialGenerator;
import io.github.mooy1.infinityexpansion.implementation.machines.PoweredBedrock;
import io.github.mooy1.infinityexpansion.implementation.machines.Quarry;
import io.github.mooy1.infinityexpansion.implementation.machines.ResourceSynthesizer;
import io.github.mooy1.infinityexpansion.implementation.machines.SingularityConstructor;
import io.github.mooy1.infinityexpansion.implementation.machines.StoneworksFactory;
import io.github.mooy1.infinityexpansion.implementation.machines.TreeGrower;
import io.github.mooy1.infinityexpansion.implementation.machines.VirtualFarm;
import io.github.mooy1.infinityexpansion.implementation.machines.VoidHarvester;
import io.github.mooy1.infinityexpansion.implementation.materials.EnderEssence;
import io.github.mooy1.infinityexpansion.implementation.materials.Singularity;
import io.github.mooy1.infinityexpansion.implementation.mobdata.EmptyDataCard;
import io.github.mooy1.infinityexpansion.implementation.mobdata.MobDataCard;
import io.github.mooy1.infinityexpansion.implementation.mobdata.MobDataInfuser;
import io.github.mooy1.infinityexpansion.implementation.mobdata.MobSimulationChamber;
import io.github.mooy1.infinityexpansion.implementation.blocks.StorageForge;
import io.github.mooy1.infinityexpansion.implementation.blocks.StorageUnit;
import io.github.mooy1.infinityexpansion.lists.Categories;
import io.github.mooy1.infinityexpansion.lists.InfinityRecipes;
import io.github.mooy1.infinityexpansion.lists.Items;
import io.github.mooy1.infinityexpansion.lists.RecipeTypes;
import io.github.mooy1.infinityexpansion.utils.RecipeUtils;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * Sets up items
 *
 * @author Mooy1
 */
public final class Setup {

    public static void setup(@Nonnull InfinityExpansion plugin) {
        
        Items.setup(plugin.getConfig());
        
        Categories.MAIN.register();

        registerCategories(
                Categories.MAIN_MATERIALS,
                Categories.BASIC_MACHINES,
                Categories.ADVANCED_MACHINES,
                Categories.STORAGE_TRANSPORT,
                Categories.MOB_SIMULATION,
                Categories.INFINITY_RECIPES,
                Categories.INFINITY_MATERIALS
        );

        Categories.INFINITY_CHEAT.register();


        new StrainerBase().register(plugin);
        for (Strainer.Type type : Strainer.Type.values()) {
            new Strainer(type).register(plugin);
        }
        for (VirtualFarm.Type type : VirtualFarm.Type.values()) {
            new VirtualFarm(type).register(plugin);
        }
        for (TreeGrower.Type type : TreeGrower.Type.values()) {
            new TreeGrower(type).register(plugin);
        }
        for (MaterialGenerator.Type type : MaterialGenerator.Type.values()) {
            new MaterialGenerator(type).register(plugin);
        }
        
        new SlimefunItem(Categories.MAIN_MATERIALS, Items.ADDON_INFO, RecipeType.NULL, null).register(plugin);
        new InfinityWorkbench().register(plugin);
        new AdvancedAnvil().register(plugin);
        new VeinMinerRune(plugin).register(plugin);


        new StorageForge().register(plugin);
        for (StorageUnit.Type type : StorageUnit.Type.values()) {
            new StorageUnit(type).register(plugin);
        }
        
        new MobSimulationChamber().register(plugin);
        new EmptyDataCard().register(plugin);
        for (MobDataCard.Type type : MobDataCard.Type.values()) {
            new MobDataCard(type).register(plugin);
        }
        new MobDataInfuser().register(plugin);

        new StoneworksFactory().register(plugin);
        
        new SlimefunItem(Categories.MAIN_MATERIALS, Items.INFINITE_INGOT, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Items.EARTH_SINGULARITY, Items.MYTHRIL, Items.FORTUNE_SINGULARITY, Items.MAGIC_SINGULARITY, Items.VOID_INGOT, Items.METAL_SINGULARITY,
                null, null, null
        }).register(plugin);
        new SlimefunItem(Categories.MAIN_MATERIALS, Items.VOID_BIT, RecipeTypes.VOID_HARVESTER, null).register(plugin);
        new SlimefunItem(Categories.MAIN_MATERIALS, Items.VOID_DUST, RecipeType.ENHANCED_CRAFTING_TABLE, RecipeUtils.Compress(Items.VOID_BIT)).register(plugin);
        new SlimefunItem(Categories.MAIN_MATERIALS, Items.VOID_INGOT, RecipeType.ENHANCED_CRAFTING_TABLE, RecipeUtils.Compress(Items.VOID_DUST)).register(plugin);
        new SlimefunItem(Categories.INFINITY_MATERIALS, Items.FORTUNE_SINGULARITY, RecipeType.SMELTERY, new ItemStack[] {
                Items.GOLD_SINGULARITY, Items.DIAMOND_SINGULARITY, Items.EMERALD_SINGULARITY, Items.NETHERITE_SINGULARITY, Items.ADAMANTITE,
                null, null, null, null
        }).register(plugin);
        new SlimefunItem(Categories.INFINITY_MATERIALS, Items.MAGIC_SINGULARITY, RecipeType.SMELTERY, new ItemStack[] {
                Items.REDSTONE_SINGULARITY, Items.LAPIS_SINGULARITY, Items.QUARTZ_SINGULARITY, Items.MAGNESIUM_SINGULARITY, Items.MAGNONIUM,
                null, null, null, null
        }).register(plugin);
        new SlimefunItem(Categories.INFINITY_MATERIALS, Items.EARTH_SINGULARITY, RecipeType.SMELTERY, new ItemStack[] {
                Items.COMPRESSED_COBBLESTONE_4, Items.COAL_SINGULARITY, Items.IRON_SINGULARITY, Items.COPPER_SINGULARITY, Items.LEAD_SINGULARITY,
                null, null, null, null
        }).register(plugin);
        new SlimefunItem(Categories.INFINITY_MATERIALS, Items.METAL_SINGULARITY, RecipeType.SMELTERY, new ItemStack[] {
                Items.SILVER_SINGULARITY, Items.ALUMINUM_SINGULARITY, Items.TIN_SINGULARITY, Items.ZINC_SINGULARITY, Items.TITANIUM,
                null, null, null, null
        }).register(plugin);
        new SlimefunItem(Categories.MAIN_MATERIALS, Items.MAGSTEEL, RecipeType.SMELTERY, new ItemStack[] {
                SlimefunItems.MAGNESIUM_INGOT, SlimefunItems.STEEL_INGOT, SlimefunItems.MAGNESIUM_DUST,
                null, null, null, null, null, null
        }).register(plugin);
        new SlimefunItem(Categories.MAIN_MATERIALS, Items.TITANIUM, RecipeType.SMELTERY, new ItemStack[] {
                SlimefunItems.REINFORCED_ALLOY_INGOT, SlimefunItems.DAMASCUS_STEEL_INGOT, SlimefunItems.HARDENED_METAL_INGOT,
                null, null, null, null, null, null
        }).register(plugin);
        new SlimefunItem(Categories.MAIN_MATERIALS, Items.MYTHRIL, RecipeType.SMELTERY, new ItemStack[] {
                SlimefunItems.REINFORCED_ALLOY_INGOT, Items.IRON_SINGULARITY, SlimefunItems.HARDENED_METAL_INGOT,
                null, null, null, null, null, null
        }).register(plugin);
        new SlimefunItem(Categories.MAIN_MATERIALS, Items.ADAMANTITE, RecipeType.SMELTERY, new ItemStack[] {
                SlimefunItems.REDSTONE_ALLOY, Items.DIAMOND_SINGULARITY, Items.MAGSTEEL,
                null, null, null, null, null, null
        }).register(plugin);
        new SlimefunItem(Categories.MAIN_MATERIALS, Items.MAGNONIUM, RecipeType.SMELTERY, new ItemStack[] {
                Items.MAGSTEEL, Items.MAGNESIUM_SINGULARITY, Items.ENDER_ESSENCE,
                null, null, null, null, null, null
        }).register(plugin);

        new SlimefunItem(Categories.MAIN_MATERIALS, Items.MAGSTEEL_PLATE, RecipeType.ENHANCED_CRAFTING_TABLE, RecipeUtils.Compress(Items.MAGSTEEL)).register(plugin);
        new SlimefunItem(Categories.MAIN_MATERIALS, Items.MACHINE_PLATE, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                SlimefunItems.REINFORCED_ALLOY_INGOT, SlimefunItems.REINFORCED_PLATE, SlimefunItems.REINFORCED_ALLOY_INGOT,
                Items.MAGSTEEL_PLATE, Items.TITANIUM, Items.MAGSTEEL_PLATE,
                SlimefunItems.REINFORCED_ALLOY_INGOT, SlimefunItems.REINFORCED_PLATE, SlimefunItems.REINFORCED_ALLOY_INGOT
        }).register(plugin);
        new SlimefunItem(Categories.MAIN_MATERIALS, Items.MACHINE_CIRCUIT, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                SlimefunItems.COPPER_INGOT, SlimefunItems.ELECTRO_MAGNET, SlimefunItems.COPPER_INGOT,
                SlimefunItems.COPPER_INGOT, SlimefunItems.SILICON, SlimefunItems.COPPER_INGOT,
                SlimefunItems.COPPER_INGOT, SlimefunItems.ELECTRO_MAGNET, SlimefunItems.COPPER_INGOT
        }).register(plugin);
        new SlimefunItem(Categories.MAIN_MATERIALS, Items.MACHINE_CORE, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                Items.TITANIUM, Items.MACHINE_CIRCUIT, Items.TITANIUM,
                Items.MACHINE_CIRCUIT, Items.MACHINE_PLATE, Items.MACHINE_CIRCUIT,
                Items.TITANIUM, Items.MACHINE_CIRCUIT, Items.TITANIUM,
        }).register(plugin);
        new SlimefunItem(Categories.INFINITY_CHEAT, Items.INFINITE_MACHINE_CIRCUIT, RecipeTypes.INFINITY_WORKBENCH, InfinityRecipes.getRecipe(Items.INFINITE_MACHINE_CIRCUIT)).register(plugin);
        new SlimefunItem(Categories.INFINITY_CHEAT, Items.INFINITE_MACHINE_CORE, RecipeTypes.INFINITY_WORKBENCH, InfinityRecipes.getRecipe(Items.INFINITE_MACHINE_CORE)).register(plugin);
        
        for (Quarry.Type type : Quarry.Type.values()) {
            new Quarry(type).register(plugin);
        }
        for (EnergyGenerator.Type type : EnergyGenerator.Type.values()) {
            new EnergyGenerator(type).register(plugin);
        }
        for (VoidHarvester.Type type : VoidHarvester.Type.values()) {
            new VoidHarvester(type).register(plugin);
        }
        for (SingularityConstructor.Type type : SingularityConstructor.Type.values()) {
            new SingularityConstructor(type).register(plugin);
        }
        for (ConversionMachine.Type type : ConversionMachine.Type.values()) {
            new ConversionMachine(type).register(plugin);
        }

        new GearTransformer().register(plugin);
        new PoweredBedrock().register(plugin);
        new ItemUpdater().register(plugin);
        new InfinityReactor().register(plugin);
        new ResourceSynthesizer().register(plugin);

        //materials

        new SlimefunItem(Categories.MAIN_MATERIALS, Items.COMPRESSED_COBBLESTONE_1, RecipeType.ENHANCED_CRAFTING_TABLE, RecipeUtils.Compress(new ItemStack(Material.COBBLESTONE))).register(plugin);
        new SlimefunItem(Categories.MAIN_MATERIALS, Items.COMPRESSED_COBBLESTONE_2, RecipeType.ENHANCED_CRAFTING_TABLE, RecipeUtils.Compress(Items.COMPRESSED_COBBLESTONE_1)).register(plugin);
        new SlimefunItem(Categories.MAIN_MATERIALS, Items.COMPRESSED_COBBLESTONE_3, RecipeType.ENHANCED_CRAFTING_TABLE, RecipeUtils.Compress(Items.COMPRESSED_COBBLESTONE_2)).register(plugin);
        new SlimefunItem(Categories.MAIN_MATERIALS, Items.COMPRESSED_COBBLESTONE_4, RecipeType.ENHANCED_CRAFTING_TABLE, RecipeUtils.Compress(Items.COMPRESSED_COBBLESTONE_3)).register(plugin);
        new SlimefunItem(Categories.MAIN_MATERIALS, Items.COMPRESSED_COBBLESTONE_5, RecipeType.ENHANCED_CRAFTING_TABLE, RecipeUtils.Compress(Items.COMPRESSED_COBBLESTONE_4)).register(plugin);

        Singularity.setup(plugin);
        new EnderEssence(plugin).register(plugin);
        
        new InfinityMatrix(plugin).register(plugin);
        for (InfinityArmor.Type type : InfinityArmor.Type.values()) {
            new InfinityArmor(type).register(plugin);
        }
        for (SlimefunItemStack item : InfinityTool.ITEMS) {
            new InfinityTool(item).register(plugin);
        }
        new EnderFlame().register(plugin);
        
        SlimefunConstructors.setup(plugin);
    }

    private static void registerCategories(Category... categories) {
        for (Category category : categories) {
            Categories.MAIN.categories.add(category);
            category.register();
        }
    }

}