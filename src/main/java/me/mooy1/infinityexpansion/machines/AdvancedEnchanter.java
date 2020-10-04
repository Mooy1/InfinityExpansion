package me.mooy1.infinityexpansion.machines;

import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.items.electric.machines.AutoEnchanter;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.mooy1.infinityexpansion.Categories;
import me.mooy1.infinityexpansion.Items;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.cscorelib2.inventory.InvUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class AdvancedEnchanter extends AutoEnchanter {

    private final Type type;

    public AdvancedEnchanter(Type type) {
        super(Categories.INFINITY_MACHINES, type.getItem(), type.getRecipeType(), type.getRecipe());
        this.type = type;
    }

    @Override
    public ItemStack getProgressBar() {
        return new ItemStack(Material.NETHERITE_CHESTPLATE);
    }

    @Override
    public int getEnergyConsumption() {
        return type.getEnergyConsumption();
    }

    @Override
    public int getCapacity() {
        return type.getEnergyConsumption();
    }

    @Override
    protected MachineRecipe findNextRecipe(BlockMenu menu) {
        for (int slot : getInputSlots()) {
            ItemStack target = menu.getItemInSlot(slot == getInputSlots()[0] ? getInputSlots()[1] : getInputSlots()[0]);

            // Check if the item is enchantable
            if (!isEnchantable(target)) {
                return null;
            }

            ItemStack item = menu.getItemInSlot(slot);

            if (item != null && item.getType() == Material.ENCHANTED_BOOK && target != null) {
                Map<Enchantment, Integer> enchantments = new HashMap<>();
                int amount = 0;
                int specialAmount = 0;
                amount = getAmount(target, item, enchantments, amount);

                if (amount > 0) {
                    ItemStack enchantedItem = target.clone();
                    enchantedItem.setAmount(1);

                    for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
                        enchantedItem.addUnsafeEnchantment(entry.getKey(), entry.getValue());
                    }

                    MachineRecipe recipe = new MachineRecipe(100 * amount / type.getSpeed(), new ItemStack[] {target, item},
                        new ItemStack[] {enchantedItem, new ItemStack(Material.BOOK)});

                    if (!InvUtils.fitAll(menu.toInventory(), recipe.getOutput(), getOutputSlots())) {
                        return null;
                    }

                    for (int inputSlot : getInputSlots()) {
                        menu.consumeItem(inputSlot);
                    }

                    return recipe;
                }

                return null;
            }
        }

        return null;
    }

    static int getAmount(ItemStack target, ItemStack item, Map<Enchantment, Integer> enchantments, int amount) {
        EnchantmentStorageMeta meta = (EnchantmentStorageMeta) item.getItemMeta();

        for (Map.Entry<Enchantment, Integer> e : meta.getStoredEnchants().entrySet()) {
            if (e.getKey().canEnchantItem(target)) {
                amount++;
                enchantments.put(e.getKey(), e.getValue());
            }
        }
        return amount;
    }

    private boolean isEnchantable(ItemStack item) {
        SlimefunItem sfItem = null;

        // stops endless checks of getByItem for enchanted book stacks.
        if (item != null && item.getType() != Material.ENCHANTED_BOOK) {
            sfItem = SlimefunItem.getByItem(item);
        }

        return sfItem == null || sfItem.isEnchantable();
    }

    @Override
    public int getSpeed() {
        return 10;
    }

    @Override
    public String getMachineIdentifier() {
        return type.getItem().getItemId();
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)

    public enum Type {
        BASIC(Items.ADVANCED_ENCHANTER, 10, 600, RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {
                        Items.MAGSTEEL, Items.MAGSTEEL, Items.MAGSTEEL,
                        Items.MAGNONIUM_INGOT, SlimefunItems.AUTO_ENCHANTER, Items.MAGNONIUM_INGOT,
                        Items.MACHINE_CIRCUIT, Items.MACHINE_CORE, Items.MACHINE_CIRCUIT
                }),
        INFINITY(Items.INFINITY_ENCHANTER, 100, 60000, RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {
                    Items.INFINITE_INGOT, Items.INFINITE_INGOT, Items.INFINITE_INGOT,
                    Items.INFINITE_INGOT, Items.ADVANCED_ENCHANTER, Items.INFINITE_INGOT,
                    Items.INFINITE_MACHINE_CIRCUIT, Items.INFINITE_MACHINE_CORE, Items.INFINITE_MACHINE_CIRCUIT
                });

        @Nonnull
        private final SlimefunItemStack item;
        private final int speed;
        private final int energyConsumption;
        private final RecipeType recipeType;
        private final ItemStack[] recipe;
    }
}