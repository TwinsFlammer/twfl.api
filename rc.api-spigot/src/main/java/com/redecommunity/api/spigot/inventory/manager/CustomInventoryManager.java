package com.redecommunity.api.spigot.inventory.manager;

import com.google.common.collect.Lists;
import com.redecommunity.api.spigot.inventory.CustomInventory;
import org.bukkit.entity.Player;
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

    public static CustomInventory getCustomInventory(Player player) {
        CustomInventoryManager.inventories.forEach(customInventory -> {
            System.out.println(customInventory == null);
            System.out.println(customInventory.getInventory() == null);

            System.out.println(customInventory.getInventory().getName());

            System.out.println(customInventory.getViewers().contains(player));

            System.out.println(">>>" + customInventory.getViewers().stream().anyMatch(humanEntity -> humanEntity.getUniqueId().equals(player.getUniqueId())));

            System.out.println(customInventory.getViewers().stream().anyMatch(humanEntity -> {
                Player player1 = (Player) humanEntity;

                return player1.getUniqueId().equals(player.getUniqueId());
            }));
        });

        return CustomInventoryManager.inventories
                .stream()
                .filter(Objects::nonNull)
                .filter(customInventory ->
                        customInventory.getViewers()
                                .stream()
                                .anyMatch(humanEntity -> humanEntity.getUniqueId().equals(player.getUniqueId()))
                )
                .findFirst()
                .orElse(null);
    }
}
