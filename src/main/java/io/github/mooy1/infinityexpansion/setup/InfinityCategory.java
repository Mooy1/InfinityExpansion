package io.github.mooy1.infinityexpansion.setup;

import io.github.mooy1.infinityexpansion.lists.InfinityRecipes;
import io.github.mooy1.infinityexpansion.utils.PresetUtils;
import io.github.mooy1.infinityexpansion.utils.StackUtils;
import io.github.thebusybiscuit.slimefun4.api.player.PlayerProfile;
import io.github.thebusybiscuit.slimefun4.core.categories.FlexCategory;
import io.github.thebusybiscuit.slimefun4.core.guide.SlimefunGuideLayout;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import lombok.NonNull;
import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinityexpansion.implementation.items.InfinityWorkbench;
import io.github.mooy1.infinityexpansion.lists.Items;
import io.github.mooy1.infinityexpansion.lists.RecipeTypes;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
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

    public InfinityCategory() {
        super(new NamespacedKey(InfinityExpansion.getInstance(), "INFINITY_RECIPES"),
                new CustomItem(Material.SMITHING_TABLE, "&bInfinity &7Recipes"), 2
        );
    }

    @Override
    public boolean isVisible(@Nonnull Player player, @Nonnull PlayerProfile playerProfile, @Nonnull SlimefunGuideLayout slimefunGuideLayout) {
        return !slimefunGuideLayout.equals(SlimefunGuideLayout.CHEAT_SHEET);
    }

    @Override
    public void open(@NonNull Player player, @NonNull PlayerProfile playerProfile, @NonNull SlimefunGuideLayout slimefunGuideLayout) {
        ChestMenu menu = new ChestMenu("&bInfinity Recipes");

        setupMain(menu, player);
        menu.addItem(1, new CustomItem(ChestMenuUtils.getBackButton(player, "",
                ChatColor.GRAY + SlimefunPlugin.getLocalization().getMessage(player, "guide.back.guide")))
        );
        menu.addMenuClickHandler(1, (p, slot, item, action) -> {
            SlimefunPlugin.getRegistry().getGuideLayout(slimefunGuideLayout).openMainMenu(playerProfile, 1);
            return false;
        });

        int i = 9;
        for (ItemStack output : InfinityRecipes.OUTPUTS) {
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

    private static void setupMain(@NonNull ChestMenu menu, @NonNull Player player) {
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

    public static void openFromWorkBench(@NonNull Player player, @NonNull BlockMenu inv) {
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
        for (ItemStack items : InfinityRecipes.OUTPUTS) {
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

    private static void openRecipeFromWorkBench(@NonNull Player player, @NonNull  BlockMenu inv, int id) {
        ItemStack output = InfinityRecipes.OUTPUTS[id];
        ChestMenu menu = new ChestMenu(Objects.requireNonNull(output.getItemMeta()).getDisplayName());
        menu.setEmptySlotsClickable(false);

        menu.addItem(BACK, ChestMenuUtils.getBackButton(player, ""), (p, slot, item, action) -> {
            openFromWorkBench(player, inv);
            return false;
        });

        menu.addItem(PREVIOUS, ChestMenuUtils.getPreviousButton(player, id + 1, InfinityRecipes.OUTPUTS.length), (p, slot, item, action) -> {
            if (id > 0) {
                openRecipeFromWorkBench(p, inv, id - 1);
            }
            return false;
        });
        menu.addItem(NEXT, ChestMenuUtils.getNextButton(player, id + 1, InfinityRecipes.OUTPUTS.length), (p, slot, item, action) -> {
            if (id + 1< InfinityRecipes.OUTPUTS.length) {
                openRecipeFromWorkBench(p, inv, id + 1);
            }
            return false;
        });

        menu.addItem(WORKBENCH, new CustomItem(Material.NETHER_STAR,
                "&bCreate the recipe from items in your inventory: ",
                "&aLeft-Click to move 1 set",
                "&aRight-Click to move as many sets as possible"
        ), (p, slot, item, action) -> {
            if (action.isRightClicked()) {
                makeRecipe(p, inv, id, 64);
            } else {
                makeRecipe(p, inv, id, 1);
            }
            return false;
        });

        int i = 0;
        for (int recipeSlot : RECIPE_SLOTS) {
            ItemStack recipeItem = InfinityRecipes.RECIPES[id][i];

            if (recipeItem != null) {

                menu.addItem(recipeSlot, recipeItem, (p, slot, item, action) -> {
                    SlimefunItem slimefunItem = SlimefunItem.getByItem(recipeItem);

                    if (slimefunItem != null ) {
                        if (slimefunItem.getRecipeType() == RecipeTypes.INFINITY_WORKBENCH) {
                            openRecipeFromWorkBench(player, inv, InfinityRecipes.getRecipeID(recipeItem));
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
     * @param id recipe id
     * @param count times to repeat
     */
    private static void makeRecipe(@NonNull Player player, @NonNull BlockMenu menu, int id, int count) {
        ItemStack[] recipe = InfinityRecipes.RECIPES[id];
        PlayerInventory inv = player.getInventory();

        menu.open(player);

        for (int i = 0 ; i < count ; i++) {
            int recipeSlot = 0;
            for (ItemStack recipeItem : recipe) { //each item in recipe
                if (recipeItem != null) { //not null
                    for (ItemStack item : inv.getContents()) { //each slot in their inv
                        if (Objects.equals(StackUtils.getIDFromItem(recipeItem), StackUtils.getIDFromItem(item))) { //matches recipe
                            //get item
                            ItemStack output = item.clone();
                            output.setAmount(1);

                            if (menu.fits(output, InfinityWorkbench.INPUT_SLOTS[recipeSlot])) {//not null and fits
                                //remove item
                                ItemUtils.consumeItem(item, 1, false);

                                //push item
                                menu.pushItem(output, InfinityWorkbench.INPUT_SLOTS[recipeSlot]);
                                break;
                            }
                        }
                    }
                }

                recipeSlot++;
            }
        }
    }

    private void openInfinityRecipe(@NonNull Player player, int id, @NonNull PlayerProfile playerProfile, @NonNull SlimefunGuideLayout slimefunGuideLayout) {
        ItemStack output = InfinityRecipes.OUTPUTS[id];

        ChestMenu menu = new ChestMenu(ItemUtils.getItemName(output));
        menu.setEmptySlotsClickable(false);

        menu.addItem(BACK, ChestMenuUtils.getBackButton(player, ""), (p, slot, item, action) -> {
            open(player, playerProfile, slimefunGuideLayout);
            return false;
        });

        menu.addItem(PREVIOUS, ChestMenuUtils.getPreviousButton(player, id + 1, InfinityRecipes.OUTPUTS.length), (p, slot, item, action) -> {
            if (id > 0) {
                openInfinityRecipe(p, id - 1, playerProfile, slimefunGuideLayout);
            }
            return false;
        });
        menu.addItem(NEXT, ChestMenuUtils.getNextButton(player, id + 1, InfinityRecipes.OUTPUTS.length), (p, slot, item, action) -> {
            if (id + 1< InfinityRecipes.OUTPUTS.length) {
                openInfinityRecipe(p, id + 1, playerProfile, slimefunGuideLayout);
            }
            return false;
        });

        int i = 0;
        for (int recipeSlot : RECIPE_SLOTS) {
            ItemStack recipeItem = InfinityRecipes.RECIPES[id][i];

            if (recipeItem != null) {

                menu.addItem(recipeSlot, recipeItem, (p, slot, item, action) -> {
                    SlimefunItem slimefunItem = SlimefunItem.getByItem(recipeItem);

                    if (slimefunItem != null ) {
                        if (slimefunItem.getRecipeType() == RecipeTypes.INFINITY_WORKBENCH) {
                            openInfinityRecipe(player, InfinityRecipes.getRecipeID(recipeItem), playerProfile, slimefunGuideLayout);
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

        setupInfinityRecipeMenu(menu, output);

        player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1, 1);
        menu.open(player);
    }

    private void openSlimefunRecipe(@NonNull Player player, int backID, @NonNull SlimefunItem[] slimefunHistory, @NonNull PlayerProfile playerProfile, @NonNull SlimefunGuideLayout slimefunGuideLayout) {
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

    private static void setupInfinityRecipeMenu(@NonNull ChestMenu menu, @NonNull ItemStack output) {
        for (int slot : BACKGROUND) {
            menu.addItem(slot, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }
        for (int slot : OUTPUT_BORDER) {
            menu.addItem(slot, PresetUtils.borderItemOutput, ChestMenuUtils.getEmptyClickHandler());
        }
        menu.addItem(OUTPUT, output, ChestMenuUtils.getEmptyClickHandler());
        for (int slot : WORKBENCH_BORDER) {
            menu.addItem(slot, PresetUtils.craftedIn, ChestMenuUtils.getEmptyClickHandler());
        }
    }

    private static void setupNormalRecipeMenu(@NonNull ChestMenu menu, @NonNull ItemStack output, @NonNull ItemStack recipeType) {
        menu.addItem(NORMAL_RECIPE_TYPE, recipeType, ChestMenuUtils.getEmptyClickHandler());

        for (int slot : NORMAL_RECIPE_BACKGROUND) {
            menu.addItem(slot, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }

        menu.addItem(NORMAL_RECIPE_OUTPUT, output, ChestMenuUtils.getEmptyClickHandler());
    }
}