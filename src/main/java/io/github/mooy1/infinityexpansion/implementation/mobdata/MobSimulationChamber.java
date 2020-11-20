package io.github.mooy1.infinityexpansion.implementation.mobdata;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinityexpansion.implementation.template.Machine;
import io.github.mooy1.infinityexpansion.lists.Categories;
import io.github.mooy1.infinityexpansion.lists.Items;
import io.github.mooy1.infinityexpansion.utils.LoreUtils;
import io.github.mooy1.infinityexpansion.utils.MathUtils;
import io.github.mooy1.infinityexpansion.utils.PresetUtils;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

public class MobSimulationChamber extends Machine implements EnergyNetComponent {

    private static final int CARD_SLOT = PresetUtils.slot1 + 27;
    private static final int STATUS_SLOT = PresetUtils.slot1;
    private static final int[] OUTPUTS_SLOTS = PresetUtils.largeOutput;
    private static final ItemStack NO_CARD = new CustomItem(Material.BARRIER, "&cInput a Mob Data Card!");
    private static final int XP_Slot = 46;
    
    public static final int BUFFER = 10000;
    public static final int ENERGY = 120;
    
    public MobSimulationChamber() {
        super(Categories.MOB_SIMULATION, Items.MOB_SIMULATION_CHAMBER, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Items.MAGSTEEL_PLATE, Items.MAGSTEEL_PLATE, Items.MAGSTEEL_PLATE,
                Items.MACHINE_CIRCUIT, SlimefunItems.PROGRAMMABLE_ANDROID_BUTCHER, Items.MACHINE_CIRCUIT,
                Items.MAGSTEEL_PLATE, Items.MAGSTEEL_PLATE, Items.MAGSTEEL_PLATE,
        });

        registerBlockHandler(getId(), (p, b, stack, reason) -> {
            BlockMenu inv = BlockStorage.getInventory(b);
            Location l = b.getLocation();
            
            if (inv != null) {
                inv.dropItems(l, OUTPUTS_SLOTS);
                inv.dropItems(l, CARD_SLOT);
            }
            
            p.giveExp(getXP(l));
            setXp(l, 0);

            return true;
        });
    }

    @Override
    public void tick(@Nonnull Block b, @Nonnull Location l, @Nonnull BlockMenu inv) {
        MobDataCard.Type card = getCard(inv.getItemInSlot(CARD_SLOT));
        
        if (card == null || card.getEnergy() == 0) {
            if (inv.hasViewer()) {
                inv.replaceExistingItem(STATUS_SLOT, NO_CARD);
            }
            return;
        }
        
        if (getCharge(l) < card.getEnergy()) {
            if (inv.hasViewer()) {
                inv.replaceExistingItem(STATUS_SLOT, PresetUtils.notEnoughEnergy);
            }
            return;
        }

        if (inv.hasViewer()) {
            inv.replaceExistingItem(STATUS_SLOT, makeSimulating(card.getEnergy()));
            inv.replaceExistingItem(XP_Slot, makeXpItem(getXP(l)));
        }

        if (!InfinityExpansion.progressEvery(12)) return;
        
        int xp = getXP(l) + card.getXp();
        setXp(l, xp);

        for (Map.Entry<Integer, ItemStack> entry : card.getDrops().entrySet()) {
            if (MathUtils.chanceIn(entry.getKey())) {
                ItemStack output = entry.getValue().clone();
                if (inv.fits(output, OUTPUTS_SLOTS)) {
                    inv.pushItem(output, OUTPUTS_SLOTS);
                } else {
                    if (inv.hasViewer()) {
                        inv.replaceExistingItem(STATUS_SLOT, PresetUtils.notEnoughRoom);
                    }
                    return;
                }
            }
        }
        
        removeCharge(l, card.getEnergy());
    }
    
    @Nonnull
    private ItemStack makeSimulating(int card) {
        return new CustomItem(Material.LIME_STAINED_GLASS_PANE, "&aSimulating... (" + ((ENERGY + card) * LoreUtils.SERVER_TICK_RATIO) + " J/s)");
    }

    @Override
    public void setupInv(@Nonnull BlockMenuPreset blockMenuPreset) {
        for (int i : PresetUtils.largeOutputBorder) {
            blockMenuPreset.addItem(i, PresetUtils.borderItemOutput, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : PresetUtils.slotChunk1) {
            blockMenuPreset.addItem(i, PresetUtils.borderItemStatus, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : PresetUtils.slotChunk1) {
            blockMenuPreset.addItem(i + 27, PresetUtils.borderItemInput, ChestMenuUtils.getEmptyClickHandler());
        }
        blockMenuPreset.addItem(STATUS_SLOT, PresetUtils.loadingItemRed, ChestMenuUtils.getEmptyClickHandler());
        blockMenuPreset.addItem(XP_Slot, PresetUtils.loadingItemRed, ChestMenuUtils.getEmptyClickHandler());
    }

    @Override
    public int[] getTransportSlots(@Nonnull ItemTransportFlow flow) {
        if (flow == ItemTransportFlow.WITHDRAW) {
            return OUTPUTS_SLOTS;
        }
        return new int[0];
    }
    
    @Nullable
    private MobDataCard.Type getCard(ItemStack item) {
        SlimefunItem sfItem = SlimefunItem.getByItem(item);
        
        if (!(sfItem instanceof MobDataCard)) return null;
        
        return ((MobDataCard) sfItem).getType();
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
    public void onNewInstance(@Nonnull BlockMenu menu, @Nonnull Block b) {
        Location l = b.getLocation();
        if (BlockStorage.getLocationInfo(l, "xp") == null) {
            setXp(l, 0);
        }
        menu.replaceExistingItem(XP_Slot, makeXpItem(0));
        menu.addMenuClickHandler(XP_Slot, (p, slot, item, action) -> {
            int xp = getXP(l);
            if (xp > 0) {
                p.giveExp(xp);
                p.playSound(l, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                setXp(l, 0);
                menu.replaceExistingItem(XP_Slot, makeXpItem(0));
            }
            return false;
        });
    }
    
    private ItemStack makeXpItem(int stored) {
        return new CustomItem(Material.LIME_STAINED_GLASS_PANE, "&aStored xp: " + stored, "", "&a> Click to claim");
    }

    private void setXp(Location l, int xp) {
        BlockStorage.addBlockInfo(l, "xp", String.valueOf(xp));
    }
    
    private int getXP(Location l) {
        try {
            return Integer.parseInt(BlockStorage.getLocationInfo(l, "xp"));
        } catch (NumberFormatException e) {
            setXp(l, 0);
            return 0;
        }
    }
}
