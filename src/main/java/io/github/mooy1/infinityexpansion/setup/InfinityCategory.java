package io.github.mooy1.infinityexpansion.setup;

import io.github.mooy1.infinityexpansion.implementation.items.InfinityWorkbench;
import io.github.mooy1.infinityexpansion.lists.Categories;
import io.github.mooy1.infinityexpansion.lists.InfinityRecipes;
import io.github.mooy1.infinityexpansion.lists.Items;
import io.github.mooy1.infinityexpansion.lists.RecipeTypes;
import io.github.mooy1.infinitylib.PluginUtils;
import io.github.mooy1.infinitylib.filter.FilterType;
import io.github.mooy1.infinitylib.filter.ItemFilter;
import io.github.mooy1.infinitylib.items.StackUtils;
import io.github.mooy1.infinitylib.presets.MenuPreset;
import io.github.thebusybiscuit.slimefun4.api.player.PlayerProfile;
import io.github.thebusybiscuit.slimefun4.core.categories.FlexCategory;
import io.github.thebusybiscuit.slimefun4.core.guide.SlimefunGuideLayout;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.cscorelib2.collections.Pair;
import me.mrCookieSlime.Slimefun.cscorelib2.inventory.ItemUtils;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.logging.Level;

/**
 * A custom category for displaying 6x6 recipes and their sub-recipes
 *
 * @author Mooy1
 */
public class InfinityCategory extends FlexCategory {

    private static final int[] RECIPE_SLOTS = {
            1, 2, 3, 4, 5, 6,
            10, 11, 12, 13, 14, 15,
            19, 20, 21, 22, 23, 24,
            28, 29, 30, 31, 32, 33,
            37, 38, 39, 40, 41, 42,
            46, 47, 48, 49, 50, 51
    };
    private static final int[] NORMAL_RECIPE_SLOTS = {
            12, 13, 14,
            21, 22, 23,
            30, 31, 32
    };
    private static final int NORMAL_RECIPE_TYPE = 19;
    private static final int NORMAL_RECIPE_OUTPUT = 25;
    private static final int[] NORMAL_RECIPE_BACKGROUND = {
            1, 2, 3, 4, 5, 6, 7, 8,
            36, 37, 38 ,39 ,40, 41 ,42 ,43, 44
    };
    private static final int[] OUTPUT_BORDER = {
            25, 26, 34, 43, 44
    };
    private static final int[] BACKGROUND = {
            9, 18, 27, 36, 52
    };
    private static final int OUTPUT = 35;
    private static final int BACK = 0;
    private static final int PREVIOUS = 45;
    private static final int NEXT = 53;
    private static final int WORKBENCH = 8;
    private static final int[] WORKBENCH_BORDER = {
            7, 16, 17
    };

    public InfinityCategory(NamespacedKey key, ItemStack item, int tier) {
        super(key, item, tier);
    }

    @Override
    public boolean isVisible(@Nonnull Player player, @Nonnull PlayerProfile playerProfile, @Nonnull SlimefunGuideLayout slimefunGuideLayout) {
        return false;
    }

    @Override
    public void open(@Nonnull Player player, @Nonnull PlayerProfile playerProfile, @Nonnull SlimefunGuideLayout slimefunGuideLayout) {
        ChestMenu menu = new ChestMenu("&bInfinity Recipes");

        setupMain(menu, player);
        menu.addItem(1, ChestMenuUtils.getBackButton(player, "", 
                ChatColor.GRAY + SlimefunPlugin.getLocalization().getMessage(player, "guide.back.guide"))
        );
        menu.addMenuClickHandler(1, (p, slot, item, action) -> {
            Categories.MAIN.open(p, playerProfile, slimefunGuideLayout);
            return false;
        });

        int i = 9;
        for (ItemStack output : InfinityRecipes.RECIPES.keySet()) {
            menu.addItem(i, output, (p, slot, item, action) -> {
                openInfinityRecipe(p, slot - 9, playerProfile, slimefunGuideLayout);
                return false;
            });

            i++;
            if (i == 45) break;
        }

        player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1, 1);
        playerProfile.getGuideHistory().add(this, 1);

        menu.open(player);
    }

    private static void setupMain(@Nonnull ChestMenu menu, @Nonnull Player player) {
        int i;
        menu.addItem(0, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        menu.setEmptySlotsClickable(false);

        for (i = 2 ; i < 9 ; i++) {
            menu.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }

        menu.addItem(45, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());

        menu.addItem(46, ChestMenuUtils.getPreviousButton(player,1, 1), ChestMenuUtils.getEmptyClickHandler());

        for (i = 47 ; i < 52 ; i++) {
            menu.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }

        menu.addItem(52, ChestMenuUtils.getNextButton(player,1, 1), ChestMenuUtils.getEmptyClickHandler());

        menu.addItem(53, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
    }

    public static void openFromWorkBench(@Nonnull Player player, @Nonnull BlockMenu inv) {
        ChestMenu menu = new ChestMenu("&bInfinity Recipes");

        setupMain(menu, player);
        menu.addItem(1, new CustomItem(ChestMenuUtils.getBackButton(player, "",
                ChatColor.GRAY + SlimefunPlugin.getLocalization().getMessage(player, "guide.back.guide")))
        );
        menu.addMenuClickHandler(1, (p, slot, item, action) -> {
            inv.open(player);
            return false;
        });

        int i = 9;
        for (ItemStack items : InfinityRecipes.RECIPES.keySet()) {
            menu.addItem(i, items, (p, slot, item, action) -> {
                openRecipeFromWorkBench(player, inv, slot - 9);
                return false;
            });

            i++;
            if (i == 45) break;
        }

        player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1, 1);

        menu.open(player);
    }

    private static void openRecipeFromWorkBench(@Nonnull Player player, @Nonnull BlockMenu inv, int id) {
        Pair<SlimefunItemStack, ItemStack[]> pair = InfinityRecipes.IDS.get(id);
        if (pair == null) {
            PluginUtils.log(Level.WARNING, "Error opening recipe for Infinity Item id: " + id + " Report this on Discord or Github!");
            return;
        }
        SlimefunItemStack output = pair.getFirstValue();

        ChestMenu menu = new ChestMenu(Objects.requireNonNull(output.getDisplayName()));
        menu.setEmptySlotsClickable(false);

        menu.addItem(BACK, ChestMenuUtils.getBackButton(player, ""), (p, slot, item, action) -> {
            openFromWorkBench(player, inv);
            return false;
        });

        menu.addItem(PREVIOUS, ChestMenuUtils.getPreviousButton(player, id + 1, InfinityRecipes.RECIPES.size()), (p, slot, item, action) -> {
            if (id > 0) {
                openRecipeFromWorkBench(p, inv, id - 1);
            }
            return false;
        });
        menu.addItem(NEXT, ChestMenuUtils.getNextButton(player, id + 1, InfinityRecipes.RECIPES.size()), (p, slot, item, action) -> {
            if (id + 1< InfinityRecipes.RECIPES.size()) {
                openRecipeFromWorkBench(p, inv, id + 1);
            }
            return false;
        });

        menu.addItem(WORKBENCH, new CustomItem(Material.NETHER_STAR,
                "&bCreate the recipe from items in your inventory: ",
                "&aLeft-Click to move 1 set",
                "&aRight-Click to move as many sets as possible"
        ), (p, slot, item, action) -> {
            makeRecipe(p, inv, pair, action.isRightClicked());
            return false;
        });

        int i = 0;
        ItemStack[] recipe = pair.getSecondValue();
        for (int recipeSlot : RECIPE_SLOTS) {
            ItemStack recipeItem = recipe[i];
            if (recipeItem != null) {
                menu.addItem(recipeSlot, recipeItem, (p, slot, item, action) -> {
                    SlimefunItem slimefunItem = SlimefunItem.getByItem(recipeItem);

                    if (slimefunItem != null ) {
                        if (slimefunItem.getRecipeType() == RecipeTypes.INFINITY_WORKBENCH) {
                            openRecipeFromWorkBench(player, inv, InfinityRecipes.STRINGS.get(slimefunItem.getId()));
                        }
                    }
                    return false;
                });
            }
            i++;
        }

        setupInfinityRecipeMenu(menu, output);

        player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1, 1);

        menu.open(player);
    }

    /**
     * This method attempts to move items from the players inventories to the correct spots in the table
     *
     * @param player player
     * @param menu workbench menu
     */

    private static void makeRecipe(@Nonnull Player player, @Nonnull BlockMenu menu, Pair<SlimefunItemStack, ItemStack[]> pair, boolean max) {
        ItemStack[] recipe = pair.getSecondValue();
        PlayerInventory inv = player.getInventory();

        for (int i = 0 ; i < (max ? 64 : 1) ; i++) {
            for (int slot = 0 ; slot < recipe.length ; slot++) { //each item in recipe
                ItemStack recipeItem = recipe[slot];

                if (recipeItem == null) {
                    continue;
                }

                ItemFilter filter = new ItemFilter(recipe[slot], FilterType.IGNORE_AMOUNT);

                for (ItemStack item : inv.getContents()) { //each slot in their inv
                    if (item != null && filter.matches(new ItemFilter(item, FilterType.IGNORE_AMOUNT), FilterType.IGNORE_AMOUNT)) { //matches recipe
                        //get item
                        ItemStack output = item.clone();
                        output.setAmount(1);

                        if (menu.fits(output, InfinityWorkbench.INPUT_SLOTS[slot])) {//not null and fits
                            //remove item
                            ItemUtils.consumeItem(item, 1, false);
                            //push item
                            menu.pushItem(output, InfinityWorkbench.INPUT_SLOTS[slot]);
                            break;
                        }
                    }
                }
            }
        }
        
        menu.open(player);

    }

    private void openInfinityRecipe(@Nonnull Player player, int id, @Nonnull PlayerProfile playerProfile, @Nonnull SlimefunGuideLayout slimefunGuideLayout) {
        Pair<SlimefunItemStack, ItemStack[]> pair = InfinityRecipes.IDS.get(id);
        if (pair == null) {
            PluginUtils.log(Level.WARNING, "Error opening recipe for Infinity Item id: " + id, "Report this on Discord or Github!");
            return;
        }

        ChestMenu menu = new ChestMenu(Objects.requireNonNull(pair.getFirstValue().getDisplayName()));
        menu.setEmptySlotsClickable(false);

        menu.addItem(BACK, ChestMenuUtils.getBackButton(player, ""), (p, slot, item, action) -> {
            open(player, playerProfile, slimefunGuideLayout);
            return false;
        });

        menu.addItem(PREVIOUS, ChestMenuUtils.getPreviousButton(player, id + 1, InfinityRecipes.RECIPES.size()), (p, slot, item, action) -> {
            if (id > 0) {
                openInfinityRecipe(p, id - 1, playerProfile, slimefunGuideLayout);
            }
            return false;
        });
        menu.addItem(NEXT, ChestMenuUtils.getNextButton(player, id + 1, InfinityRecipes.RECIPES.size()), (p, slot, item, action) -> {
            if (id + 1< InfinityRecipes.RECIPES.size()) {
                openInfinityRecipe(p, id + 1, playerProfile, slimefunGuideLayout);
            }
            return false;
        });
        
        int i = 0;
        for (int recipeSlot : RECIPE_SLOTS) {
            ItemStack recipeItem = pair.getSecondValue()[i];

            if (recipeItem != null) {

                menu.addItem(recipeSlot, recipeItem, (p, slot, item, action) -> {
                    SlimefunItem slimefunItem = SlimefunItem.getByItem(recipeItem);

                    if (slimefunItem != null ) {
                        if (slimefunItem.getRecipeType() == RecipeTypes.INFINITY_WORKBENCH) {
                            openInfinityRecipe(player, InfinityRecipes.STRINGS.get(StackUtils.getItemID(recipeItem, false)), playerProfile, slimefunGuideLayout);
                        } else {
                            openSlimefunRecipe(player, id, new SlimefunItem[] {slimefunItem}, playerProfile, slimefunGuideLayout);
                        }
                    }
                    return false;
                });
            }
            i++;
        }

        menu.addItem(WORKBENCH, Items.INFINITY_WORKBENCH, (p, slot, item, action) -> {
            openSlimefunRecipe(player, id, new SlimefunItem[]{SlimefunItem.getByItem(Items.INFINITY_WORKBENCH)}, playerProfile, slimefunGuideLayout);
            return false;
        });

        setupInfinityRecipeMenu(menu, pair.getFirstValue());

        player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1, 1);
        menu.open(player);
        
    }

    private void openSlimefunRecipe(@Nonnull Player player, int backID, @Nonnull SlimefunItem[] slimefunHistory, @Nonnull PlayerProfile playerProfile, @Nonnull SlimefunGuideLayout slimefunGuideLayout) {
        int length = slimefunHistory.length;

        SlimefunItem slimefunItem = slimefunHistory[length - 1];
        ItemStack output = slimefunItem.getRecipeOutput().clone();

        ChestMenu menu = new ChestMenu(ItemUtils.getItemName(output));
        menu.setEmptySlotsClickable(false);

        menu.addItem(0, ChestMenuUtils.getBackButton(player, ""), (p, slot, item, action) -> {
            if (length == 1) {
                openInfinityRecipe(player, backID, playerProfile, slimefunGuideLayout);
            } else {
                SlimefunItem[] backHistory = new SlimefunItem[length - 1];
                System.arraycopy(slimefunHistory, 0, backHistory, 0, length - 1);
                openSlimefunRecipe(player, backID, backHistory, playerProfile, slimefunGuideLayout);
            }
            return false;
        });

        int i = 0;
        for (int recipeSlot : NORMAL_RECIPE_SLOTS) {
            ItemStack recipeItem = slimefunItem.getRecipe()[i];

            if (recipeItem != null) {

                menu.addItem(recipeSlot, recipeItem, (p, slot, item, action) -> {
                    SlimefunItem recipeSlimefunItem = SlimefunItem.getByItem(recipeItem);

                    if (recipeSlimefunItem != null) {
                        SlimefunItem[] newHistory = new SlimefunItem[length + 1];
                        System.arraycopy(slimefunHistory, 0, newHistory, 0, length);
                        newHistory[length] = recipeSlimefunItem;

                        openSlimefunRecipe(player, backID, newHistory, playerProfile, slimefunGuideLayout);
                    }
                    return false;
                });
            }

            i++;
        }

        setupNormalRecipeMenu(menu, output, slimefunItem.getRecipeType().toItem());

        player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1, 1);
        menu.open(player);
    }
    
    private static final ItemStack recipe = new CustomItem(Material.CYAN_STAINED_GLASS_PANE, "&aRecipe Type");

    private static void setupInfinityRecipeMenu(@Nonnull ChestMenu menu, @Nonnull ItemStack output) {
        for (int slot : BACKGROUND) {
            menu.addItem(slot, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }
        for (int slot : OUTPUT_BORDER) {
            menu.addItem(slot, MenuPreset.borderItemOutput, ChestMenuUtils.getEmptyClickHandler());
        }
        menu.addItem(OUTPUT, output, ChestMenuUtils.getEmptyClickHandler());
        for (int slot : WORKBENCH_BORDER) {
            menu.addItem(slot, recipe, ChestMenuUtils.getEmptyClickHandler());
        }
    }

    private static void setupNormalRecipeMenu(@Nonnull ChestMenu menu, @Nonnull ItemStack output, @Nonnull ItemStack recipeType) {
        menu.addItem(NORMAL_RECIPE_TYPE, recipeType, ChestMenuUtils.getEmptyClickHandler());

        for (int slot : NORMAL_RECIPE_BACKGROUND) {
            menu.addItem(slot, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }

        menu.addItem(NORMAL_RECIPE_OUTPUT, output, ChestMenuUtils.getEmptyClickHandler());
    }
}