package io.github.mooy1.infinityexpansion.setup;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinityexpansion.implementation.blocks.AdvancedAnvil;
import io.github.mooy1.infinityexpansion.implementation.blocks.InfinityWorkbench;
import io.github.mooy1.infinityexpansion.implementation.blocks.StorageForge;
import io.github.mooy1.infinityexpansion.implementation.blocks.StorageUnit;
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
import io.github.mooy1.infinityexpansion.implementation.materials.CompressedItem;
import io.github.mooy1.infinityexpansion.implementation.materials.EnderEssence;
import io.github.mooy1.infinityexpansion.implementation.materials.InfinityItem;
import io.github.mooy1.infinityexpansion.implementation.materials.MachineItem;
import io.github.mooy1.infinityexpansion.implementation.materials.MiscItem;
import io.github.mooy1.infinityexpansion.implementation.materials.Singularity;
import io.github.mooy1.infinityexpansion.implementation.materials.SmelteryItem;
import io.github.mooy1.infinityexpansion.implementation.mobdata.EmptyDataCard;
import io.github.mooy1.infinityexpansion.implementation.mobdata.MobDataCard;
import io.github.mooy1.infinityexpansion.implementation.mobdata.MobDataInfuser;
import io.github.mooy1.infinityexpansion.implementation.mobdata.MobSimulationChamber;
import io.github.mooy1.infinityexpansion.setup.categories.Categories;
import io.github.mooy1.infinityexpansion.setup.categories.InfinityCategory;
import io.github.mooy1.infinityexpansion.setup.categories.MultiCategory;
import io.github.mooy1.infinitylib.PluginUtils;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

/**
 * Sets up items
 *
 * @author Mooy1
 */
public final class Setup {

    public static void setup(@Nonnull InfinityExpansion plugin) {
        
        // stacks
        addEnchantsFromConfig("infinity-enchant-levels", "INFINITY", plugin.getConfig(),
                InfinityArmor.CROWN, InfinityArmor.CHESTPLATE, InfinityArmor.LEGGINGS, InfinityArmor.BOOTS,
                InfinityTool.AXE, InfinityTool.BLADE, InfinityTool.PICKAXE, InfinityTool.SHIELD, InfinityTool.SHOVEL, InfinityTool.BOW
        );
        
        // categories
        MultiCategory.CATEGORY.register(plugin,
                Categories.MAIN_MATERIALS,
                Categories.BASIC_MACHINES,
                Categories.ADVANCED_MACHINES,
                Categories.STORAGE_TRANSPORT,
                Categories.MOB_SIMULATION,
                Categories.INFINITY_MATERIALS,
                InfinityCategory.CATEGORY
        );
        Categories.INFINITY_CHEAT.register(plugin);
        
        // items
        PluginUtils.registerAddonInfoItem(Categories.MAIN_MATERIALS, plugin);

        Strainer.setup(plugin);
        StorageUnit.setup(plugin);
        new StrainerBase().register(plugin);
        new InfinityWorkbench().register(plugin);
        new AdvancedAnvil().register(plugin);
        new VeinMinerRune().register(plugin);
        new StorageForge().register(plugin);
        
        // materials
        CompressedItem.setup(plugin);
        Singularity.setup(plugin);
        SmelteryItem.setup(plugin);
        MachineItem.setup(plugin);
        MiscItem.setup(plugin);
        InfinityItem.setup(plugin);
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
        
        SlimefunConstructors.setup(plugin);
    }
    
    private static void addEnchantsFromConfig(String section, String gearType, FileConfiguration config, SlimefunItemStack... items) {
        ConfigurationSection typeSection = config.getConfigurationSection(section);

        if (typeSection == null) {
            PluginUtils.log(Level.SEVERE, "Config section " + section + " missing, Check your config and report this!");
            return;
        }

        for (SlimefunItemStack item : items) {
            ItemMeta meta = item.getItemMeta();
            Objects.requireNonNull(meta).setUnbreakable(true);
            List<String> lore = meta.getLore();
            Objects.requireNonNull(lore).add("&bSoulbound");
            meta.setLore(lore);
            item.setItemMeta(meta);

            String itemPath = item.getItemId().replace(gearType + "_", "").toLowerCase();
            ConfigurationSection itemSection = typeSection.getConfigurationSection(itemPath);
            if (itemSection == null) {
                PluginUtils.log(Level.SEVERE, "Config section " + itemPath + " missing, Check your config and report this!");
                continue;
            }

            for (String path : itemSection.getKeys(false)) {
                int level = config.getInt(section + "." + itemPath + "." + path);
                if (level > 0 && level <= Short.MAX_VALUE) {
                    Enchantment e = getEnchantFromString(path);
                    if (e == null) {
                        PluginUtils.log(Level.WARNING, "Failed to add enchantment " + path + ", your config may be messed up, report this!");
                        continue;
                    }
                    item.addUnsafeEnchantment(e, level);
                } else if (level != 0) {
                    config.set(section + "." + itemPath + "." + path, 1);
                    PluginUtils.log(Level.WARNING, "Enchantment level " + level + " for enchantment " + path + " is not allowed, resetting to 1, please check your config and update it with a correct value");
                }
            }
        }

        InfinityExpansion.getInstance().saveConfig();
    }

    @Nullable //TODO move to infinity lib in map of all
    private static Enchantment getEnchantFromString(String string) {
        switch (string) {
            case "sharpness": return Enchantment.DAMAGE_ALL;
            case "efficiency": return Enchantment.DIG_SPEED;
            case "protection": return Enchantment.PROTECTION_ENVIRONMENTAL;
            case "fire-aspect": return Enchantment.FIRE_ASPECT;
            case "fortune": return Enchantment.LOOT_BONUS_BLOCKS;
            case "looting": return Enchantment.LOOT_BONUS_MOBS;
            case "silk-touch": return Enchantment.SILK_TOUCH;
            case "thorns": return Enchantment.THORNS;
            case "aqua-affinity": return Enchantment.WATER_WORKER;
            case "power": return Enchantment.ARROW_DAMAGE;
            case "flame": return Enchantment.ARROW_FIRE;
            case "infinity": return Enchantment.ARROW_INFINITE;
            case "punch": return Enchantment.ARROW_KNOCKBACK;
            case "feather-falling": return Enchantment.PROTECTION_FALL;
            default: return null;
        }
    }
    
}