package com.redecommunity.api.spigot.listeners.general;

import com.redecommunity.api.spigot.SpigotAPI;
import com.redecommunity.common.shared.permissions.user.data.User;
import com.redecommunity.common.shared.permissions.user.manager.UserManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by @SrGutyerrez
 */
public class PlayerQuitListener implements Listener {
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        User user = UserManager.getUser(player.getUniqueId());

        SpigotAPI.unloadUser(user);
    }
}
