package me.mooy1.infinityexpansion.machines;

import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetProvider;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNet;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mooy1.infinityexpansion.setup.Categories;
import me.mooy1.infinityexpansion.Items;
import me.mooy1.infinityexpansion.setup.InfinityRecipes;
import me.mooy1.infinityexpansion.setup.RecipeTypes;
import me.mooy1.infinityexpansion.utils.IDUtils;
import me.mooy1.infinityexpansion.utils.PresetUtils;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import me.mrCookieSlime.Slimefun.cscorelib2.protection.ProtectableAction;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


public class InfinityReactor extends SlimefunItem implements EnergyNetProvider, RecipeDisplayItem {

    public static final int ENERGY = 600_000;
    public static final int STORAGE = 120_000_000;
    public static final int INFINITY_INTERVAL = 144000;
    public static final int VOID_INTERVAL = 24000;
    public static final int[] INPUT_SLOTS = {
            PresetUtils.slot1, PresetUtils.slot3
    };
    public static final int STATUS_SLOT = PresetUtils.slot2;

    public InfinityReactor() {
        super(Categories.INFINITY_MACHINES,
                Items.INFINITY_REACTOR,
                RecipeTypes.INFINITY_WORKBENCH,
                InfinityRecipes.REACTOR);

        new BlockMenuPreset(getId(), Objects.requireNonNull(Items.INFINITY_REACTOR.getDisplayName())) {
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
                    return new int[0];
                } else {
                    return new int[0];
                }
            }
        };

        registerBlockHandler(getId(), (p, b, stack, reason) -> {
            BlockMenu inv = BlockStorage.getInventory(b);

            if (inv != null) {
                Location l = b.getLocation();
                inv.dropItems(l, INPUT_SLOTS);
            }

            if (BlockStorage.getLocationInfo(b.getLocation(), "stand") != null) {
                Objects.requireNonNull(Bukkit.getEntity(UUID.fromString(BlockStorage.getLocationInfo(b.getLocation(), "stand")))).remove();
            }

            return true;
        });
    }

    private void setupInv(BlockMenuPreset blockMenuPreset) {
        for (int i : PresetUtils.slotChunk2) {
            blockMenuPreset.addItem(i, PresetUtils.borderItemStatus, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : PresetUtils.slotChunk3) {
            blockMenuPreset.addItem(i, new CustomItem(
                    Material.BLACK_STAINED_GLASS_PANE, "&8Void Ingot Input"), ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : PresetUtils.slotChunk1) {
            blockMenuPreset.addItem(i, new CustomItem(
                    Material.WHITE_STAINED_GLASS_PANE, "&fInfinity Ingot Input"), ChestMenuUtils.getEmptyClickHandler());
        }
        blockMenuPreset.addItem(STATUS_SLOT, PresetUtils.loadingItemRed, ChestMenuUtils.getEmptyClickHandler());
    }

    @Override
    public void preRegister() {
        this.addItemHandler(new BlockTicker() {
            public void tick(Block b, SlimefunItem sf, Config data) {
                InfinityReactor.this.tick(b);
            }

            public boolean isSynchronized() {
                return true;
            }
        });
    }

    public void tick(Block b) {
        Location l = b.getLocation();

        if (!SlimefunPlugin.getNetworkManager().getNetworkFromLocation(l, EnergyNet.class).isPresent()) {
            @Nullable final BlockMenu inv = BlockStorage.getInventory(l);
            if (inv == null) return;

            if (!inv.toInventory().getViewers().isEmpty()) {
                inv.replaceExistingItem(STATUS_SLOT, PresetUtils.connectToEnergyNet);
            }
        }
    }

    @Override
    public int getGeneratedOutput(@Nonnull Location l, @Nonnull Config config) {
        @Nullable final BlockMenu inv = BlockStorage.getInventory(l);
        if (inv == null) return 0;
        Block b = l.getBlock();

        boolean playerWatching = inv.toInventory() != null && !inv.toInventory().getViewers().isEmpty();

        int progress = Integer.parseInt(getProgress(b));

        if (progress == 0) { //need infinity + void

            if (!IDUtils.getIDFromItem(inv.getItemInSlot(INPUT_SLOTS[0])).equals("INFINITE_INGOT")) { //wrong input

                if (playerWatching) {
                    inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.RED_STAINED_GLASS_PANE, "&cInput more &fInfinity Ingots"));
                }
                return 0;

            } else if (!IDUtils.getIDFromItem(inv.getItemInSlot(INPUT_SLOTS[1])).equals("VOID_INGOT")) { //wrong input

                if (playerWatching) {
                    inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.RED_STAINED_GLASS_PANE, "&cInput more &8Void Ingots"));
                }
                return 0;

            } else { //correct input

                if (playerWatching) {
                    inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.LIME_STAINED_GLASS_PANE,
                                    "&aStarting Generation",
                                    "&aTime until infinity ingot needed: " + INFINITY_INTERVAL,
                                    "&aTime until void ingot needed: " + VOID_INTERVAL
                            )
                    );
                }
                inv.consumeItem(INPUT_SLOTS[0]);
                inv.consumeItem(INPUT_SLOTS[1]);
                setProgress(b, 1);
                return ENERGY;

            }

        } else if (progress == INFINITY_INTERVAL) { //done

            if (playerWatching) {
                inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.LIME_STAINED_GLASS_PANE, "&aFinished Generation"));
            }
            setProgress(b, 0);
            return ENERGY;

        } else if (Math.floorMod(progress, VOID_INTERVAL) == 0) { //need void

            if (!IDUtils.getIDFromItem(inv.getItemInSlot(INPUT_SLOTS[1])).equals("VOID_INGOT")) { //wrong input

                if (playerWatching) {
                    inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.RED_STAINED_GLASS_PANE, "&cInput more &8Void Ingots"));
                }
                return 0;

            } else { //right input

                if (playerWatching) {
                    inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.LIME_STAINED_GLASS_PANE,
                                    "&aGenerating...",
                                    "&aTime until infinity ingot needed: " + (INFINITY_INTERVAL - progress),
                                    "&aTime until void ingot needed: " + (VOID_INTERVAL - Math.floorMod(progress, VOID_INTERVAL))
                            )
                    );
                }
                setProgress(b, progress + 1);
                inv.consumeItem(INPUT_SLOTS[1]);
                return ENERGY;
            }

        } else { //generate

            if (playerWatching) {
                inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.LIME_STAINED_GLASS_PANE,
                                "&aGenerating...",
                                "&aTime until infinity ingot needed: " + (INFINITY_INTERVAL - progress),
                                "&aTime until void ingot needed: " + (VOID_INTERVAL - Math.floorMod(progress, VOID_INTERVAL))
                        )
                );
            }
            setProgress(b, progress + 1);
            return ENERGY;
        }
    }

    @Override
    public int getCapacity() {
        return STORAGE;
    }

    @Nonnull
    @Override
    public EnergyNetComponentType getEnergyComponentType() {
        return EnergyNetComponentType.GENERATOR;
    }

    @Nonnull
    @Override
    public List<ItemStack> getDisplayRecipes() {
        final List<ItemStack> items = new ArrayList<>();

        ItemStack item = Items.INFINITE_INGOT.clone();
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        List<String> lore = meta.getLore();
        assert lore != null;
        lore.add("");
        lore.add(ChatColor.GREEN + "Lasts for 1 day");
        meta.setLore(lore);
        item.setItemMeta(meta);

        items.add(item);

        items.add(null);

        item = Items.VOID_INGOT.clone();
        meta = item.getItemMeta();
        assert meta != null;
        lore = meta.getLore();
        assert lore != null;
        lore.add("");
        lore.add(ChatColor.GREEN + "Lasts for 3 hours");
        meta.setLore(lore);
        item.setItemMeta(meta);

        items.add(item);

        return items;
    }

    private void setProgress(Block b, int progress) {
        setBlockData(b, "progress", String.valueOf(progress));
    }

    private String getProgress(Block b) {
        return getBlockData(b.getLocation(), "progress");
    }

    private void setBlockData(Block b, String key, String data) {
        BlockStorage.addBlockInfo(b, key, data);
    }

    private String getBlockData(Location l, String key) {
        return BlockStorage.getLocationInfo(l, key);
    }
}
