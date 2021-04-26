package io.github.mooy1.infinityexpansion.implementation.blocks;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinityexpansion.implementation.abstracts.AbstractCrafter;
import io.github.mooy1.infinitylib.slimefun.recipes.inputs.StrictMultiInput;
import io.github.mooy1.infinitylib.slimefun.recipes.outputs.StrictMultiOutput;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;

/**
 * A crafting machine for upgrading storage units and retaining the stored items
 *
 * @author Mooy1
 */
public final class StorageForge extends AbstractCrafter {

    private static final Map<StrictMultiInput, StrictMultiOutput> RECIPES = new HashMap<>();
    public static final RecipeType TYPE = new RecipeType(InfinityExpansion.inst().getKey("storage_forge"), Blocks.STORAGE_FORGE,
            (stacks, itemStack) -> {
                StrictMultiInput input = new StrictMultiInput(stacks);
                RECIPES.put(input, new StrictMultiOutput(itemStack, input.getAmounts()));
            }
    );

    public StorageForge(Category category, SlimefunItemStack stack, RecipeType type, ItemStack[] recipe) {
        super(category, stack, RECIPES, type, recipe);
    }

    @Override
    protected void modifyOutput(@Nonnull BlockMenu inv, @Nonnull ItemStack output) {
        StorageUnit.transferToStack(inv.getItemInSlot(INPUT_SLOTS[4]), output);
    }

    @Override
    public void postCraft(@Nonnull Location l, @Nonnull BlockMenu inv, @Nonnull Player p) {
        p.sendMessage(ChatColor.GREEN + "Transferred items to upgraded unit");
    }
    
}
