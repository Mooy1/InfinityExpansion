package me.mooy1.infinityexpansion.Machines;

import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.items.electric.machines.AutoEnchanter;
import me.mooy1.infinityexpansion.Categories;
import me.mooy1.infinityexpansion.Items;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.cscorelib2.inventory.InvUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.util.HashMap;
import java.util.Map;

public class AdvancedEnchanter extends AutoEnchanter {

    public AdvancedEnchanter() {
        super(Categories.INFINITY_MACHINES,
                Items.ADVANCED_ENCHANTER,
                RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {
                        Items.MAGNONIUM_INGOT, Items.MAGNONIUM_INGOT, Items.MAGNONIUM_INGOT,
                        Items.MAGNONIUM_INGOT, SlimefunItems.AUTO_ENCHANTER, Items.MAGNONIUM_INGOT,
                        Items.MACHINE_CIRCUIT, Items.MACHINE_CORE, Items.MACHINE_CIRCUIT
        });
    }

    @Override
    public ItemStack getProgressBar() {
        return new ItemStack(Material.CHAINMAIL_CHESTPLATE);
    }

    @Override
    public int getEnergyConsumption() {
        return 600;
    }

    @Override
    public int getCapacity() {
        return 600;
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
                EnchantmentStorageMeta meta = (EnchantmentStorageMeta) item.getItemMeta();

                for (Map.Entry<Enchantment, Integer> e : meta.getStoredEnchants().entrySet()) {
                    if (e.getKey().canEnchantItem(target)) {
                        amount++;
                        enchantments.put(e.getKey(), e.getValue());
                    }
                }

                if (amount > 0) {
                    ItemStack enchantedItem = target.clone();
                    enchantedItem.setAmount(1);

                    for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
                        enchantedItem.addUnsafeEnchantment(entry.getKey(), entry.getValue());
                    }

                    MachineRecipe recipe = new MachineRecipe(10 * amount, new ItemStack[] { target, item }, new ItemStack[] { enchantedItem, new ItemStack(Material.BOOK) });

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
        return "ADVANCED_ENCHANTER";
    }

}