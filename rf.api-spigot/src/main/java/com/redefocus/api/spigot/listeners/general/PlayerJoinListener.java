package com.redefocus.api.spigot.listeners.general;

import com.redefocus.api.spigot.SpigotAPI;
import com.redefocus.api.spigot.nametag.manager.NametagManager;
import com.redefocus.common.shared.permissions.group.data.Group;
import com.redefocus.common.shared.permissions.user.data.User;
import com.redefocus.common.shared.permissions.user.manager.UserManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created by @SrGutyerrez
 */
public class PlayerJoinListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        event.setJoinMessage(null);

        User user = UserManager.getUser(player.getUniqueId());
        Group group = user.getHighestGroup();

        NametagManager nametagManager = SpigotAPI.getInstance().getNametagManager();

        nametagManager.sendTeams(player);

        nametagManager.setNametag(
                player.getName(),
                group.getPrefix(),
                group.getSuffix(),
                group.getTabListListOrder()
        );
    }
}
