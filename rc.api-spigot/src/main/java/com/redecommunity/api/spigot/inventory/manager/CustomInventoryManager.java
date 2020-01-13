package com.redecommunity.api.spigot.inventory.manager;

import com.google.common.collect.Lists;
import com.redecommunity.api.spigot.inventory.CustomInventory;
import org.bukkit.inventory.Inventory;

import java.util.List;
import java.util.Objects;

/**
 * Created by @SrGutyerrez
 */
public class CustomInventoryManager {
    private static List<CustomInventory> inventories = Lists.newArrayList();

    public static void addCustomInventory(CustomInventory customInventory) {
        CustomInventoryManager.inventories.add(customInventory);
    }

    public static CustomInventory getCustomInventory(Inventory inventory) {
        CustomInventoryManager.inventories.forEach(customInventory -> {
            System.out.println(customInventory == null);
            System.out.println(customInventory.getInventory() == null);
            System.out.println(customInventory.getInventory().equals(inventory));
        });

        return CustomInventoryManager.inventories
                .stream()
                .filter(Objects::nonNull)
                .filter(customInventory -> customInventory.getInventory() != null)
                .filter(customInventory -> customInventory.getInventory().equals(inventory))
                .findFirst()
                .orElse(null);
    }
}
