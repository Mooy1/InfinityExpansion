package me.mooy1.infinityexpansion.implementation.items;

import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mooy1.infinityexpansion.lists.Items;
import me.mooy1.infinityexpansion.lists.Categories;
import me.mooy1.infinityexpansion.utils.ItemStackUtils;
import me.mooy1.infinityexpansion.utils.MathUtils;
import me.mooy1.infinityexpansion.utils.MessageUtils;
import me.mooy1.infinityexpansion.utils.PresetUtils;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import me.mrCookieSlime.Slimefun.cscorelib2.protection.ProtectableAction;
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
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class StrainerBase extends SlimefunItem implements RecipeDisplayItem {

    public static final int BASIC_SPEED = 1;
    public static final int ADVANCED_SPEED = 4;
    public static final int REINFORCED_SPEED = 16;
    private static final int TIME = 128;

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
        super(Categories.BASIC_MACHINES, Items.STRAINER_BASE, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {

        });

        new BlockMenuPreset(getId(), Objects.requireNonNull(Items.STRAINER_BASE.getDisplayName())) {
            @Override
            public void init() {
                setupInv(this);
            }

            @Override
            public void newInstance(@Nonnull BlockMenu menu, @Nonnull Block b) {
                if (getProgress(b) == null) {
                    setProgress(b, 0);
                }
            }

            @Override
            public boolean canOpen(@Nonnull Block block, @Nonnull Player player) {
                return (player.hasPermission("slimefun.inventory.bypass")
                        || SlimefunPlugin.getProtectionManager().hasPermission(
                        player, block.getLocation(), ProtectableAction.ACCESS_INVENTORIES));
            }

            @Override
            public int[] getSlotsAccessedByItemTransport(ItemTransportFlow itemTransportFlow) {
                return new int[0];
            }

            @Override
            public int[] getSlotsAccessedByItemTransport(DirtyChestMenu menu, ItemTransportFlow flow, ItemStack item) {
                if (flow == ItemTransportFlow.INSERT) {
                    return INPUT_SLOTS;
                } else if (flow == ItemTransportFlow.WITHDRAW) {
                    return OUTPUT_SLOTS;
                } else {
                    return new int[0];
                }
            }
        };

        registerBlockHandler(getId(), (p, b, stack, reason) -> {
            BlockMenu inv = BlockStorage.getInventory(b);

            if (inv != null) {
                Location l = b.getLocation();
                inv.dropItems(l, OUTPUT_SLOTS);
                inv.dropItems(l, INPUT_SLOTS);
            }

            setProgress(b, 0);

            return true;
        });
    }

    private void setupInv(BlockMenuPreset blockMenuPreset) {
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
    public void preRegister() {
        this.addItemHandler(new BlockTicker() {
            public void tick(Block b, SlimefunItem sf, Config data) {
                StrainerBase.this.tick(b);
            }

            public boolean isSynchronized() {
                return true;
            }
        });
    }

    public void tick(Block b) {

        BlockData blockData = b.getBlockData();
        Waterlogged waterLogged = (Waterlogged) blockData;

        Location l = b.getLocation();
        @Nullable final BlockMenu inv = BlockStorage.getInventory(l);
        if (inv == null) return;
        boolean playerWatching = inv.toInventory() != null && !inv.toInventory().getViewers().isEmpty();

        if (!waterLogged.isWaterlogged()) { //wait for water

            if (playerWatching) {
                inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.BARRIER, "&cMust be in water!"));
            }

        } else {

            ItemStack strainer = inv.getItemInSlot(INPUT_SLOTS[0]);
            int speed = getStrainer(ItemStackUtils.getIDFromItem(strainer));

            if (speed == 0) { //wrong input

                if (playerWatching) {
                    inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.BARRIER, "&cInput a Strainer!"));
                }

            } else { //progress

                int time = TIME / speed;
                int progress = Integer.parseInt(getProgress(b));

                if (progress < time) {
                    setProgress(b, progress + 1);

                    if (playerWatching) {
                        inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.LIME_STAINED_GLASS_PANE, "&aCollecting..."));
                    }

                } else {
                    double random = Math.random();
                    if (MathUtils.chanceIn(random, 10000)) {
                        fish(inv);
                    }
                    ItemStack output = OUTPUTS[MathUtils.randomFrom(random, (OUTPUTS.length - 1))].clone();

                    if (inv.fits(output, OUTPUT_SLOTS)) { //output

                        inv.pushItem(output, OUTPUT_SLOTS);

                        if (playerWatching) {
                            inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.LIME_STAINED_GLASS_PANE, "&aMaterial Collected!"));
                        }

                    } else { //full

                        if (playerWatching) {
                            inv.replaceExistingItem(STATUS_SLOT, PresetUtils.notEnoughRoom);
                        }
                    }
                    setProgress(b, 0);

                    if (MathUtils.chanceIn(random, speed)) {
                        ItemMeta itemMeta = strainer.getItemMeta();
                        Damageable durability = (Damageable) itemMeta;

                        assert durability != null;
                        int current = durability.getDamage();

                        if (current + 1 == Material.FISHING_ROD.getMaxDurability()) { //break

                            inv.consumeItem(INPUT_SLOTS[0]);

                        } else { //reduce

                            ((Damageable) itemMeta).setDamage(current + 1);
                            strainer.setItemMeta(itemMeta);
                            inv.replaceExistingItem(INPUT_SLOTS[0], strainer);

                        }
                    }
                }
                //((Waterlogged) blockData).setWaterlogged(false);
                //b.setBlockData(blockData);
            }
        }
    }

    private int getStrainer(String id) {
        if (id.equals("BASIC_STRAINER")) return BASIC_SPEED;
        if (id.equals("ADVANCED_STRAINER")) return ADVANCED_SPEED;
        if (id.equals("REINFORCED_STRAINER")) return REINFORCED_SPEED;
        return 0;
    }

    private void fish(BlockMenu inv) {
        ItemStack potato = Items.POTATO_FISH.clone();
        ItemMeta meta = potato.getItemMeta();
        assert meta != null;
        List<String> lore = meta.getLore();
        assert lore != null;
        lore.clear();
        lore.add(ChatColor.YELLOW + "Lucky...");
        lore.add("");
        meta.setLore(lore);
        potato.setItemMeta(meta);
        if (inv.fits(potato, OUTPUT_SLOTS)) {
            inv.pushItem(potato, OUTPUT_SLOTS);

            MessageUtils.messagePlayersInInv(inv, ChatColor.YELLOW + "You caught a lucky potato! ... fish?");
        }
    }

    private void setProgress(Block b, int progress) {
        BlockStorage.addBlockInfo(b, "progress", String.valueOf(progress));
    }

    private String getProgress(Block b) {
        return getBlockData(b.getLocation());
    }

    private String getBlockData(Location l) {
        return BlockStorage.getLocationInfo(l, "progress");
    }

    @Nonnull
    @Override
    public List<ItemStack> getDisplayRecipes() {
        return new ArrayList<>(Arrays.asList(OUTPUTS));
    }

    @Nonnull
    @Override
    public String getRecipeSectionLabel(@Nonnull Player p) {
        return "&7Collects:";
    }
}
