package com.redefocus.api.spigot.listeners.general;

import com.redefocus.api.spigot.SpigotAPI;
import com.redefocus.api.spigot.user.data.SpigotUser;
import com.redefocus.common.shared.permissions.user.data.User;
import com.redefocus.common.shared.permissions.user.group.dao.UserGroupDao;
import com.redefocus.common.shared.permissions.user.group.data.UserGroup;
import com.redefocus.common.shared.permissions.user.manager.UserManager;
import com.redefocus.common.shared.server.data.Server;
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

        if (server == null || server.isLoginServer()) return;

        String serverIdSQLWhere = server.isLobby() ? "" : "AND `server_id`=0 OR `server_id`=" + SpigotAPI.getRootServerId();

        Set<UserGroup> groups = userGroupDao.findAll(user.getId(), serverIdSQLWhere);

        user.getGroups().addAll(groups);

        SpigotUser spigotUser = SpigotAPI.getSpigotUserFactory().getUser(user.getId());

        spigotUser.applyPermissions();
    }
}
