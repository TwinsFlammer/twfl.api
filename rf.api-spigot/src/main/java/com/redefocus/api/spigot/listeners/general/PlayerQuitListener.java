package com.redefocus.api.spigot.listeners.general;

import com.redefocus.api.spigot.SpigotAPI;
import com.redefocus.api.spigot.nametag.manager.NametagManager;
import com.redefocus.common.shared.permissions.user.data.User;
import com.redefocus.common.shared.permissions.user.manager.UserManager;
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

        event.setQuitMessage(null);

        User user = UserManager.getUser(player.getUniqueId());

        SpigotAPI.unloadUser(user);

        NametagManager nametagManager = SpigotAPI.getInstance().getNametagManager();

        nametagManager.reset(player.getName());
    }
}
