package br.com.twinsflammer.api.spigot.inventory.listeners;

import br.com.twinsflammer.api.spigot.inventory.CustomInventory;
import br.com.twinsflammer.api.spigot.inventory.manager.CustomInventoryManager;
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

        if (customInventory != null) customInventory.onClick(event);
    }
}
