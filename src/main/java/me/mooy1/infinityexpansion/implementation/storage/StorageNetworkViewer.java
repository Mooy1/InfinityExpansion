package me.mooy1.infinityexpansion.implementation.storage;

import io.github.thebusybiscuit.slimefun4.core.networks.cargo.CargoNet;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import me.mooy1.infinityexpansion.lists.Items;
import me.mooy1.infinityexpansion.lists.Categories;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
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
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

public class StorageNetworkViewer extends SlimefunItem {

    private static final int STATUS_SLOT = 4;

    public StorageNetworkViewer() {
        super(Categories.STORAGE_TRANSPORT, Items.STORAGE_NETWORK_VIEWER, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                Items.TITANIUM, Items.MACHINE_CIRCUIT, Items.TITANIUM,
                Items.MACHINE_CIRCUIT, Items.MACHINE_CORE, Items.MACHINE_CIRCUIT,
                Items.TITANIUM, Items.MACHINE_CIRCUIT, Items.TITANIUM,
        });

        new BlockMenuPreset(getId(), Objects.requireNonNull(Items.STORAGE_NETWORK_VIEWER.getDisplayName())) {
            @Override
            public void init() {
                setupInv(this);
            }

            @Override
            public void newInstance(@Nonnull BlockMenu menu, @Nonnull Block b) {

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
    }

    private void setupInv(BlockMenuPreset blockMenuPreset) {

    }

    @Override
    public void preRegister() {
        this.addItemHandler(new BlockTicker() {
            public void tick(Block b, SlimefunItem sf, Config data) {
                StorageNetworkViewer.this.tick(b);
            }

            public boolean isSynchronized() {
                return true;
            }
        });
    }

    public void tick(Block b) {
        Location l = b.getLocation();
        @Nullable final BlockMenu inv = BlockStorage.getInventory(l);
        if (inv == null) return;

        if (!inv.toInventory().getViewers().isEmpty()) {
            return;
        }

        Optional<CargoNet> cargoNet = SlimefunPlugin.getNetworkManager().getNetworkFromLocation(l, CargoNet.class);

        if (!cargoNet.isPresent()) {
            inv.replaceExistingItem(STATUS_SLOT, new CustomItem(
                    Material.RED_STAINED_GLASS_PANE,
                    "&cConnect to a cargo network!"
            ));

        }


    }
}