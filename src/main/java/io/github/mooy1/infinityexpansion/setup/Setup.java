package io.github.mooy1.infinityexpansion.setup;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinityexpansion.implementation.gear.EnderFlame;
import io.github.mooy1.infinityexpansion.implementation.gear.InfinityArmor;
import io.github.mooy1.infinityexpansion.implementation.gear.InfinityMatrix;
import io.github.mooy1.infinityexpansion.implementation.gear.InfinityTool;
import io.github.mooy1.infinityexpansion.implementation.gear.VeinMinerRune;
import io.github.mooy1.infinityexpansion.implementation.items.AdvancedAnvil;
import io.github.mooy1.infinityexpansion.implementation.items.InfinityWorkbench;
import io.github.mooy1.infinityexpansion.implementation.items.Strainer;
import io.github.mooy1.infinityexpansion.implementation.items.StrainerBase;
import io.github.mooy1.infinityexpansion.implementation.machines.ConversionMachine;
import io.github.mooy1.infinityexpansion.implementation.machines.EnergyGenerator;
import io.github.mooy1.infinityexpansion.implementation.machines.GearTransformer;
import io.github.mooy1.infinityexpansion.implementation.machines.InfinityReactor;
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
import io.github.mooy1.infinityexpansion.implementation.materials.CompressedCobblestone;
import io.github.mooy1.infinityexpansion.implementation.materials.EnderEssence;
import io.github.mooy1.infinityexpansion.implementation.materials.MachineMaterial;
import io.github.mooy1.infinityexpansion.implementation.materials.MainMaterial;
import io.github.mooy1.infinityexpansion.implementation.materials.Singularity;
import io.github.mooy1.infinityexpansion.implementation.mobdata.EmptyDataCard;
import io.github.mooy1.infinityexpansion.implementation.mobdata.MobDataCard;
import io.github.mooy1.infinityexpansion.implementation.mobdata.MobDataInfuser;
import io.github.mooy1.infinityexpansion.implementation.mobdata.MobSimulationChamber;
import io.github.mooy1.infinityexpansion.implementation.storage.StorageDuct;
import io.github.mooy1.infinityexpansion.implementation.storage.StorageForge;
import io.github.mooy1.infinityexpansion.implementation.storage.StorageNetworkViewer;
import io.github.mooy1.infinityexpansion.implementation.storage.StorageUnit;
import io.github.mooy1.infinityexpansion.implementation.storage.WirelessConfigurator;
import io.github.mooy1.infinityexpansion.implementation.storage.WirelessInputNode;
import io.github.mooy1.infinityexpansion.implementation.storage.WirelessOutputNode;
import io.github.mooy1.infinityexpansion.lists.Categories;
import io.github.mooy1.infinityexpansion.lists.Items;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;

import javax.annotation.Nonnull;

/**
 * Sets up items
 *
 * @author Mooy1
 */
public final class Setup {

    public static void setup(@Nonnull InfinityExpansion plugin) {

        //slimefun item stacks

        Items.setup(plugin.getConfig());

        //categories in order

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

        //basic

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

        //main

        new SlimefunItem(Categories.MAIN_MATERIALS, Items.INFINITY_ADDON_INFO, RecipeType.NULL, null).register(plugin);
        new InfinityWorkbench().register(plugin);
        new AdvancedAnvil().register(plugin);
        new VeinMinerRune(plugin).register(plugin);

        //storage and transport

        new WirelessInputNode().register(plugin);
        new WirelessOutputNode().register(plugin);
        new WirelessConfigurator(plugin).register(plugin);
        new StorageForge().register(plugin);
        for (StorageUnit.Type type : StorageUnit.Type.values()) {
            new StorageUnit(type).register(plugin);
        }
        new StorageNetworkViewer().register(plugin);
        new StorageDuct().register(plugin);

        //mob simulation

        new MobSimulationChamber().register(plugin);
        new EmptyDataCard().register(plugin);
        for (MobDataCard.Type type : MobDataCard.Type.values()) {
            new MobDataCard(type).register(plugin);
        }
        new MobDataInfuser().register(plugin);

        //machine

        new StoneworksFactory().register(plugin);
        for (MainMaterial.Type type : MainMaterial.Type.values()) {
            new MainMaterial(type).register(plugin);
        }
        for (MachineMaterial.Type type : MachineMaterial.Type.values()) {
            new MachineMaterial(type).register(plugin);
        }
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

        for (CompressedCobblestone.Type type : CompressedCobblestone.Type.values()) {
            new CompressedCobblestone(type).register(plugin);
        }
        for (int i = 0 ; i < SingularityConstructor.RECIPES.size() ; i++) {
            new Singularity(i).register(plugin);
        }
        new EnderEssence(plugin).register(plugin);

        //gear

        new InfinityMatrix(plugin).register(plugin);
        for (InfinityArmor.Type type : InfinityArmor.Type.values()) {
            new InfinityArmor(type).register(plugin);
        }
        for (InfinityTool.Type type : InfinityTool.Type.values()) {
            new InfinityTool(type).register(plugin);
        }
        new EnderFlame().register(plugin);

        //sf constructors
        SlimefunConstructors.setup(plugin);
    }

    private static void registerCategories(Category... categories) {
        for (Category category : categories) {
            MainCategory.categories.add(category);
            category.register();
        }
    }

}