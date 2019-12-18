package com.redecommunity.api.bungeecord.user;

import com.redecommunity.common.shared.permissions.group.data.Group;
import com.redecommunity.common.shared.permissions.user.data.User;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Collection;
import java.util.UUID;

/**
 * Created by @SrGutyerrez
 */
public class ProxyUser extends User {
    public ProxyUser(Integer id, String name, String displayName, UUID uniqueId, String email, Long discordId, Long createdAt, Long firstLogin, Long lastLogin, String lastAddress, Integer lastLobbyId, Integer languageId, Collection<Group> groups) {
        super(id, name, displayName, uniqueId, email, discordId, createdAt, firstLogin, lastLogin, lastAddress, lastLobbyId, languageId, groups);
    }

    public ProxiedPlayer getProxiedPlayer() {
        return ProxyServer.getInstance().getPlayer(this.uniqueId);
    }
}
