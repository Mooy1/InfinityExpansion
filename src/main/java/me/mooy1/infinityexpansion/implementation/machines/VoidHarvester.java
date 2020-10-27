package me.mooy1.infinityexpansion.implementation.machines;

import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.mooy1.infinityexpansion.lists.Categories;
import me.mooy1.infinityexpansion.lists.Items;
import me.mooy1.infinityexpansion.lists.InfinityRecipes;
import me.mooy1.infinityexpansion.utils.PresetUtils;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
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
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * harvests void bits from... the void
 *
 * @author Mooy1
 */
public class VoidHarvester extends SlimefunItem implements EnergyNetComponent, RecipeDisplayItem {

    public static final int BASIC_ENERGY = 900;
    public static final int BASIC_SPEED = 1;
    public static final int INFINITY_ENERGY = 90000;
    public static final int INFINITY_SPEED = 10;

    private static final int[] OUTPUT_SLOTS = {
        13
    };
    private static final int STATUS_SLOT = 4;
    private static final int TIME = 640;

    private final Type type;

    public VoidHarvester(Type type) {
        super(type.getCategory(), type.getItem(), type.getRecipeType(), type.getRecipe());
        this.type = type;

        new BlockMenuPreset(getId(), Objects.requireNonNull(type.getItem().getDisplayName())) {
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
            public int[] getSlotsAccessedByItemTransport(ItemTransportFlow flow) {
                if (flow == ItemTransportFlow.WITHDRAW) {
                    return OUTPUT_SLOTS;
                }

                return new int[0];
            }

            @Override
            public int[] getSlotsAccessedByItemTransport(DirtyChestMenu menu, ItemTransportFlow flow, ItemStack item) {
                if (flow == ItemTransportFlow.WITHDRAW) {
                    return OUTPUT_SLOTS;
                }

                return new int[0];
            }
        };

        registerBlockHandler(getId(), (p, b, stack, reason) -> {
            BlockMenu inv = BlockStorage.getInventory(b);

            if (inv != null) {
                inv.dropItems(b.getLocation(), OUTPUT_SLOTS);
            }

            return true;
        });
    }

    private void setupInv(BlockMenuPreset  blockMenuPreset) {
        for (int i = 0; i < 13; i++) {
            blockMenuPreset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i = 14; i < 18; i++) {
            blockMenuPreset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }

        blockMenuPreset.addItem(STATUS_SLOT, PresetUtils.loadingItemRed,
                ChestMenuUtils.getEmptyClickHandler());
    }

    @Override
    public void preRegister() {
        this.addItemHandler(new BlockTicker() {
            public void tick(Block b, SlimefunItem sf, Config data) { VoidHarvester.this.tick(b); }

            public boolean isSynchronized() { return false; }
        });
    }

    public void tick(Block b) {

        @Nullable final BlockMenu inv = BlockStorage.getInventory(b.getLocation());
        if (inv == null) return;


        boolean playerWatching = inv.toInventory() != null && !inv.toInventory().getViewers().isEmpty();

        int energy = type.getEnergy();

        if (getCharge(b.getLocation()) < energy) { //not enough energy

            if (playerWatching) {
                inv.replaceExistingItem(STATUS_SLOT, PresetUtils.notEnoughEnergy);
            }

        } else {

            int progress = Integer.parseInt(getProgress(b));

            int speed = type.getSpeed();

            if (progress >= TIME) { //reached full progress

                ItemStack output = Items.VOID_BIT.clone();

                if (inv.fits(output, OUTPUT_SLOTS)) {

                    inv.pushItem(output, OUTPUT_SLOTS);

                    setProgress(b, speed);

                    removeCharge(b.getLocation(), energy);

                    if (playerWatching) { //done
                        inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.LIME_STAINED_GLASS_PANE,
                                "&aHarvesting complete! - 100%",
                                "&7(" + progress + "/" + TIME + ")"
                        ));
                    }

                } else { //output slots full

                    if (playerWatching) {
                        inv.replaceExistingItem(STATUS_SLOT, PresetUtils.notEnoughRoom);
                    }
                }

            } else { //increase progress

                setProgress(b, progress+speed);
                removeCharge(b.getLocation(), energy);

                if (playerWatching) { //update status
                    inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.LIME_STAINED_GLASS_PANE,
                            "&aHarvesting - " + progress * 100 / TIME + "%",
                            "&7(" + progress + "/" + TIME + ")"
                    ));
                }
            }
        }
    }

    private void setProgress(Block b, int progress) {
        BlockStorage.addBlockInfo(b, "progress", String.valueOf(progress));
    }

    private String getProgress(Block b) {
        return BlockStorage.getLocationInfo(b.getLocation(), "progress");
    }

    @Nonnull
    @Override
    public EnergyNetComponentType getEnergyComponentType() {
        return EnergyNetComponentType.CONSUMER;
    }

    @Override
    public int getCapacity() {
        return type.getEnergy();
    }

    @Nonnull
    @Override
    public List<ItemStack> getDisplayRecipes() {
        final List<ItemStack> items = new ArrayList<>();

        items.add(Items.VOID_BIT);

        return items;
    }

    @Nonnull
    @Override
    public String getRecipeSectionLabel(@Nonnull Player p) {
        return "&7Harvests:";
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum Type {
        BASIC(Categories.ADVANCED_MACHINES, BASIC_ENERGY, BASIC_SPEED, Items.VOID_HARVESTER, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Items.MACHINE_PLATE, Items.MACHINE_PLATE, Items.MACHINE_PLATE,
                Items.MAGNONIUM, SlimefunItems.GEO_MINER, Items.MAGNONIUM,
                Items.MACHINE_CIRCUIT, Items.MACHINE_CORE, Items.MACHINE_CIRCUIT
        }),
        INFINITY(Categories.INFINITY_CHEAT, INFINITY_ENERGY, INFINITY_SPEED, Items.INFINITE_VOID_HARVESTER, RecipeType.ENHANCED_CRAFTING_TABLE, InfinityRecipes.getRecipe(Items.INFINITE_VOID_HARVESTER));

        @Nonnull
        private final Category category;
        private final int energy;
        private final int speed;
        private final SlimefunItemStack item;
        private final RecipeType recipeType;
        private final ItemStack[] recipe;
    }
}

