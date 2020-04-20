package com.redefocus.api.spigot.listeners.general;

import com.redefocus.api.spigot.nametag.manager.NametagManager;
import com.redefocus.api.spigot.SpigotAPI;
import com.redefocus.common.shared.permissions.group.data.Group;
import com.redefocus.common.shared.permissions.user.data.User;
import com.redefocus.common.shared.permissions.user.manager.UserManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.permissions.PermissionAttachment;

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
                group.getColor() + group.getPrefix(),
                group.getSuffix(),
                group.getTabListListOrder()
        );

        player.getEffectivePermissions().clear();

        PermissionAttachment permissionAttachment = player.addAttachment(SpigotAPI.getInstance());

        group.getPermissions().forEach(permission -> {
            String name = permission.getName();

            permissionAttachment.setPermission(
                    name.replaceAll("-", ""),
                    !name.startsWith("-")
            );
        });
    }
}
