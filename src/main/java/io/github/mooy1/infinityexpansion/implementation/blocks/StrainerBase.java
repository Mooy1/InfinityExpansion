package io.github.mooy1.infinityexpansion.implementation.blocks;

import io.github.mooy1.infinityexpansion.lists.Categories;
import io.github.mooy1.infinityexpansion.lists.Items;
import io.github.mooy1.infinityexpansion.utils.Utils;
import io.github.mooy1.infinitylib.math.RandomUtils;
import io.github.mooy1.infinitylib.objects.AbstractContainer;
import io.github.mooy1.infinitylib.player.MessageUtils;
import io.github.mooy1.infinitylib.presets.MenuPreset;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Generates items slowly using up strainers, must be waterlogged
 *
 * @author Mooy1
 */
public class StrainerBase extends AbstractContainer implements RecipeDisplayItem {
    
    private static final int TIME = 48;

    private static final int STATUS_SLOT = MenuPreset.slot1;
    private static final int[] OUTPUT_SLOTS = Utils.largeOutput;
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
        super(Categories.BASIC_MACHINES, Items.STRAINER_BASE, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                new ItemStack(Material.STICK), new ItemStack(Material.STRING), new ItemStack(Material.STICK),
                new ItemStack(Material.STICK), new ItemStack(Material.STRING), new ItemStack(Material.STICK),
                Items.MAGSTEEL, Items.MAGSTEEL, Items.MAGSTEEL,
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

    public void setupInv(@Nonnull BlockMenuPreset blockMenuPreset) {
        for (int i : MenuPreset.slotChunk1) {
            blockMenuPreset.addItem(i, MenuPreset.borderItemStatus, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : MenuPreset.slotChunk1) {
            blockMenuPreset.addItem(i + 27, MenuPreset.borderItemInput, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : Utils.largeOutputBorder) {
            blockMenuPreset.addItem(i, MenuPreset.borderItemOutput, ChestMenuUtils.getEmptyClickHandler());
        }
        blockMenuPreset.addItem(STATUS_SLOT, MenuPreset.loadingItemRed, ChestMenuUtils.getEmptyClickHandler());
    }

    @Override
    public int[] getTransportSlots(@Nonnull ItemTransportFlow flow) {
        if (flow == ItemTransportFlow.WITHDRAW) {
            return OUTPUT_SLOTS;
        }
        return new int[0];
    }

    @Override
    public int[] getTransportSlots(@Nonnull DirtyChestMenu menu, @Nonnull ItemTransportFlow flow, @Nonnull ItemStack item) {
        if (flow == ItemTransportFlow.WITHDRAW) {
            return OUTPUT_SLOTS;
        }
        
        if (getStrainer(item) > 0) {
            return INPUT_SLOTS;
        }

        return new int[0];
    }

    @Override
    public void tick(@Nonnull Block b, @Nonnull BlockMenu inv) {
        boolean playerWatching = inv.hasViewer();

        //check water
        BlockData blockData = b.getBlockData();
        
        if (blockData instanceof Waterlogged) {
            Waterlogged waterlogged = (Waterlogged) blockData;

            if (!waterlogged.isWaterlogged()) {

                if (playerWatching) {
                    inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.BARRIER, "&cMust be in water!"));
                }

                return;
            }

        } else return;

        //check input

        ItemStack strainer = inv.getItemInSlot(INPUT_SLOTS[0]);
        int speed = getStrainer(strainer);
        
        if (speed == 0) {

            if (playerWatching) {
                inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.BARRIER, "&cInput a Strainer!"));
            }

            return;
        }

        //progress

        if (!RandomUtils.chanceIn(TIME / speed)) {

            if (playerWatching) {
                inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.LIME_STAINED_GLASS_PANE, "&aCollecting..."));
            }

            return;
        }

        //fish

        if (RandomUtils.chanceIn(10000)) {
            fish(inv);
        }
        
        ItemStack output = RandomUtils.randomOutput(OUTPUTS);

        //check fits

        if (!inv.fits(output, OUTPUT_SLOTS)) {

            if (playerWatching) {
                inv.replaceExistingItem(STATUS_SLOT, MenuPreset.notEnoughRoom);
            }

            return;
        }

        //output

        inv.pushItem(output, OUTPUT_SLOTS);

        if (playerWatching) {
            inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.LIME_STAINED_GLASS_PANE, "&aMaterial Collected!"));
        }

        //reduce durability

        if (RandomUtils.chanceIn(strainer.getEnchantmentLevel(Enchantment.DURABILITY) + (3 * strainer.getEnchantmentLevel(Enchantment.MENDING)) + 1)) {
            ItemMeta itemMeta = strainer.getItemMeta();
            Damageable durability = (Damageable) itemMeta;
            
            int current = Objects.requireNonNull(durability).getDamage();

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

    @Override
    public void onNewInstance(@Nonnull BlockMenu menu, @Nonnull Block b) {
        
    }

    /**
     * This method gets the speed of strainer from its id
     *
     * @return speed
     */
    private static int getStrainer(@Nullable ItemStack item) {
        if (item != null) {
            ItemMeta meta = item.getItemMeta();

            if (meta != null) {
                Integer speed = meta.getPersistentDataContainer().get(Strainer.KEY, PersistentDataType.INTEGER);
                if (speed != null) {
                    return speed;
                }
            }
        }
        
        return 0;
    }

    private static final ItemStack potato = new CustomItem(Material.POTATO, "&7:&6Potatofish&7:", "&eLucky");

    /**
     * This method will try to output a lucky potatofish
     *
     * @param inv inventory to output to
     */
    private static void fish(BlockMenu inv) {
        if (inv.fits(potato, OUTPUT_SLOTS)) {
            inv.pushItem(potato, OUTPUT_SLOTS);
            MessageUtils.messagePlayersInInv(inv, ChatColor.YELLOW + "You caught a lucky fish! ... potato?");
        }
    }

    @Nonnull
    @Override
    public List<ItemStack> getDisplayRecipes() {
        List<ItemStack> items = new ArrayList<>();
        
        for (ItemStack output : OUTPUTS) {
            items.add(Items.BASIC_STRAINER);
            items.add(output);
        }
        
        return items;
    }

    @Nonnull
    @Override
    public String getRecipeSectionLabel(@Nonnull Player p) {
        return "&7Collects:";
    }
}
