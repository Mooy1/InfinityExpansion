package io.github.mooy1.infinityexpansion.utils;

import lombok.Getter;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

/**
 * A utility class for testing if items fit a 'filter' for each other
 * 
 * @author Mooy1
 * 
 */
@Getter
public class ItemFilter {

    @Nullable
    private final Material material;
    @Nullable
    private final String id;
    private final int amount;
    @Nullable
    private final ItemStack item;

    public ItemFilter(@Nonnull SlimefunItemStack stack) {
        this.material = null;
        this.id = stack.getItemId();
        this.amount = stack.getAmount();
        this.item = stack;
    }

    public ItemFilter(@Nonnull SlimefunItem item) {
        this.material = null;
        this.id = item.getId();
        this.amount = 1;
        this.item = item.getItem();
    }

    public ItemFilter(@Nonnull ItemStack stack) {
        String id = StackUtils.getItemID(stack, false);
        if (id != null) {
            this.material = null;
            this.id = id;
        } else {
            this.material = stack.getType();
            this.id = null;
        }
        this.amount = stack.getAmount();
        this.item = stack;
    }

    public ItemFilter(@Nonnull Material material) {
        this.material = material;
        this.id = null;
        this.amount = 1;
        this.item = new ItemStack(material, 1);
    }

    public ItemFilter(@Nonnull String id) {
        this.material = null;
        this.id = id;
        this.amount = 1;
        this.item = Objects.requireNonNull(SlimefunItem.getByID(id)).getItem();
    }

    public boolean matches(@Nullable ItemFilter filter, boolean amount) {
        if (filter == null) {
            return false;
        }
        if ((getMaterial() == null) != (filter.getMaterial() == null)) {
            return false;
        }
        if (getMaterial() != null) {
            return getMaterial() == filter.getMaterial() && (!amount || getAmount() <= filter.getAmount());
        } else {
            return Objects.equals(getId(), filter.getId()) && (!amount || getAmount() <= filter.getAmount());
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ItemFilter)) {
            return false;
        }
        return matches((ItemFilter) obj, false);
    }
}
