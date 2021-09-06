package io.github.mooy1.infinityexpansion.items.storage;

import javax.annotation.ParametersAreNonnullByDefault;

import org.bukkit.inventory.ItemStack;

import io.github.mooy1.infinitylib.machines.CraftingBlock;
import io.github.mooy1.infinitylib.machines.MachineRecipeType;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;

/**
 * A crafting machine for upgrading storage units and retaining the stored items
 *
 * @author Mooy1
 */
@ParametersAreNonnullByDefault
public final class StorageForge extends CraftingBlock {

    public static final MachineRecipeType TYPE = new MachineRecipeType("storage_forge", Storage.STORAGE_FORGE);

    public StorageForge(ItemGroup category, SlimefunItemStack stack, RecipeType type, ItemStack[] recipe) {
        super(category, stack, type, recipe);
        addRecipesFrom(TYPE);
    }

    @Override
    protected void onSuccessfulCraft(BlockMenu menu, ItemStack toOutput) {
        StorageUnit.transferToStack(menu.getItemInSlot(layout.inputSlots()[4]), toOutput);
    }

}
