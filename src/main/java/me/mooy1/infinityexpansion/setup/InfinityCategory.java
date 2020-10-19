package me.mooy1.infinityexpansion.setup;

import io.github.thebusybiscuit.slimefun4.api.player.PlayerProfile;
import io.github.thebusybiscuit.slimefun4.core.categories.FlexCategory;
import io.github.thebusybiscuit.slimefun4.core.guide.SlimefunGuideLayout;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mooy1.infinityexpansion.InfinityExpansion;
import me.mooy1.infinityexpansion.implementation.machines.InfinityWorkbench;
import me.mooy1.infinityexpansion.lists.InfinityRecipes;
import me.mooy1.infinityexpansion.lists.Items;
import me.mooy1.infinityexpansion.utils.ItemStackUtils;
import me.mooy1.infinityexpansion.utils.PresetUtils;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
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
    public void open(Player player, PlayerProfile playerProfile, SlimefunGuideLayout slimefunGuideLayout) {
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
        for (ItemStack items : InfinityRecipes.OUTPUTS) {
            menu.addItem(i, items, (p, slot, item, action) -> {
                openRecipe(p, slot - 9, playerProfile, slimefunGuideLayout);
                return false;
            });

            i++;
            if (i == 45) break;
        }

        player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1, 1);
        playerProfile.getGuideHistory().add(this, 1);

        menu.open(player);
    }

    private static void setupMain(ChestMenu menu, Player player) {
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

    public static void openFromWorkBench(Player player, BlockMenu inv) {
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

    private static void openRecipeFromWorkBench(Player player, BlockMenu inv, int id) {
        ItemStack output = InfinityRecipes.OUTPUTS[id];
        ChestMenu menu = new ChestMenu(Objects.requireNonNull(output.getItemMeta()).getDisplayName());

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

        setupRecipe(menu, output);

        player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1, 1);

        menu.open(player);
    }

    private static void makeRecipe(Player player, BlockMenu menu, int id, int repeats) {
        ItemStack[] recipe = InfinityRecipes.RECIPES[id];
        PlayerInventory inv = player.getInventory();

        menu.open(player);

        for (int i = 0 ; i < repeats ; i++) {

            int recipeSlot = 0;
            for (ItemStack recipeItem : recipe) { //each item in recipe
                if (recipeItem != null && menu.fits(recipeItem, InfinityWorkbench.INPUT_SLOTS[recipeSlot])) {

                    int slot = 0;
                    for (ItemStack item : inv.getContents()) { //each slot in their inv
                        if (ItemStackUtils.getIDFromItem(recipeItem).equals(ItemStackUtils.getIDFromItem(item))) {

                            //remove item
                            int amount = item.getAmount();
                            if (amount == 1) {
                                inv.setItem(slot, null);
                            } else {
                                item.setAmount(amount - 1);
                                inv.setItem(slot, item);
                            }

                            //add item

                            menu.pushItem(recipeItem.clone(), InfinityWorkbench.INPUT_SLOTS[recipeSlot]);
                            break;
                        }

                        slot++;
                    }
                }

                recipeSlot++;
            }
        }
    }

    private void openRecipe(Player player, int id, PlayerProfile playerProfile, SlimefunGuideLayout slimefunGuideLayout) {
        ItemStack output = InfinityRecipes.OUTPUTS[id];
        ChestMenu menu = new ChestMenu(Objects.requireNonNull(output.getItemMeta()).getDisplayName());

        menu.addItem(BACK, ChestMenuUtils.getBackButton(player, ""), (p, slot, item, action) -> {
            open(player, playerProfile, slimefunGuideLayout);
            return false;
        });

        menu.addItem(PREVIOUS, ChestMenuUtils.getPreviousButton(player, id + 1, InfinityRecipes.OUTPUTS.length), (p, slot, item, action) -> {
            if (id > 0) {
                openRecipe(p, id - 1, playerProfile, slimefunGuideLayout);
            }
            return false;
        });
        menu.addItem(NEXT, ChestMenuUtils.getNextButton(player, id + 1, InfinityRecipes.OUTPUTS.length), (p, slot, item, action) -> {
            if (id + 1< InfinityRecipes.OUTPUTS.length) {
                openRecipe(p, id + 1, playerProfile, slimefunGuideLayout);
            }
            return false;
        });

        menu.addItem(WORKBENCH, Items.INFINITY_WORKBENCH, (p, slot, item, action) -> {
            openNormalRecipe(player, Objects.requireNonNull(SlimefunItem.getByItem(Items.INFINITY_WORKBENCH)), id, playerProfile, slimefunGuideLayout);
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
                            openRecipe(player, InfinityRecipes.getRecipeID(recipeItem), playerProfile, slimefunGuideLayout);
                        } else {
                            openNormalRecipe(player, Objects.requireNonNull(SlimefunItem.getByItem(recipeItem)), id, playerProfile, slimefunGuideLayout);
                        }
                    }
                    return false;
                });
            }
            i++;
        }

        setupRecipe(menu, output);

        player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1, 1);

        menu.open(player);
    }

    private static void setupRecipe(ChestMenu menu, ItemStack output) {
        menu.setEmptySlotsClickable(false);

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

    private void openNormalRecipe(Player player, SlimefunItem slimefunItem, int fromID, PlayerProfile playerProfile, SlimefunGuideLayout slimefunGuideLayout) {
        ChestMenu menu = new ChestMenu(slimefunItem.getItemName());

        menu.addItem(0, ChestMenuUtils.getBackButton(player, ""), (p, slot, item, action) -> {
            openRecipe(player, fromID, playerProfile, slimefunGuideLayout);
            return false;
        });

        int i = 0;
        for (int recipeSlot : NORMAL_RECIPE_SLOTS) {
            ItemStack recipeItem = slimefunItem.getRecipe()[i];
            menu.addItem(recipeSlot, recipeItem, (p, slot, item, action) -> {
                SlimefunItem recipeSlimefunItem = SlimefunItem.getByItem(recipeItem);
                if (recipeSlimefunItem != null) {
                    openNormalRecipeRecipe(player, recipeSlimefunItem, fromID, slimefunItem, playerProfile, slimefunGuideLayout);
                }
                return false;
            });
            i++;
        }

        setupNormalRecipe(menu, slimefunItem);

        player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1, 1);

        menu.open(player);
    }

    private void openNormalRecipeRecipe(Player player, SlimefunItem slimefunItem, int fromID, SlimefunItem fromSlimefunItem, PlayerProfile playerProfile, SlimefunGuideLayout slimefunGuideLayout) {
        ChestMenu menu = new ChestMenu(slimefunItem.getItemName());

        menu.addItem(0, ChestMenuUtils.getBackButton(player, ""), (p, slot, item, action) -> {
            openNormalRecipe(player, fromSlimefunItem, fromID, playerProfile, slimefunGuideLayout);
            return false;
        });

        int i = 0;
        for (int recipeSlot : NORMAL_RECIPE_SLOTS) {
            ItemStack recipeItem = slimefunItem.getRecipe()[i];
            menu.addItem(recipeSlot, recipeItem, (p, slot, item, action) -> {
                SlimefunItem recipeSlimefunItem = SlimefunItem.getByItem(recipeItem);
                if (recipeSlimefunItem != null) {
                    openNormalRecipeRecipe(player, recipeSlimefunItem, fromID, fromSlimefunItem, playerProfile, slimefunGuideLayout);
                }
                return false;
            });
            i++;
        }

        setupNormalRecipe(menu, slimefunItem);

        player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1, 1);

        menu.open(player);
    }

    private static void setupNormalRecipe(ChestMenu menu, SlimefunItem slimefunItem) {
        menu.setEmptySlotsClickable(false);
        menu.addItem(NORMAL_RECIPE_TYPE, slimefunItem.getRecipeType().toItem(), ChestMenuUtils.getEmptyClickHandler());

        for (int slot : NORMAL_RECIPE_BACKGROUND) {
            menu.addItem(slot, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }

        menu.addItem(NORMAL_RECIPE_OUTPUT, slimefunItem.getRecipeOutput(), ChestMenuUtils.getEmptyClickHandler());
    }
}