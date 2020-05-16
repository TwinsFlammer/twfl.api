package com.redefocus.api.spigot.listeners.general;

import com.google.common.collect.Lists;
import com.redefocus.api.spigot.SpigotAPI;
import com.redefocus.api.spigot.user.data.SpigotUser;
import com.redefocus.common.shared.permissions.user.group.dao.UserGroupDao;
import com.redefocus.common.shared.permissions.user.group.data.UserGroup;
import com.redefocus.common.shared.server.data.Server;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.util.List;
import java.util.UUID;

/**
 * Created by @SrGutyerrez
 */
public class AsyncPlayerPreLoginListener implements Listener {
    @EventHandler
    public void onLogin(AsyncPlayerPreLoginEvent event) {
        UUID uniqueId = event.getUniqueId();

        SpigotUser spigotUser = SpigotAPI.getSpigotUserFactory().getUser(uniqueId);

        UserGroupDao userGroupDao = new UserGroupDao();

        Server server = SpigotAPI.getCurrentServer();

        if (server == null || server.isLoginServer()) return;

        String serverIdSQLWhere = server.isLobby() ? "" : "AND `server_id`=0 OR `server_id`=" + SpigotAPI.getRootServerId();

        System.out.println(serverIdSQLWhere);

        List<UserGroup> groups = Lists.newArrayList(
                userGroupDao.findAll(spigotUser.getId(), serverIdSQLWhere)
        );

        spigotUser.setGroups(groups);

        spigotUser.applyPermissions();
    }
}
