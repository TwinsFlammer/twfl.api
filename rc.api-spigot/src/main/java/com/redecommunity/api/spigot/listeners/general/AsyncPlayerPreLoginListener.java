package com.redecommunity.api.spigot.listeners.general;

import com.redecommunity.api.spigot.SpigotAPI;
import com.redecommunity.common.shared.permissions.user.data.User;
import com.redecommunity.common.shared.permissions.user.group.dao.UserGroupDao;
import com.redecommunity.common.shared.permissions.user.group.data.UserGroup;
import com.redecommunity.common.shared.permissions.user.manager.UserManager;
import com.redecommunity.common.shared.server.data.Server;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.util.Set;
import java.util.UUID;

/**
 * Created by @SrGutyerrez
 */
public class AsyncPlayerPreLoginListener implements Listener {
    @EventHandler
    public void onLogin(AsyncPlayerPreLoginEvent event) {
        UUID uniqueId = event.getUniqueId();

        User user = UserManager.getUser(uniqueId);

        UserGroupDao userGroupDao = new UserGroupDao();

        Server server = SpigotAPI.getCurrentServer();

        if (server == null) return;

        String serverId = "0 || " + server.getId();

        Set<UserGroup> groups = userGroupDao.findAll(user.getId(), serverId);

        user.setGroups(groups);
    }
}
