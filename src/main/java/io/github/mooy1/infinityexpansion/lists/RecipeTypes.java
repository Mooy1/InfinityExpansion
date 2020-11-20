package io.github.mooy1.infinityexpansion.lists;

import io.github.mooy1.infinityexpansion.InfinityExpansion;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import org.bukkit.NamespacedKey;

/**
 * RecipeTypes for this addon
 *
 * @author Mooy1
 */
public class RecipeTypes {

    public static final RecipeType INFINITY_WORKBENCH = new RecipeType(new NamespacedKey(InfinityExpansion.getInstance(), "infinity_workbench"), Items.INFINITY_WORKBENCH, "", "&cUse the infinity recipes category to see the correct recipe!");

    public static final RecipeType VOID_HARVESTER = new RecipeType(new NamespacedKey(InfinityExpansion.getInstance(), "void_harvester"), Items.VOID_HARVESTER);

    public static final RecipeType SINGULARITY_CONSTRUCTOR = new RecipeType(new NamespacedKey(InfinityExpansion.getInstance(), "singularity_constructor"), Items.SINGULARITY_CONSTRUCTOR);

    public static final RecipeType STORAGE_FORGE = new RecipeType(new NamespacedKey(InfinityExpansion.getInstance(), "storage_forge"), Items.STORAGE_FORGE);

    public static final RecipeType DATA_INFUSER = new RecipeType(new NamespacedKey(InfinityExpansion.getInstance(), "data_infuser"), Items.DATA_INFUSER);
    
}
