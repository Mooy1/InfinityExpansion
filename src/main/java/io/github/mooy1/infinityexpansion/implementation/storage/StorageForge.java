package io.github.mooy1.infinityexpansion.implementation.storage;

import javax.annotation.Nonnull;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinityexpansion.categories.Categories;
import io.github.mooy1.infinityexpansion.implementation.abstracts.AbstractCrafter;
import io.github.mooy1.infinityexpansion.implementation.materials.Items;
import io.github.mooy1.infinitylib.slimefun.recipes.AdvancedRecipeMap;
import io.github.mooy1.infinitylib.slimefun.recipes.RecipeMap;
import io.github.mooy1.infinitylib.slimefun.recipes.inputs.StrictMultiInput;
import io.github.mooy1.infinitylib.slimefun.recipes.outputs.StrictMultiOutput;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;

/**
 * A crafting machine for upgrading storage units and retaining the stored items
 *
 * @author Mooy1
 */
public final class StorageForge extends AbstractCrafter {
    
    public static final SlimefunItemStack ITEM = new SlimefunItemStack(
            "STORAGE_FORGE",
            Material.BEEHIVE,
            "&6Storage Forge",
            "&7Upgrades the tier of Storage Units",
            "&7Retains stored items"
    );

    private static final RecipeMap<StrictMultiInput, StrictMultiOutput> RECIPES = new AdvancedRecipeMap<>();
    public static final RecipeType TYPE = new RecipeType(InfinityExpansion.inst().getKey("storage_forge"), ITEM,
            ((stacks, itemStack) -> RECIPES.put(new StrictMultiInput(stacks), new StrictMultiOutput(itemStack))));
    
    public StorageForge() {
        super(Categories.STORAGE, ITEM, RECIPES, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                Items.MAGSTEEL, new ItemStack(Material.ANVIL), Items.MAGSTEEL,
                Items.MAGSTEEL, new ItemStack(Material.CRAFTING_TABLE), Items.MAGSTEEL,
                Items.MAGSTEEL, new ItemStack(Material.BARREL), Items.MAGSTEEL,
        });
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
