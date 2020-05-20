package br.com.twinsflammer.api.spigot.inventory.manager;

import br.com.twinsflammer.api.spigot.inventory.CustomInventory;
import com.google.common.collect.Lists;
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
