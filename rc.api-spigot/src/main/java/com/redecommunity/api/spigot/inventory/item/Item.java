package com.redecommunity.api.spigot.inventory.item;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by @SrGutyerrez
 */
@RequiredArgsConstructor
public class Item {
    private final ItemStack itemStack;

    @Getter
    private Consumer<InventoryClickEvent> inventoryClickEventConsumer;
    @Setter
    private Boolean editable = false;

    public Item(Material material) {
        this.itemStack = new ItemStack(material);
    }

    public Item(Item item) {
        this.itemStack = new ItemStack(item.itemStack);
    }

    public Item type(Material material) {
        this.itemStack.setType(material);

        return this;
    }

    public Item name(String name) {
        ItemMeta itemMeta = this.meta();
        itemMeta.setDisplayName(name);

        return this;
    }

    public Item lore(String... lore) {
        return this.lore(Arrays.asList(lore));
    }

    public Item lore(List<String> lore) {
        ItemMeta itemMeta = this.meta();
        itemMeta.setLore(lore);

        return this;
    }

    public Item hideAttributes() {
        ItemMeta itemMeta = this.meta();

        for (ItemFlag itemFlag : ItemFlag.values())
            itemMeta.addItemFlags(itemFlag);

        this.setItemMeta(itemMeta);

        return this;
    }

    public Item data(Integer data) {
        return this.data(data.shortValue());
    }

    public Item data(Short data) {
        this.itemStack.setDurability(data);

        return this;
    }

    public Item amount(Integer amount) {
        this.itemStack.setAmount(amount);

        return this;
    }

    public Item enchant(Enchantment enchantment, Integer level) {
        this.itemStack.addUnsafeEnchantment(enchantment, level);

        return this;
    }

    public Item owner(String owner) {
        SkullMeta skullMeta = (SkullMeta) this.meta();

        skullMeta.setOwner(owner);

        this.setItemMeta(skullMeta);

        return this;
    }

    public Item onClick(Consumer<InventoryClickEvent> inventoryClickEventConsumer) {
        this.inventoryClickEventConsumer = inventoryClickEventConsumer;

        return this;
    }

    public Item clone() {
        return new Item(this);
    }

    public ItemStack build() {
        return this.itemStack;
    }

    public Boolean isEditable() {
        return this.editable;
    }

    private ItemMeta meta() {
        return this.itemStack.getItemMeta();
    }

    private boolean setItemMeta(ItemMeta itemMeta) {
        return this.itemStack.setItemMeta(itemMeta);
    }
}
