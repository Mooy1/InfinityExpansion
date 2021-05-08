package io.github.mooy1.infinityexpansion.implementation.mobdata;

import javax.annotation.Nonnull;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinityexpansion.utils.Util;
import io.github.mooy1.infinitylib.items.StackUtils;
import io.github.mooy1.infinitylib.slimefun.abstracts.TickingContainer;
import io.github.mooy1.infinitylib.slimefun.presets.LorePreset;
import io.github.mooy1.infinitylib.slimefun.presets.MenuPreset;
import io.github.mooy1.infinitylib.slimefun.utils.TickerUtils;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;

public final class MobSimulationChamber extends TickingContainer implements EnergyNetComponent {
    
    private static final ItemStack NO_CARD = new CustomItem(Material.BARRIER, "&cInput a Mob Data Card!");
    private static final int CARD_SLOT = MenuPreset.slot1 + 27;
    private static final int STATUS_SLOT = MenuPreset.slot1;
    private static final int[] OUTPUT_SLOTS = Util.largeOutput;
    private static final int XP_SLOT = 46;
    
    private final int energy;
    private final int interval;
    
    public MobSimulationChamber(Category category, SlimefunItemStack item, RecipeType type, ItemStack[] recipe, int energy, int interval) {
        super(category, item, type, recipe);
        this.energy = energy;
        this.interval = interval;
    }

    @Override
    protected void onBreak(@Nonnull BlockBreakEvent e, @Nonnull BlockMenu menu, @Nonnull Location l) {

        menu.dropItems(l, OUTPUT_SLOTS);
        menu.dropItems(l, CARD_SLOT);

        e.getPlayer().giveExp(Util.getIntData("xp", l));
        BlockStorage.addBlockInfo(l, "xp", "0");
    }

    @Nonnull
    @Override
    public EnergyNetComponentType getEnergyComponentType() {
        return EnergyNetComponentType.CONSUMER;
    }

    @Override
    public int getCapacity() {
        return this.energy + Math.max(MobDataTier.BOSS.energy, this.energy * 9);
    }

    @Override
    protected void setupMenu(@Nonnull BlockMenuPreset blockMenuPreset) {
        for (int i : Util.largeOutputBorder) {
            blockMenuPreset.addItem(i, MenuPreset.borderItemOutput, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : MenuPreset.slotChunk1) {
            blockMenuPreset.addItem(i, MenuPreset.borderItemStatus, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : MenuPreset.slotChunk1) {
            blockMenuPreset.addItem(i + 27, MenuPreset.borderItemInput, ChestMenuUtils.getEmptyClickHandler());
        }
        blockMenuPreset.addItem(STATUS_SLOT, MenuPreset.loadingItemRed, ChestMenuUtils.getEmptyClickHandler());
        blockMenuPreset.addItem(XP_SLOT, MenuPreset.loadingItemRed, ChestMenuUtils.getEmptyClickHandler());
    }

    @Nonnull
    @Override
    protected int[] getTransportSlots(@Nonnull DirtyChestMenu menu, @Nonnull ItemTransportFlow flow, ItemStack item) {
        if (flow == ItemTransportFlow.WITHDRAW) {
            return OUTPUT_SLOTS;
        }
        return new int[0];
    }

    @Override
    public void onNewInstance(@Nonnull BlockMenu menu, @Nonnull Block b) {
        Location l = b.getLocation();
        if (BlockStorage.getLocationInfo(l, "xp") == null) {
            BlockStorage.addBlockInfo(l, "xp", "O");
        }
        menu.replaceExistingItem(XP_SLOT, makeXpItem(0));
        menu.addMenuClickHandler(XP_SLOT, (p, slot, item, action) -> {
            int xp = Util.getIntData("xp", l);
            if (xp > 0) {
                p.giveExp(xp);
                p.playSound(l, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                BlockStorage.addBlockInfo(l, "xp", "O");
                menu.replaceExistingItem(XP_SLOT, makeXpItem(0));
            }
            return false;
        });
    }
    
    private static ItemStack makeXpItem(int stored) {
        return new CustomItem(Material.LIME_STAINED_GLASS_PANE, "&aStored xp: " + stored, "", "&a> Click to claim");
    }

    @Override
    protected void tick(@Nonnull BlockMenu inv, @Nonnull Block b, @Nonnull Config data) {
        ItemStack input = inv.getItemInSlot(CARD_SLOT);
        
        if (input == null) {
            return;
        }
        
        MobDataCard card = MobDataCard.CARDS.get(StackUtils.getID(input));

        if (card == null) {
            if (inv.hasViewer()) {
                inv.replaceExistingItem(STATUS_SLOT, NO_CARD);
            }
            return;
        }

        int energy = card.tier.energy + this.energy;

        if (getCharge(b.getLocation()) < energy) {
            if (inv.hasViewer()) {
                inv.replaceExistingItem(STATUS_SLOT, MenuPreset.notEnoughEnergy);
            }
            return;
        }
        
        removeCharge(b.getLocation(), energy);
        
        int xp = Util.getIntData("xp", b.getLocation());
        
        if (inv.hasViewer()) {
            inv.replaceExistingItem(STATUS_SLOT, new CustomItem(Material.LIME_STAINED_GLASS_PANE,
                    "&aSimulating... (" + LorePreset.format(energy * TickerUtils.TPS) + " J/s)")
            );
            inv.replaceExistingItem(XP_SLOT, makeXpItem(xp));
        }

        if (InfinityExpansion.inst().getGlobalTick() % this.interval != 0) return;

        BlockStorage.addBlockInfo(b.getLocation(), "xp", String.valueOf(xp + card.tier.xp));

        ItemStack item = card.drops.getRandom();
        if (inv.fits(item, OUTPUT_SLOTS)) {
            inv.pushItem(item.clone(), OUTPUT_SLOTS);
        } else if (inv.hasViewer()) {
            inv.replaceExistingItem(STATUS_SLOT, MenuPreset.notEnoughRoom);
        }
    }

}
