package com.redecommunity.api.spigot.inventory.manager;

import com.google.common.collect.Lists;
import com.redecommunity.api.spigot.inventory.InventoryBuilder;
import lombok.Getter;
import org.bukkit.inventory.Inventory;

import java.util.List;

/**
 * Created by @SrGutyerrez
 */
public class InventoryBuilderManager {
    private static List<InventoryBuilder> inventories = Lists.newArrayList();

    public static InventoryBuilder getInventoryBuilder(Inventory inventory) {
        return InventoryBuilderManager.inventories
                .stream()
                .filter(inventoryBuilder -> inventoryBuilder.getInventory() != null)
                .filter(inventoryBuilder -> inventoryBuilder.getInventory().equals(inventory))
                .findFirst()
                .orElse(null);
    }
}
