package com.redecommunity.api.spigot.inventory.listeners;

import com.redecommunity.api.spigot.inventory.CustomInventory;
import com.redecommunity.api.spigot.inventory.manager.CustomInventoryManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Created by @SrGutyerrez
 */
public class CustomInventoryClickListener implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        CustomInventory customInventory = CustomInventoryManager.getCustomInventory(player);

        System.out.println(customInventory == null);

        if (customInventory != null) customInventory.onClick(event);
    }
}
