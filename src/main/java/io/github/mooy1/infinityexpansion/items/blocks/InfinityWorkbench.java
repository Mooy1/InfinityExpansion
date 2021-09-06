package io.github.mooy1.infinityexpansion.items.blocks;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.infinityexpansion.categories.InfinityGroup;
import io.github.mooy1.infinitylib.machines.MachineLayout;
import io.github.mooy1.infinitylib.machines.MachineRecipeType;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.collections.Pair;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;

@ParametersAreNonnullByDefault
public final class InfinityWorkbench extends io.github.mooy1.infinitylib.machines.CraftingBlock implements EnergyNetComponent {

    public static final int[] INPUT_SLOTS = {
            0, 1, 2, 3, 4, 5,
            9, 10, 11, 12, 13, 14,
            18, 19, 20, 21, 22, 23,
            27, 28, 29, 30, 31, 32,
            36, 37, 38, 39, 40, 41,
            45, 46, 47, 48, 49, 50
    };
    private static final int[] OUTPUT_SLOTS = { 43 };
    private static final int STATUS_SLOT = 16;
    private static final int RECIPE_SLOT = 7;

    public static final LinkedHashMap<String, Pair<SlimefunItemStack, ItemStack[]>> ITEMS = new LinkedHashMap<>();
    public static final List<String> IDS = new ArrayList<>();

    public static final MachineRecipeType TYPE = new MachineRecipeType("infinity_forge",
            new CustomItemStack(Blocks.INFINITY_FORGE, Blocks.INFINITY_FORGE.getDisplayName(),
                    "", "&cUse the infinity recipes category to see the correct recipe!"));

    static {
        TYPE.sendRecipesTo((input, output) -> {
            SlimefunItemStack item = (SlimefunItemStack) output;
            IDS.add(item.getItemId());
            ITEMS.put(item.getItemId(), new Pair<>(item, input));
        });
    }

    private final int energy;

    public InfinityWorkbench(ItemGroup category, SlimefunItemStack item, RecipeType type, ItemStack[] recipe, int energy) {
        super(category, item, type, recipe);
        layout(new MachineLayout()
                .inputSlots(INPUT_SLOTS)
                .outputSlots(OUTPUT_SLOTS)
                .statusSlot(STATUS_SLOT)
                .inputBorder(new int[0])
                .outputBorder(new int[] {
                        33, 34, 35,
                        42, 44,
                        51, 52, 53
                }).background(new int[] {
                        6, 8, 15, 17, 24, 25, 26
                })
        );
        this.energy = energy;
    }

    @Override
    protected void onNewInstance(BlockMenu menu, Block b) {
        super.onNewInstance(menu, b);
        menu.addItem(RECIPE_SLOT, new CustomItemStack(Material.BOOK, "&6Recipes"), (p, slot, item, action) -> {
            InfinityGroup.open(p, menu);
            return false;
        });
    }

    @Override
    protected void craft(Block b, BlockMenu menu, Player p) {
        int charge = getCharge(menu.getLocation());
        if (charge < this.energy) {
            p.sendMessage(
                    ChatColor.RED + "Not enough energy!",
                    ChatColor.GREEN + "Charge: " + ChatColor.RED + charge + ChatColor.GREEN + "/" + this.energy + " J"
            );
        }
        else {
            super.craft(b, menu, p);
        }
    }

    @Override
    protected void onSuccessfulCraft(BlockMenu menu, ItemStack toOutput) {
        setCharge(menu.getLocation(), 0);
    }

    @Nonnull
    @Override
    public EnergyNetComponentType getEnergyComponentType() {
        return EnergyNetComponentType.CONSUMER;
    }

    @Override
    public int getCapacity() {
        return this.energy;
    }

}
