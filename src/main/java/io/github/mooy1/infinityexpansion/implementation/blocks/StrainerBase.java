package io.github.mooy1.infinityexpansion.implementation.blocks;

import io.github.mooy1.infinityexpansion.implementation.materials.SmelteryItem;
import io.github.mooy1.infinityexpansion.setup.categories.Categories;
import io.github.mooy1.infinityexpansion.utils.Util;
import io.github.mooy1.infinitylib.abstracts.AbstractTicker;
import io.github.mooy1.infinitylib.math.RandomUtils;
import io.github.mooy1.infinitylib.presets.MenuPreset;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * Generates items slowly using up strainers, must be waterlogged
 *
 * @author Mooy1
 */
public final class StrainerBase extends AbstractTicker implements RecipeDisplayItem {
    
    private static final int TIME = 48;
    public static final SlimefunItemStack ITEM = new SlimefunItemStack(
            "STRAINER_BASE",
            Material.SANDSTONE_WALL,
            "&7Strainer Base"
    );
    
    private static final int STATUS_SLOT = MenuPreset.slot1;
    private static final int[] OUTPUT_SLOTS = Util.largeOutput;
    private static final int[] INPUT_SLOTS = {
            MenuPreset.slot1 + 27
    };

    private static final ItemStack[] OUTPUTS = {
            new ItemStack(Material.STICK),
            new ItemStack(Material.SAND),
            new ItemStack(Material.GRAVEL),
            new ItemStack(Material.QUARTZ),
            new ItemStack(Material.REDSTONE),
            new ItemStack(Material.EMERALD),
            new SlimefunItemStack(SlimefunItems.MAGNESIUM_DUST, 1),
            new SlimefunItemStack(SlimefunItems.COPPER_DUST, 1),
            new SlimefunItemStack(SlimefunItems.COPPER_DUST, 1),
            new SlimefunItemStack(SlimefunItems.SILVER_DUST, 1),
            new SlimefunItemStack(SlimefunItems.ALUMINUM_DUST, 1),
            new SlimefunItemStack(SlimefunItems.LEAD_DUST, 1),
            new SlimefunItemStack(SlimefunItems.IRON_DUST, 1),
            new SlimefunItemStack(SlimefunItems.GOLD_DUST, 1),
            new SlimefunItemStack(SlimefunItems.TIN_DUST, 1),
            new SlimefunItemStack(SlimefunItems.ZINC_DUST, 1),
    };

    public StrainerBase() {
        super(Categories.BASIC_MACHINES, ITEM, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                new ItemStack(Material.STICK), new ItemStack(Material.STRING), new ItemStack(Material.STICK),
                new ItemStack(Material.STICK), new ItemStack(Material.STRING), new ItemStack(Material.STICK),
                SmelteryItem.MAGSTEEL, SmelteryItem.MAGSTEEL, SmelteryItem.MAGSTEEL,
        });
        
        registerBlockHandler(getId(), (p, b, stack, reason) -> {
            BlockMenu inv = BlockStorage.getInventory(b);

            if (inv != null) {
                Location l = b.getLocation();
                inv.dropItems(l, OUTPUT_SLOTS);
                inv.dropItems(l, INPUT_SLOTS);
            }

            return true;
        });
    }
    
    @Override
    public void setupMenu(@Nonnull BlockMenuPreset blockMenuPreset) {
        for (int i : MenuPreset.slotChunk1) {
            blockMenuPreset.addItem(i, MenuPreset.borderItemStatus, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : MenuPreset.slotChunk1) {
            blockMenuPreset.addItem(i + 27, MenuPreset.borderItemInput, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : Util.largeOutputBorder) {
            blockMenuPreset.addItem(i, MenuPreset.borderItemOutput, ChestMenuUtils.getEmptyClickHandler());
        }
        blockMenuPreset.addItem(STATUS_SLOT, MenuPreset.loadingItemRed, ChestMenuUtils.getEmptyClickHandler());
    }
    
    @Nonnull
    @Override
    public int[] getTransportSlots(@Nonnull DirtyChestMenu menu, @Nonnull ItemTransportFlow flow, @Nonnull ItemStack item) {
        if (flow == ItemTransportFlow.WITHDRAW) {
            return OUTPUT_SLOTS;
        }
        if (Strainer.getStrainer(item) > 0) {
            return INPUT_SLOTS;
        }
        return new int[0];
    }

    @Override
    public void onNewInstance(@Nonnull BlockMenu menu, @Nonnull Block b) {
        
    }
    
    private static final ItemStack POTATO = new CustomItem(Material.POTATO, "&7:&6Potatofish&7:", "&eLucky");

    @Nonnull
    @Override
    public List<ItemStack> getDisplayRecipes() {
        List<ItemStack> items = new ArrayList<>();
        
        for (ItemStack output : OUTPUTS) {
            items.add(Strainer.BASIC);
            items.add(output);
        }
        
        return items;
    }

    @Nonnull
    @Override
    public String getRecipeSectionLabel(@Nonnull Player p) {
        return "&7Collects:";
    }

    @Override
    public void tick(@Nonnull BlockMenu inv, @Nonnull Block b, @Nonnull Config config) {

        //check water
        if (!Util.isWaterLogged(b)) {
            return;
        }

        //check input

        ItemStack strainer = inv.getItemInSlot(INPUT_SLOTS[0]);
        int speed = Strainer.getStrainer(strainer);

        if (speed == 0) {

            if (inv.hasViewer()) {
                inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.BARRIER, "&cInput a Strainer!"));
            }

            return;
        }

        //progress

        if (!RandomUtils.chanceIn(TIME / speed)) {

            if (inv.hasViewer()) {
                inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.LIME_STAINED_GLASS_PANE, "&aCollecting..."));
            }

            return;
        }

        //fish

        if (RandomUtils.chanceIn(10000)) {
            inv.pushItem(POTATO, OUTPUT_SLOTS);
        }

        ItemStack output = RandomUtils.randomOutput(OUTPUTS);

        //check fits

        if (!inv.fits(output, OUTPUT_SLOTS)) {

            if (inv.hasViewer()) {
                inv.replaceExistingItem(STATUS_SLOT, MenuPreset.notEnoughRoom);
            }

            return;
        }

        //output

        inv.pushItem(output, OUTPUT_SLOTS);

        if (inv.hasViewer()) {
            inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.LIME_STAINED_GLASS_PANE, "&aMaterial Collected!"));
        }

        //reduce durability

        if (RandomUtils.chanceIn(strainer.getEnchantmentLevel(Enchantment.DURABILITY) + 3 * strainer.getEnchantmentLevel(Enchantment.MENDING) + 1)) {
            ItemMeta itemMeta = strainer.getItemMeta();
            Damageable durability = (Damageable) itemMeta;

            int current = durability.getDamage();

            if (current + 1 == Material.FISHING_ROD.getMaxDurability()) {

                inv.consumeItem(INPUT_SLOTS[0]);

            } else { //reduce

                ((Damageable) itemMeta).setDamage(current + 1);
                strainer.setItemMeta(itemMeta);
                inv.replaceExistingItem(INPUT_SLOTS[0], strainer);

            }
        }
        //((Waterlogged) blockData).setWaterlogged(false);
        //b.setBlockData(blockData);
    }

}
