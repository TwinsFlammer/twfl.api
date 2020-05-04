package com.redefocus.api.spigot.teleport.listeners;

import com.redefocus.api.spigot.teleport.data.TeleportRequest;
import com.redefocus.api.spigot.teleport.manager.TeleportRequestManager;
import com.redefocus.common.shared.permissions.user.data.User;
import com.redefocus.common.shared.permissions.user.manager.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created by @SrGutyerrez
 */
public class PlayerJoinWithTeleportRequestListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        User user = UserManager.getUser(player.getUniqueId());

        TeleportRequest teleportRequest = TeleportRequestManager.getTeleportRequest(user);

        if (teleportRequest == null) return;

        Location location = teleportRequest.getTargetLocation();

        if (location != null) {
            player.teleport(location);
            return;
        }

        User user1 = UserManager.getUser(teleportRequest.getTargetId());
        Player player1 = Bukkit.getPlayer(user1.getUniqueId());

        if (player1 != null) player.teleport(player1);

        TeleportRequestManager.getWaitingJoin().remove(teleportRequest);
    }
}
