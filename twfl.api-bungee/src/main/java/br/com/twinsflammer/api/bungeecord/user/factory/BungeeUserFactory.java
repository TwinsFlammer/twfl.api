package br.com.twinsflammer.api.bungeecord.user.factory;

import br.com.twinsflammer.api.bungeecord.user.data.BungeeUser;
import br.com.twinsflammer.common.shared.permissions.user.factory.AbstractUserFactory;
import br.com.twinsflammer.common.shared.permissions.user.manager.UserManager;

import java.util.UUID;

/**
 * Created by @SrGutyerrez
 */
public class BungeeUserFactory<U extends BungeeUser> extends AbstractUserFactory<U> {
    @Override
    public U getUser(Integer id) {
        BungeeUser bungeeUser = new BungeeUser(
                UserManager.getUser(id)
        );

        return (U) bungeeUser;
    }

    @Override
    public U getUser(String username) {
        BungeeUser bungeeUser = new BungeeUser(
                UserManager.getUser(username)
        );

        return (U) bungeeUser;
    }

    @Override
    public U getUser(UUID uniqueId) {
        BungeeUser bungeeUser = new BungeeUser(
                UserManager.getUser(uniqueId)
        );

        return (U) bungeeUser;
    }
}
