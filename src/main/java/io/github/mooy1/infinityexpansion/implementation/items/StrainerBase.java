package io.github.mooy1.infinityexpansion.implementation.items;

import io.github.mooy1.infinityexpansion.implementation.template.Machine;
import io.github.mooy1.infinityexpansion.lists.Categories;
import io.github.mooy1.infinityexpansion.lists.Items;
import io.github.mooy1.infinityexpansion.utils.MathUtils;
import io.github.mooy1.infinityexpansion.utils.MessageUtils;
import io.github.mooy1.infinityexpansion.utils.PresetUtils;
import io.github.mooy1.infinityexpansion.utils.StackUtils;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
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
public class StrainerBase extends Machine implements RecipeDisplayItem {

    public static final int BASIC_SPEED = 1;
    public static final int ADVANCED_SPEED = 4;
    public static final int REINFORCED_SPEED = 12;
    private static final int TIME = 48;

    private static final int STATUS_SLOT = PresetUtils.slot1;
    private static final int[] OUTPUT_SLOTS = PresetUtils.largeOutput;

    private static final int[] INPUT_SLOTS = {
            PresetUtils.slot1 + 27
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
        for (int i : PresetUtils.slotChunk1) {
            blockMenuPreset.addItem(i, PresetUtils.borderItemStatus, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : PresetUtils.slotChunk1) {
            blockMenuPreset.addItem(i + 27, PresetUtils.borderItemInput, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : PresetUtils.largeOutputBorder) {
            blockMenuPreset.addItem(i, PresetUtils.borderItemOutput, ChestMenuUtils.getEmptyClickHandler());
        }
        blockMenuPreset.addItem(STATUS_SLOT, PresetUtils.loadingItemRed, ChestMenuUtils.getEmptyClickHandler());
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
        String id = StackUtils.getIDFromItem(item);

        if (id != null && id.endsWith("_STRAINER")) {
            return INPUT_SLOTS;
        }

        return new int[0];
    }

    @Override
    public void tick(@Nonnull Block b, @Nonnull Location l, @Nonnull BlockMenu inv) {
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
        SlimefunItem slimefunItem = SlimefunItem.getByItem(strainer);

        if (slimefunItem == null) {

            if (playerWatching) {
                inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.BARRIER, "&cInput a Strainer!"));
            }

            return;
        }

        int speed = getStrainer(slimefunItem.getId());

        if (speed == 0) {

            if (playerWatching) {
                inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.BARRIER, "&cInput a Strainer!"));
            }

            return;
        }

        //progress

        if (!MathUtils.chanceIn(TIME / speed)) {

            if (playerWatching) {
                inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.LIME_STAINED_GLASS_PANE, "&aCollecting..."));
            }

            return;
        }

        //fish

        if (MathUtils.chanceIn(10000)) {
            fish(inv);
        }


        ItemStack output = MathUtils.randomOutput(OUTPUTS);

        //check fits

        if (!inv.fits(output, OUTPUT_SLOTS)) {

            if (playerWatching) {
                inv.replaceExistingItem(STATUS_SLOT, PresetUtils.notEnoughRoom);
            }

            return;
        }

        //output

        inv.pushItem(output, OUTPUT_SLOTS);

        if (playerWatching) {
            inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.LIME_STAINED_GLASS_PANE, "&aMaterial Collected!"));
        }

        //reduce durability

        if (MathUtils.chanceIn(speed == 1 ? 2 : speed == 4 ? 4 : 8)) {
            ItemMeta itemMeta = strainer.getItemMeta();
            Damageable durability = (Damageable) itemMeta;

            assert durability != null;
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

    /**
     * This method gets the speed of strainer from its id
     *
     * @param id id of strainer
     * @return speed
     */
    private static int getStrainer(@Nonnull String id) {
        if (id.equals("BASIC_STRAINER")) return BASIC_SPEED;
        if (id.equals("ADVANCED_STRAINER")) return ADVANCED_SPEED;
        if (id.equals("REINFORCED_STRAINER")) return REINFORCED_SPEED;
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
