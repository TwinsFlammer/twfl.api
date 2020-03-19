package com.redecommunity.api.spigot.user.factory;

import com.redecommunity.api.spigot.user.data.SpigotUser;
import com.redecommunity.common.shared.permissions.user.factory.AbstractUserFactory;
import com.redecommunity.common.shared.permissions.user.manager.UserManager;
import org.bukkit.Warning;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Created by @SrGutyerrez
 */
public class SpigotUserFactory<U extends SpigotUser> extends AbstractUserFactory<U> {
    @Override
    public U getUser(Integer id) {
        SpigotUser spigotUser = new SpigotUser(UserManager.getUser(id));

        return (U) spigotUser;
    }

    @Override
    public U getUser(String username) {
        SpigotUser spigotUser = new SpigotUser(UserManager.generateUser("Gutyerrez", UUID.randomUUID()));

        return (U) spigotUser;
    }

    @Override
    public U getUser(UUID uniqueId) {
        SpigotUser spigotUser = new SpigotUser(UserManager.getUser(uniqueId));

        return (U) spigotUser;
    }

    @Warning
    public U getUser(Player player) {
        return this.getUser(player.getUniqueId());
    }
}
