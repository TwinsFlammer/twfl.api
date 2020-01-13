package com.redecommunity.api.spigot.inventory;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.redecommunity.api.spigot.inventory.item.CustomItem;
import com.redecommunity.api.spigot.inventory.manager.CustomInventoryManager;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.server.v1_8_R3.*;
import org.apache.commons.lang3.Validate;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftHumanEntity;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventory;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Created by @SrGutyerrez
 */
public class CustomInventory extends CraftInventory {
    @Setter
    private Boolean cancelled = false;
    private HashMap<Integer, CustomItem> customItems = Maps.newHashMap();

    private Consumer<InventoryCloseEvent> closeEventConsumer;

    private HashMap<Integer, Character> design;

    public CustomInventory(String name, Integer rows) {
        super(
                new MinecraftInventory(
                        rows * 9,
                        name
                )
        );

        CustomInventoryManager.addCustomInventory(this);
    }

    public CustomInventory(String name, InventoryType inventoryType) {
        super(
                new MinecraftInventory(
                        inventoryType,
                        name
                )
        );

        CustomInventoryManager.addCustomInventory(this);
    }

    public CustomInventory setItem(int index, CustomItem customItem) {
        super.setItem(
                index,
                customItem.build()
        );

        this.customItems.put(index, customItem);

        return this;
    }

    public CustomInventory addItem(CustomItem customItem) {
        for (int i = 0; i < this.getSize(); i++)
            if (this.getContents()[i] == null || this.getContents()[i].getType() == Material.AIR) {
                this.setItem(i, customItem);
                break;
            }

        return this;
    }

    public CustomInventory setDesign(String... design) {
        this.design = Maps.newHashMap();

        Integer slot = 0;

        for (String design1 : design) {
            char[] chars = design1.toCharArray();

            for (int i = 0; i < chars.length; i++) {
                char char1 = chars[i];

                this.design.put(slot, char1);

                slot++;
            }
        }

//        Integer slot = 0;
//
//        for (int i = 0; i < design.length; i++) {
//            String design1 = design[i];
//
//            char[] chars = design1.toCharArray();
//
//            for (int j = 0; j < chars.length; j++) {
//                char char1 = chars[j];
//
//                if (char1 != 'X') this.design.put(
//                        slot,
//                        char1
//                );
//
//                slot++;
//            }
//        }

        this.organize();

        return this;
    }

    private void organize() {
        if (this.design == null) return;

        HashMap<Integer, CustomItem> customItems = Maps.newHashMap();

        Integer index = 0;

        for (Map.Entry<Integer, Character> entrySet : this.design.entrySet()) {
            Integer slot = entrySet.getKey();
            Character character = entrySet.getValue();

            if (character != 'O') continue;

            System.out.println(slot);


            System.out.println(index);

            CustomItem customItem = this.customItems.get(index);

            System.out.println(customItem == null);

            if (customItem != null && customItem.isEditable()) {
                super.setItem(index, null);

                customItems.put(
                        slot,
                        customItem
                );
            }

            index++;
        }

        customItems.forEach(this::setItem);
    }

    public void onClick(InventoryClickEvent event) {
        if (event.getClick() == ClickType.NUMBER_KEY) event.setCancelled(this.cancelled);
        event.setCancelled(this.cancelled);

        Integer slot = event.getSlot();

        CustomItem customItem = this.customItems.get(slot);

        if (customItem != null && customItem.getInventoryClickEventConsumer() != null)
            customItem.getInventoryClickEventConsumer().accept(event);
    }

    public void onClose(InventoryCloseEvent event) {
        if (this.closeEventConsumer != null) this.closeEventConsumer.accept(event);
    }

    static class MinecraftInventory implements IInventory {

        private final ItemStack[] items;

        @Setter
        @Getter
        private int maxStackSize = 64;

        @Getter
        private final List<HumanEntity> viewers;

        @Getter
        private final String name;

        @Getter
        private final InventoryType type;

        public MinecraftInventory(int size, String title) {
            Validate.notNull(title, "Title cannot be null");

            this.items = new ItemStack[size];
            this.name = title;
            this.viewers = Lists.newArrayList();
            this.type = InventoryType.CHEST;
        }

        public MinecraftInventory(InventoryType type, String title) {
            Validate.notNull(title, "Title cannot be null");

            this.items = new ItemStack[type.getDefaultSize()];
            this.name = title;
            this.viewers = Lists.newArrayList();
            this.type = type;
        }

        @Override
        public int getSize() {
            return items.length;
        }

        @Override
        public ItemStack getItem(int i) {
            return items[i];
        }

        @Override
        public ItemStack splitStack(int i, int j) {
            ItemStack stack = this.getItem(i);
            ItemStack result;
            if (stack == null) {
                return null;
            }
            if (stack.count <= j) {
                this.setItem(i, null);
                result = stack;
            } else {
                result = CraftItemStack.copyNMSStack(stack, j);
                stack.count -= j;
            }
            this.update();
            return result;
        }

        @Override
        public ItemStack splitWithoutUpdate(int i) {
            ItemStack stack = this.getItem(i);
            ItemStack result;
            if (stack == null) {
                return null;
            }
            if (stack.count <= 1) {
                this.setItem(i, null);
                result = stack;
            } else {
                result = CraftItemStack.copyNMSStack(stack, 1);
                stack.count -= 1;
            }
            return result;
        }

        @Override
        public void setItem(int i, ItemStack itemstack) {
            items[i] = itemstack;
            if (itemstack != null && this.getMaxStackSize() > 0 && itemstack.count > this.getMaxStackSize()) {
                itemstack.count = this.getMaxStackSize();
            }
        }

        @Override
        public void update() {
        }

        @Override
        public boolean a(EntityHuman entityhuman) {
            return true;
        }

        @Override
        public ItemStack[] getContents() {
            return items;
        }

        @Override
        public void onOpen(CraftHumanEntity who) {
            viewers.add(who);
        }

        public void onClose(CraftHumanEntity who) {
            viewers.remove(who);
        }

        @Override
        public InventoryHolder getOwner() {
            return null;
        }

        public boolean b(int i, ItemStack itemstack) {
            return true;
        }

        @Override
        public void startOpen(EntityHuman entityHuman) {

        }

        @Override
        public void closeContainer(EntityHuman entityHuman) {

        }

        @Override
        public int getProperty(int i) {
            return 0;
        }

        @Override
        public void b(int i, int i1) {

        }

        @Override
        public int g() {
            return 0;
        }

        @Override
        public void l() {

        }

        @Override
        public boolean hasCustomName() {
            return name != null;
        }

        @Override
        public IChatBaseComponent getScoreboardDisplayName() {
            return new ChatComponentText(name);
        }
    }
}
