package me.mooy1.infinityexpansion.Machines;

import io.github.thebusybiscuit.slimefun4.api.events.AutoDisenchantEvent;
import io.github.thebusybiscuit.slimefun4.implementation.items.electric.machines.AutoDisenchanter;
import me.mooy1.infinityexpansion.Items;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.cscorelib2.inventory.InvUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Repairable;

import java.util.HashMap;
import java.util.Map;

public class InfinityDisenchanter extends AutoDisenchanter {

    public InfinityDisenchanter() {
        super(Items.MOOYMACHINES,
                Items.INFINITY_DISENCHANTER,
                RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {
                        Items.INFINITY_INGOT, Items.INFINITY_INGOT, Items.INFINITY_INGOT,
                        Items.INFINITY_INGOT, Items.ADVANCED_DISENCHANTER, Items.INFINITY_INGOT,
                        Items.INFINITE_MACHINE_CIRCUIT, Items.INFINITE_MACHINE_CORE, Items.INFINITE_MACHINE_CIRCUIT
        });
    }

    @Override
    public ItemStack getProgressBar() {
        return new ItemStack(Material.NETHERITE_CHESTPLATE);
    }

    @Override
    public int getEnergyConsumption() {
        return 99999;
    }

    @Override
    public int getCapacity() {
        return 99999;
    }

    @Override
    protected MachineRecipe findNextRecipe(BlockMenu menu) {
        Map<Enchantment, Integer> enchantments = new HashMap<>();

        for (int slot : getInputSlots()) {
            ItemStack item = menu.getItemInSlot(slot);

            if (!isDisenchantable(item)) {
                return null;
            }

            AutoDisenchantEvent event = new AutoDisenchantEvent(item);
            Bukkit.getPluginManager().callEvent(event);

            if (event.isCancelled()) {
                return null;
            }

            ItemStack target = menu.getItemInSlot(slot == getInputSlots()[0] ? getInputSlots()[1] : getInputSlots()[0]);

            // Disenchanting
            if (target != null && target.getType() == Material.BOOK) {
                int amount = 0;

                for (Map.Entry<Enchantment, Integer> entry : item.getEnchantments().entrySet()) {
                    enchantments.put(entry.getKey(), entry.getValue());
                    amount++;
                }

                if (amount > 0) {
                    ItemStack disenchantedItem = item.clone();
                    disenchantedItem.setAmount(1);

                    ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
                    transferEnchantments(disenchantedItem, book, enchantments);

                    MachineRecipe recipe = new MachineRecipe(1 * amount, new ItemStack[] { target, item }, new ItemStack[] { disenchantedItem, book });

                    if (!InvUtils.fitAll(menu.toInventory(), recipe.getOutput(), getOutputSlots())) {
                        return null;
                    }

                    for (int inputSlot : getInputSlots()) {
                        menu.consumeItem(inputSlot);
                    }

                    return recipe;
                }
            }
        }

        return null;
    }

    private void transferEnchantments(ItemStack item, ItemStack book, Map<Enchantment, Integer> enchantments) {
        ItemMeta itemMeta = item.getItemMeta();
        ItemMeta bookMeta = book.getItemMeta();
        ((Repairable) bookMeta).setRepairCost(((Repairable) itemMeta).getRepairCost());
        ((Repairable) itemMeta).setRepairCost(0);
        item.setItemMeta(itemMeta);
        book.setItemMeta(bookMeta);

        EnchantmentStorageMeta meta = (EnchantmentStorageMeta) book.getItemMeta();

        for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
            item.removeEnchantment(entry.getKey());
            meta.addStoredEnchant(entry.getKey(), entry.getValue(), true);
        }

        book.setItemMeta(meta);
    }

    private boolean isDisenchantable(ItemStack item) {
        if (item == null) {
            return false;
        }
        // stops endless checks of getByItem for books
        else if (item.getType() != Material.BOOK) {
            SlimefunItem sfItem = SlimefunItem.getByItem(item);
            return sfItem == null || sfItem.isDisenchantable();
        }
        else {
            return true;
        }

    }


    @Override
    public int getSpeed() {
        return 100;
    }

    @Override
    public String getMachineIdentifier() {
        return "INFINITY_DISENCHANTER";
    }

}