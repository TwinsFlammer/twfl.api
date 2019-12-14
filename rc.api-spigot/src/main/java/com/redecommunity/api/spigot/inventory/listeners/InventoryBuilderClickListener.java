package com.redecommunity.api.spigot.inventory.listeners;

import com.redecommunity.api.spigot.inventory.InventoryBuilder;
import com.redecommunity.api.spigot.inventory.manager.InventoryBuilderManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

/**
 * Created by @SrGutyerrez
 */
public class InventoryBuilderClickListener implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();

        InventoryBuilder inventoryBuilder = InventoryBuilderManager.getInventoryBuilder(inventory);

        inventoryBuilder.onClick(event);
    }
}
