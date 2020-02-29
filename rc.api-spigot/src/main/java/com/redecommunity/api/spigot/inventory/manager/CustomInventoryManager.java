package com.redecommunity.api.spigot.inventory.manager;

import com.google.common.collect.Lists;
import com.redecommunity.api.spigot.inventory.CustomInventory;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Created by @SrGutyerrez
 */
public class CustomInventoryManager {
    private static List<CustomInventory> inventories = Lists.newArrayList();

    public static void addCustomInventory(CustomInventory customInventory) {
        CustomInventoryManager.inventories.add(customInventory);
    }

    public static CustomInventory getCustomInventory(Player player) {
        return CustomInventoryManager.inventories
                .stream()
                .filter(customInventory ->
                        customInventory.getViewers()
                                .stream()
                                .anyMatch(humanEntity -> humanEntity.getUniqueId().equals(player.getUniqueId()))
                )
                .findFirst()
                .orElse(null);
    }
}
