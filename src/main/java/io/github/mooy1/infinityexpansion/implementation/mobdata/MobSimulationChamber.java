package io.github.mooy1.infinityexpansion.implementation.mobdata;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinityexpansion.implementation.Categories;
import io.github.mooy1.infinityexpansion.implementation.materials.Items;
import io.github.mooy1.infinityexpansion.utils.Util;
import io.github.mooy1.infinitylib.core.PluginUtils;
import io.github.mooy1.infinitylib.items.StackUtils;
import io.github.mooy1.infinitylib.slimefun.abstracts.TickingContainer;
import io.github.mooy1.infinitylib.slimefun.presets.LorePreset;
import io.github.mooy1.infinitylib.slimefun.presets.MenuPreset;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
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
import me.mrCookieSlime.Slimefun.cscorelib2.collections.Pair;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.concurrent.ThreadLocalRandom;

public final class MobSimulationChamber extends TickingContainer implements EnergyNetComponent {
    
    public static final SlimefunItemStack ITEM = new SlimefunItemStack(
            "MOB_SIMULATION_CHAMBER",
            Material.GILDED_BLACKSTONE,
            "&8Mob Simulation Chamber",
            "&7Use mob data cards to activate",
            "",
            LorePreset.energyBuffer(MobSimulationChamber.BUFFER),
            LorePreset.energyPerSecond(MobSimulationChamber.ENERGY)
    );
    
    private static final int INTERVAL = 10 + (int) (10 * InfinityExpansion.getDifficulty());
    
    private static final int CARD_SLOT = MenuPreset.slot1 + 27;
    private static final int STATUS_SLOT = MenuPreset.slot1;
    private static final int[] OUTPUT_SLOTS = Util.largeOutput;
    private static final int XP_Slot = 46;
    public static final int BUFFER = 15000;
    public static final int ENERGY = 150;
    private static final ItemStack NO_CARD = new CustomItem(Material.BARRIER, "&cInput a Mob Data Card!");
    
    public MobSimulationChamber() {
        super(Categories.MOB_SIMULATION, ITEM, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Items.MAGSTEEL_PLATE, Items.MACHINE_PLATE, Items.MAGSTEEL_PLATE,
                Items.MACHINE_CIRCUIT, SlimefunItems.PROGRAMMABLE_ANDROID_BUTCHER, Items.MACHINE_CIRCUIT,
                Items.MAGSTEEL_PLATE, Items.MACHINE_PLATE, Items.MAGSTEEL_PLATE,
        });
    }

    @Override
    protected void onBreak(@Nonnull BlockBreakEvent e, @Nonnull BlockMenu menu, @Nonnull Location l) {

        menu.dropItems(l, OUTPUT_SLOTS);
        menu.dropItems(l, CARD_SLOT);

        e.getPlayer().giveExp(Util.getIntData("xp", l));
        BlockStorage.addBlockInfo(l, "xp", "0");
    }

    @Nonnull
    private static ItemStack makeSimulating(int energy) {
        return new CustomItem(Material.LIME_STAINED_GLASS_PANE, "&aSimulating... (" + Math.round(energy * PluginUtils.TICK_RATIO) + " J/s)");
    }

    @Nonnull
    @Override
    public EnergyNetComponentType getEnergyComponentType() {
        return EnergyNetComponentType.CONSUMER;
    }

    @Override
    public int getCapacity() {
        return BUFFER;
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
        blockMenuPreset.addItem(XP_Slot, MenuPreset.loadingItemRed, ChestMenuUtils.getEmptyClickHandler());
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
        menu.replaceExistingItem(XP_Slot, makeXpItem(0));
        menu.addMenuClickHandler(XP_Slot, (p, slot, item, action) -> {
            int xp = Util.getIntData("xp", l);
            if (xp > 0) {
                p.giveExp(xp);
                p.playSound(l, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                BlockStorage.addBlockInfo(l, "xp", "O");
                menu.replaceExistingItem(XP_Slot, makeXpItem(0));
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

        int energy = card.tier.energy + ENERGY;

        if (getCharge(b.getLocation()) < energy) {
            if (inv.hasViewer()) {
                inv.replaceExistingItem(STATUS_SLOT, MenuPreset.notEnoughEnergy);
            }
            return;
        }
        
        removeCharge(b.getLocation(), energy);
        
        int xp = Util.getIntData("xp", b.getLocation());
        
        if (inv.hasViewer()) {
            inv.replaceExistingItem(STATUS_SLOT, makeSimulating(energy));
            inv.replaceExistingItem(XP_Slot, makeXpItem(xp));
        }

        if (PluginUtils.getCurrentTick() % INTERVAL != 0) return;

        if (ThreadLocalRandom.current().nextBoolean()) {
            BlockStorage.addBlockInfo(b.getLocation(), "xp", String.valueOf(xp + card.tier.xp));
        }

        for (Pair<ItemStack, Integer> entry : card.drops) {
            if (ThreadLocalRandom.current().nextInt(entry.getSecondValue()) == 0) {
                ItemStack output = entry.getFirstValue();
                if (inv.fits(output, OUTPUT_SLOTS)) {
                    inv.pushItem(output.clone(), OUTPUT_SLOTS);
                } else if (inv.hasViewer()) {
                    inv.replaceExistingItem(STATUS_SLOT, MenuPreset.notEnoughRoom);
                }
            }
        }
    }

}
