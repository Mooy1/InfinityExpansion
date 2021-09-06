package io.github.mooy1.infinityexpansion.items.machines;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinityexpansion.items.materials.Materials;
import io.github.mooy1.infinitylib.machines.AbstractMachineBlock;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;

/**
 * harvests void bits from... the void
 *
 * @author Mooy1
 */
public final class VoidHarvester extends AbstractMachineBlock implements RecipeDisplayItem {

    public static final RecipeType TYPE = new RecipeType(InfinityExpansion.createKey("void_harvester"), Machines.VOID_HARVESTER);

    private static final int[] OUTPUT_SLOTS = { 13 };
    private static final int TIME = 1024;

    private final int speed;

    public VoidHarvester(ItemGroup category, SlimefunItemStack item, RecipeType type, ItemStack[] recipe, int speed) {
        super(category, item, type, recipe);
        this.speed = speed;
    }

    @Override
    protected boolean process(@Nonnull Block b, @Nonnull BlockMenu inv) {
        int progress = Integer.parseInt(getProgress(b));

        if (progress >= TIME) { //reached full progress

            ItemStack output = Materials.VOID_BIT;

            if (inv.fits(output, OUTPUT_SLOTS)) {

                inv.pushItem(output.clone(), OUTPUT_SLOTS);

                progress = this.speed;

            }
            else {
                if (inv.hasViewer()) {
                    inv.replaceExistingItem(getStatusSlot(), NO_ROOM_ITEM);
                }
                return false;
            }
        }
        else {
            progress += this.speed;
        }

        setProgress(b, progress);
        if (inv.hasViewer()) { //update status
            inv.replaceExistingItem(getStatusSlot(), new CustomItemStack(Material.LIME_STAINED_GLASS_PANE,
                    "&aHarvesting - " + progress * 100 / TIME + "%",
                    "&7(" + progress + "/" + TIME + ")"
            ));
        }
        return true;
    }

    @Override
    protected int getStatusSlot() {
        return 4;
    }

    @Override
    protected void setup(BlockMenuPreset blockMenuPreset) {
        blockMenuPreset.drawBackground(new int[] {
                0, 1, 2, 3, 5, 6, 7, 8,
                9, 10, 11, 12, 14, 15, 16, 17
        });

    }

    @Override
    protected int[] getInputSlots() {
        return new int[0];
    }

    @Override
    protected int[] getOutputSlots() {
        return OUTPUT_SLOTS;
    }

    @Override
    public void onNewInstance(@Nonnull BlockMenu menu, @Nonnull Block b) {
        if (getProgress(b) == null) {
            setProgress(b, 0);
        }
    }

    private static void setProgress(Block b, int progress) {
        BlockStorage.addBlockInfo(b, "progress", String.valueOf(progress));
    }

    private static String getProgress(Block b) {
        return BlockStorage.getLocationInfo(b.getLocation(), "progress");
    }

    @Nonnull
    @Override
    public List<ItemStack> getDisplayRecipes() {
        final List<ItemStack> items = new ArrayList<>();

        items.add(null);
        items.add(Materials.VOID_BIT);

        return items;
    }

    @Nonnull
    @Override
    public String getRecipeSectionLabel(@Nonnull Player p) {
        return "&7Harvests:";
    }

}

