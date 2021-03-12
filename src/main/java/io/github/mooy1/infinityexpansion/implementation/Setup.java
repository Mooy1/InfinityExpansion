package io.github.mooy1.infinityexpansion.implementation;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinityexpansion.categories.Categories;
import io.github.mooy1.infinityexpansion.categories.InfinityCategory;
import io.github.mooy1.infinityexpansion.categories.MultiCategory;
import io.github.mooy1.infinityexpansion.implementation.blocks.AdvancedAnvil;
import io.github.mooy1.infinityexpansion.implementation.blocks.InfinityWorkbench;
import io.github.mooy1.infinityexpansion.implementation.blocks.StorageForge;
import io.github.mooy1.infinityexpansion.implementation.storage.StorageUnit;
import io.github.mooy1.infinityexpansion.implementation.blocks.Strainer;
import io.github.mooy1.infinityexpansion.implementation.blocks.StrainerBase;
import io.github.mooy1.infinityexpansion.implementation.gear.EnderFlame;
import io.github.mooy1.infinityexpansion.implementation.gear.InfinityArmor;
import io.github.mooy1.infinityexpansion.implementation.gear.InfinityMatrix;
import io.github.mooy1.infinityexpansion.implementation.gear.InfinityTool;
import io.github.mooy1.infinityexpansion.implementation.gear.VeinMinerRune;
import io.github.mooy1.infinityexpansion.implementation.generators.EnergyGenerator;
import io.github.mooy1.infinityexpansion.implementation.generators.InfinityReactor;
import io.github.mooy1.infinityexpansion.implementation.machines.ConversionMachine;
import io.github.mooy1.infinityexpansion.implementation.machines.GearTransformer;
import io.github.mooy1.infinityexpansion.implementation.machines.GeoQuarry;
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
import io.github.mooy1.infinityexpansion.implementation.materials.Items;
import io.github.mooy1.infinityexpansion.implementation.materials.Singularity;
import io.github.mooy1.infinityexpansion.implementation.mobdata.EmptyDataCard;
import io.github.mooy1.infinityexpansion.implementation.mobdata.MobDataCard;
import io.github.mooy1.infinityexpansion.implementation.mobdata.MobDataInfuser;
import io.github.mooy1.infinityexpansion.implementation.mobdata.MobSimulationChamber;
import io.github.mooy1.infinityexpansion.utils.Util;
import io.github.mooy1.infinitylib.core.PluginUtils;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * Sets up items
 *
 * @author Mooy1
 */
public final class Setup {
    
    public static void setup(@Nonnull InfinityExpansion plugin) {
        
        // stacks
        addInfinityEnchants(plugin.getConfig(),
                InfinityArmor.CROWN, InfinityArmor.CHESTPLATE, InfinityArmor.LEGGINGS, InfinityArmor.BOOTS,
                InfinityTool.AXE, InfinityTool.BLADE, InfinityTool.PICKAXE, InfinityTool.SHIELD, InfinityTool.SHOVEL, InfinityTool.BOW
        );
        
        // categories
        Categories.INFINITY_CHEAT.register(plugin);
        MultiCategory.CATEGORY.register(plugin,
                Categories.MAIN_MATERIALS,
                Categories.BASIC_MACHINES,
                Categories.ADVANCED_MACHINES,
                Categories.STORAGE_TRANSPORT,
                Categories.MOB_SIMULATION,
                Categories.INFINITY_MATERIALS,
                InfinityCategory.CATEGORY
        );

        // blocks
        Strainer.setup(plugin);
        StorageUnit.setup(plugin);
        new StrainerBase().register(plugin);
        new InfinityWorkbench().register(plugin);
        new AdvancedAnvil().register(plugin);
        new VeinMinerRune().register(plugin);
        new StorageForge().register(plugin);
        
        // materials
        Items.setup();
        Singularity.setup(plugin);
        new EnderEssence().register(plugin);

        // machines
        Quarry.setup(plugin);
        EnergyGenerator.setup(plugin);
        VoidHarvester.setup(plugin);
        SingularityConstructor.setup(plugin);
        ConversionMachine.setup(plugin);
        VirtualFarm.setup(plugin);
        TreeGrower.setup(plugin);
        MaterialGenerator.setup(plugin);
        MobDataCard.setup(plugin);
        new MobDataInfuser().register(plugin);
        new StoneworksFactory().register(plugin);
        new GeoQuarry().register(plugin);
        new GearTransformer().register(plugin);
        new PoweredBedrock().register(plugin);
        new InfinityReactor().register(plugin);
        new ResourceSynthesizer().register(plugin);
        new MobSimulationChamber().register(plugin);
        new EmptyDataCard().register(plugin);
        
        // tools and gear
        InfinityArmor.setup(plugin);
        InfinityTool.setup(plugin);
        new InfinityMatrix().register(plugin);
        new EnderFlame().register(plugin);
        
        SlimefunExtension.setup(plugin);
    }
    
    private static void addInfinityEnchants(FileConfiguration config, SlimefunItemStack... items) {
        ConfigurationSection typeSection = config.getConfigurationSection("infinity-enchant-levels");

        if (typeSection == null) {
            PluginUtils.log(Level.SEVERE, "Config section \"infinity-enchant-levels\" missing, Check your config and report this!");
            return;
        }

        for (SlimefunItemStack item : items) {
            ItemMeta meta = item.getItemMeta();
            
            meta.setUnbreakable(true);
            
            List<String> lore = meta.getLore();
            if (lore == null) {
                lore = new ArrayList<>();
            }
            lore.add(ChatColor.AQUA + "Soulbound");
            meta.setLore(lore);
            item.setItemMeta(meta);

            String itemPath = item.getItemId().replace("INFINITY_", "").toLowerCase();
            ConfigurationSection itemSection = typeSection.getConfigurationSection(itemPath);
            
            if (itemSection != null) {
                item.addUnsafeEnchantments(Util.getEnchants(itemSection));
            } else {
                PluginUtils.log(Level.SEVERE, "Config section for " + itemPath + " missing, Check your config and report this!");
            }
        }

        InfinityExpansion.getInstance().saveConfig();
    }
    
}