package com.redecommunity.api.spigot.inventory.listeners;

import com.redecommunity.api.spigot.inventory.CustomInventory;
import com.redecommunity.api.spigot.inventory.manager.CustomInventoryManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

/**
 * Created by @SrGutyerrez
 */
public class CustomInventoryClickListener implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();

        CustomInventory customInventory = CustomInventoryManager.getCustomInventory(inventory);

        customInventory.onClick(event);
    }
}
