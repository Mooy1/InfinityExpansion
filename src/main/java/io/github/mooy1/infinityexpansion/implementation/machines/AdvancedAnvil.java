package io.github.mooy1.infinityexpansion.implementation.machines;

import io.github.mooy1.infinityexpansion.lists.Items;
import io.github.mooy1.infinityexpansion.utils.PresetUtils;
import io.github.mooy1.infinityexpansion.utils.RecipeUtils;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import lombok.NonNull;
import io.github.mooy1.infinityexpansion.lists.Categories;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import me.mrCookieSlime.Slimefun.cscorelib2.protection.ProtectableAction;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Combines slimefun items, exceeded vanilla anvil limits
 *
 * @author Mooy1
 */
public class AdvancedAnvil extends SlimefunItem implements EnergyNetComponent {

    public static final int ENERGY = 100_000;

    private static final int[] max_enchant_levels = {
        9, 10, 13, 6
    };

    private static final List<Enchantment> UPGRADEABLE_ENCHANTS = Arrays.asList(
            Enchantment.DAMAGE_ALL,
            Enchantment.PROTECTION_ENVIRONMENTAL,
            Enchantment.DURABILITY,
            Enchantment.LOOT_BONUS_BLOCKS
    );

    private static final int[] INPUT_SLOTS = {
            PresetUtils.slot1, PresetUtils.slot2
    };
    private static final int INPUT_SLOT1 = INPUT_SLOTS[0];
    private static final int INPUT_SLOT2 = INPUT_SLOTS[1];
    private static final int[] OUTPUT_SLOTS = {
            PresetUtils.slot3
    };
    private static final int STATUS_SLOT = PresetUtils.slot2 + 27;
    private static final int[] OTHER_STATUS = {47, 51};
    private static final int[] BACKGROUND = {
            27, 28, 29, 33, 34, 35,
            36, 37, 38, 42, 43, 44,
            45, 46, 52, 53
    };

    public AdvancedAnvil() {
        super(Categories.MAIN, Items.ADVANCED_ANVIL, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                Items.ADAMANTITE, Items.ADAMANTITE, Items.ADAMANTITE,
                Items.MAGNONIUM, new ItemStack(Material.ANVIL), Items.MAGNONIUM,
                Items.MACHINE_CIRCUIT, Items.MACHINE_CORE, Items.MACHINE_CIRCUIT
        });

        new BlockMenuPreset(getId(), Objects.requireNonNull(Items.ADVANCED_ANVIL.getDisplayName())) {
            @Override
            public void init() {
                setupInv(this);
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
                return new int[0];
            }
        };

        registerBlockHandler(getId(), (p, b, stack, reason) -> {
            BlockMenu inv = BlockStorage.getInventory(b);

            if (inv != null) {
                Location l = b.getLocation();
                inv.dropItems(l, INPUT_SLOTS);
                inv.dropItems(l, OUTPUT_SLOTS);
            }

            return true;
        });
    }

    private void setupInv(BlockMenuPreset blockMenuPreset) {
        for (int i : PresetUtils.slotChunk3) {
            blockMenuPreset.addItem(i, PresetUtils.borderItemOutput, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : BACKGROUND) {
            blockMenuPreset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : PresetUtils.slotChunk1) {
            blockMenuPreset.addItem(i, PresetUtils.borderItemInput, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : PresetUtils.slotChunk2) {
            blockMenuPreset.addItem(i, PresetUtils.borderItemInput, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : PresetUtils.slotChunk2) {
            blockMenuPreset.addItem(i + 27, PresetUtils.borderItemStatus, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : OTHER_STATUS) {
            blockMenuPreset.addItem(i, PresetUtils.borderItemStatus, ChestMenuUtils.getEmptyClickHandler());
        }
        blockMenuPreset.addItem(STATUS_SLOT, PresetUtils.loadingItemBarrier,
                ChestMenuUtils.getEmptyClickHandler());
        blockMenuPreset.addItem(STATUS_SLOT, PresetUtils.loadingItemRed,
                ChestMenuUtils.getEmptyClickHandler());
    }

    @Override
    public void preRegister() {
        this.addItemHandler(new BlockTicker() {
            @Override
            public void tick(Block b, SlimefunItem item, me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config data) {
                AdvancedAnvil.this.tick(b);
            }

            public boolean isSynchronized() {
                return false;
            }


        });
    }

    public void tick(Block b) {
        Location l = b.getLocation();
        @Nullable final BlockMenu inv = BlockStorage.getInventory(l);
        if (inv == null) return;
        if (!inv.hasViewer()) return;

        if (getCharge(l) < ENERGY) { //not enough energy
            inv.replaceExistingItem(STATUS_SLOT, PresetUtils.notEnoughEnergy);
            return;
        }

        ItemStack item1 = inv.getItemInSlot(INPUT_SLOT1);
        ItemStack item2 = inv.getItemInSlot(INPUT_SLOT2);

        if (item1 == null || item2 == null || item1.getType() != item2.getType()) {
            inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.BARRIER, "&cInput matching items!"));
            return;
        }

        ItemStack output = getOutput(item1, item2);

        if (output == null) {
            inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.BARRIER, "&cInvalid items"));
            return;
        }

        inv.replaceExistingItem(STATUS_SLOT, RecipeUtils.getDisplayItem(output));
    }

    @Nullable
    private static ItemStack getOutput(@NonNull ItemStack item1, @NonNull ItemStack item2) {
        ItemMeta meta1 = item1.getItemMeta();
        ItemMeta meta2 = item2.getItemMeta();
        if (meta1 == null || meta2 == null || (!meta1.hasEnchants() && !meta2.hasEnchants())) return null;

        ItemStack item = item1.clone();

        Map<Enchantment, Integer> map1 = meta1.getEnchants();
        Map<Enchantment, Integer> map2 = meta1.getEnchants();
        List<Enchantment> enchants1 = new ArrayList<>(map1.keySet());
        List<Enchantment> enchants2 = new ArrayList<>(map2.keySet());

        for (Enchantment enchant1 : enchants1) {
            if (enchants2.contains(enchant1)) {
                int value1 = map1.get(enchant1);
                int value2 = map2.get(enchant1);

                if (value1 == value2 && UPGRADEABLE_ENCHANTS.contains(enchant1)) { //try upgrade
                    int upgrade = value1 + 1;

                    if (upgrade <= max_enchant_levels[UPGRADEABLE_ENCHANTS.indexOf(enchant1)]) {
                        item.addUnsafeEnchantment(enchant1, upgrade);
                    }

                } else if (value2 > value1) { //take highest

                    item.addUnsafeEnchantment(enchant1, value2);
                }
            }
        }

        for (Enchantment enchant2 : enchants2) {
            if (!enchants1.contains(enchant2)) {
                item.addUnsafeEnchantment(enchant2, map2.get(enchant2));
            }
        }

        return item;
    }

    @Nonnull
    @Override
    public EnergyNetComponentType getEnergyComponentType() {
        return EnergyNetComponentType.CONSUMER;
    }

    @Override
    public int getCapacity() {
        return ENERGY;
    }
}
