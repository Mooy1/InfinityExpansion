package io.github.mooy1.infinityexpansion.items.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.Nonnull;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import io.github.mooy1.infinityexpansion.items.Materials;
import io.github.mooy1.infinityexpansion.items.materials.Strainer;
import io.github.mooy1.infinityexpansion.utils.Util;
import io.github.mooy1.infinitylib.presets.MenuPreset;
import io.github.mooy1.infinitylib.slimefun.AbstractTickingContainer;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;

/**
 * Generates items slowly using up strainers, must be waterlogged
 *
 * @author Mooy1
 */
public final class StrainerBase extends AbstractTickingContainer implements RecipeDisplayItem {
    
    private static final int STATUS_SLOT = MenuPreset.INPUT;
    private static final int[] OUTPUT_SLOTS = Util.LARGE_OUTPUT;
    private static final int[] INPUT_SLOTS = {
            MenuPreset.INPUT + 27
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
    
    private final int time;

    public StrainerBase(Category category, SlimefunItemStack item, RecipeType type, ItemStack[] recipe, int time) {
        super(category, item, type, recipe);
        this.time = time;
    }

    @Override
    protected void onBreak(@Nonnull BlockBreakEvent e, @Nonnull BlockMenu inv, @Nonnull Location l) {
        inv.dropItems(l, OUTPUT_SLOTS);
        inv.dropItems(l, INPUT_SLOTS);
    }

    @Override
    public void setupMenu(@Nonnull BlockMenuPreset blockMenuPreset) {
        for (int i : MenuPreset.INPUT_BORDER) {
            blockMenuPreset.addItem(i, MenuPreset.STATUS_ITEM, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : MenuPreset.INPUT_BORDER) {
            blockMenuPreset.addItem(i + 27, MenuPreset.INPUT_ITEM, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : Util.LARGE_OUTPUT_BORDER) {
            blockMenuPreset.addItem(i, MenuPreset.OUTPUT_ITEM, ChestMenuUtils.getEmptyClickHandler());
        }
        blockMenuPreset.addItem(STATUS_SLOT, MenuPreset.LOADING, ChestMenuUtils.getEmptyClickHandler());
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
            items.add(Materials.BASIC_STRAINER);
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
    protected void tick(@Nonnull BlockMenu inv, @Nonnull Block b) {

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
        
        Random random = ThreadLocalRandom.current();

        //progress

        if (random.nextInt(this.time / speed) != 0) {

            if (inv.hasViewer()) {
                inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.LIME_STAINED_GLASS_PANE, "&aCollecting..."));
            }

            return;
        }

        //fish

        if (random.nextInt(10000) == 0) {
            inv.pushItem(POTATO, OUTPUT_SLOTS);
        }

        ItemStack output = OUTPUTS[random.nextInt(OUTPUTS.length)];

        //check fits

        if (!inv.fits(output, OUTPUT_SLOTS)) {

            if (inv.hasViewer()) {
                inv.replaceExistingItem(STATUS_SLOT, MenuPreset.NO_ROOM);
            }

            return;
        }

        //output

        inv.pushItem(output.clone(), OUTPUT_SLOTS);

        if (inv.hasViewer()) {
            inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.LIME_STAINED_GLASS_PANE, "&aMaterial Collected!"));
        }

        //reduce durability

        if (random.nextInt(strainer.getEnchantmentLevel(Enchantment.DURABILITY) + 3 * strainer.getEnchantmentLevel(Enchantment.MENDING) + 1) == 0) {
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
    }

}
