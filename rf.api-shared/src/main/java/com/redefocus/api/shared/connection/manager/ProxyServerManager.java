package com.redefocus.api.shared.connection.manager;

import com.google.common.collect.Lists;
import com.redefocus.api.shared.connection.dao.ProxyServerDao;
import com.redefocus.api.shared.connection.data.ProxyServer;
import com.redefocus.common.shared.Common;
import com.redefocus.common.shared.permissions.user.data.User;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by @SrGutyerrez
 */
public class ProxyServerManager {
    private static List<ProxyServer> proxies = Lists.newArrayList();

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
                    Lists.newArrayList()
            );

            proxyServerDao.insert(proxyServer);

            ProxyServerManager.proxies.add(proxyServer);
        }

        Common.getInstance().getScheduler().scheduleWithFixedDelay(
                () -> {
                    proxyServerDao.findAll().forEach(proxyServer -> {
                        ProxyServer proxyServer1 = ProxyServerManager.getProxyServer(proxyServer.getId());

                        if (proxyServer1 != null) proxyServer1.setUsers(proxyServer.getUsers());
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

        ProxyServerManager.proxies.forEach(proxyServer -> users.addAll(proxyServer.getUsers()));

        return users;
    }
}
