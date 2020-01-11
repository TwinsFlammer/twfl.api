package com.redecommunity.api.spigot.inventory.item;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
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
public class CustomItem {
    private final ItemStack itemStack;

    @Getter
    private Consumer<InventoryClickEvent> inventoryClickEventConsumer;

    private Boolean editable = true;

    public CustomItem(Material material) {
        this.itemStack = new ItemStack(material);
    }

    public CustomItem(CustomItem customItem) {
        this.itemStack = new ItemStack(customItem.itemStack);
    }

    public CustomItem type(Material material) {
        this.itemStack.setType(material);

        return this;
    }

    public CustomItem name(String name) {
        ItemMeta itemMeta = this.meta();
        itemMeta.setDisplayName(name);

        this.setItemMeta(itemMeta);

        return this;
    }

    public CustomItem lore(String... lore) {
        return this.lore(Arrays.asList(lore));
    }

    public CustomItem lore(List<String> lore) {
        ItemMeta itemMeta = this.meta();
        itemMeta.setLore(lore);

        this.setItemMeta(itemMeta);

        return this;
    }

    public CustomItem hideAttributes() {
        ItemMeta itemMeta = this.meta();

        for (ItemFlag itemFlag : ItemFlag.values())
            itemMeta.addItemFlags(itemFlag);

        this.setItemMeta(itemMeta);

        this.setItemMeta(itemMeta);

        return this;
    }

    public CustomItem data(Integer data) {
        return this.data(data.shortValue());
    }

    public CustomItem data(Short data) {
        this.itemStack.setDurability(data);

        return this;
    }

    public CustomItem amount(Integer amount) {
        this.itemStack.setAmount(amount);

        return this;
    }

    public CustomItem enchant(Enchantment enchantment, Integer level) {
        this.itemStack.addUnsafeEnchantment(enchantment, level);

        return this;
    }

    public CustomItem owner(String owner) {
        SkullMeta skullMeta = (SkullMeta) this.meta();

        skullMeta.setOwner(owner);

        this.setItemMeta(skullMeta);

        return this;

    }

    public CustomItem editable(Boolean editable) {
        this.editable = editable;

        return this;
    }

    public CustomItem onClick(Consumer<InventoryClickEvent> inventoryClickEventConsumer) {
        this.inventoryClickEventConsumer = inventoryClickEventConsumer;

        return this;
    }

    public CustomItem clone() {
        return new CustomItem(this);
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
