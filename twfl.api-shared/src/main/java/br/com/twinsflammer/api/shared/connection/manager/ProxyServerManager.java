package br.com.twinsflammer.api.shared.connection.manager;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import br.com.twinsflammer.api.shared.connection.data.ProxyServer;
import br.com.twinsflammer.api.shared.connection.dao.ProxyServerDao;
import br.com.twinsflammer.common.shared.Common;
import br.com.twinsflammer.common.shared.permissions.user.dao.UserDao;
import br.com.twinsflammer.common.shared.permissions.user.data.User;
import br.com.twinsflammer.common.shared.permissions.user.group.dao.UserGroupDao;
import br.com.twinsflammer.common.shared.permissions.user.group.data.UserGroup;
import br.com.twinsflammer.common.shared.permissions.user.manager.UserManager;
import br.com.twinsflammer.common.shared.server.data.Server;
import br.com.twinsflammer.common.shared.server.manager.ServerManager;
import lombok.Setter;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by @SrGutyerrez
 */
public class ProxyServerManager {
    private static List<ProxyServer> proxies = Lists.newArrayList();

    @Setter
    private static Integer serverId = 0;

    public static List<ProxyServer> getProxies() {
        return ProxyServerManager.proxies;
    }

    public ProxyServerManager(Integer proxyId, String proxyName) {
        ProxyServerDao proxyServerDao = new ProxyServerDao();

        if (proxyId != null && proxyName != null) {
            ProxyServer proxyServer = new ProxyServer(
                    proxyId,
                    proxyName,
                    true,
                    Sets.newHashSet()
            );

            proxyServerDao.insert(proxyServer);

            ProxyServerManager.proxies.add(proxyServer);
        }

        Common.getInstance().getScheduler().scheduleWithFixedDelay(
                () -> {
                    proxyServerDao.findAll().forEach(proxyServer -> {
                        ProxyServer proxyServer1 = ProxyServerManager.getProxyServer(proxyServer.getId());

                        if (proxyServer1 != null) proxyServer1.setUsersId(proxyServer.getUsersId());
                        else ProxyServerManager.addProxyServer(proxyServer);
                    });
                },
                0,
                1,
                TimeUnit.SECONDS
        );
    }

    public static Integer getProxyCount() {
        return ProxyServerManager.proxies.size();
    }

    public static Integer getProxyCountOnline() {
        return (int) ProxyServerManager.proxies
                .stream()
                .filter(ProxyServer::isOnline)
                .count();
    }

    public static ProxyServer getProxyServer(Integer id) {
        return ProxyServerManager.proxies
                .stream()
                .filter(proxyServer -> proxyServer.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public static ProxyServer getProxyServer(String name) {
        return ProxyServerManager.proxies
                .stream()
                .filter(proxyServer -> proxyServer.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public static Boolean addProxyServer(ProxyServer proxyServer) {
        return ProxyServerManager.proxies.add(proxyServer);
    }

    public static List<User> getUsers() {
        List<User> users = Lists.newArrayList();

        UserGroupDao userGroupDao = new UserGroupDao();

        Server server = ServerManager.getServer(ProxyServerManager.serverId);

        ProxyServerManager.proxies.forEach(proxyServer -> proxyServer.getUsersId().forEach(userId -> {
            User user = UserManager.getUser(userId);

            if (user != null) {
                List<UserGroup> groups = Lists.newArrayList(
                        userGroupDao.findAll(
                                user.getId(),
                                server == null || server.isLobby() || server.isLoginServer() ? "" : "AND `server_id`=0 OR `server_id`=" + ProxyServerManager.serverId
                        )
                );

                user.setGroups(groups);

                users.add(user);
            }
        }));

        return users;
    }
}
