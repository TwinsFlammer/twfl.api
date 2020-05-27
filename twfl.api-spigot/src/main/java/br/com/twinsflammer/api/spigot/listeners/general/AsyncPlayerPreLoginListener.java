package br.com.twinsflammer.api.spigot.listeners.general;

import br.com.twinsflammer.api.spigot.SpigotAPI;
import com.google.common.collect.Lists;
import br.com.twinsflammer.api.spigot.user.data.SpigotUser;
import br.com.twinsflammer.common.shared.permissions.user.data.User;
import br.com.twinsflammer.common.shared.permissions.user.group.dao.UserGroupDao;
import br.com.twinsflammer.common.shared.permissions.user.group.data.UserGroup;
import br.com.twinsflammer.common.shared.permissions.user.manager.UserManager;
import br.com.twinsflammer.common.shared.server.data.Server;
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

        Long currentTime = System.currentTimeMillis();

        String serverIdSQLWhere = server.isLobby() ? "" : String.format(
                "AND ( `server_id`=0 OR `server_id`=%d ) AND (`end_time` > %d || `end_time`=-1)",
                SpigotAPI.getRootServerId(),
                currentTime
        );

        List<UserGroup> groups = Lists.newArrayList(
                userGroupDao.findAll(spigotUser.getId(), serverIdSQLWhere)
        );

        User user = UserManager.getUser(spigotUser.getUniqueId());

        user.setGroups(groups);

        spigotUser.applyPermissions();
    }
}
