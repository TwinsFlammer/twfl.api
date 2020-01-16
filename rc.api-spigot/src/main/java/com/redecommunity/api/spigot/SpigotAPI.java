package com.redecommunity.api.spigot;

import com.redecommunity.api.shared.API;
import com.redecommunity.api.spigot.manager.StartManager;
import com.redecommunity.api.spigot.reflection.Reflection;
import com.redecommunity.common.shared.permissions.user.data.User;
import com.redecommunity.common.shared.permissions.user.manager.UserManager;
import com.redecommunity.common.shared.server.data.Server;
import com.redecommunity.common.shared.server.manager.ServerManager;
import org.bukkit.Bukkit;

/**
 * Created by @SrGutyerrez
 */
public class SpigotAPI extends CommunityPlugin {
    private static SpigotAPI instance;

    public SpigotAPI() {
        SpigotAPI.instance = this;
    }

    private Reflection reflection;

    private API api;

    @Override
    public void onEnablePlugin() {
        this.reflection = new Reflection(this);

        new StartManager();

        this.api = new API();
    }

    @Override
    public void onDisablePlugin() {

    }

    public static SpigotAPI getInstance() {
        return SpigotAPI.instance;
    }

    public static Server getCurrentServer() {
        String address = Bukkit.getIp();
        Integer port = Bukkit.getPort();

        return ServerManager.getServer(address, port);
    }

    public Reflection getReflection() {
        return this.reflection;
    }

    public API getApi() {
        return this.api;
    }

    public static void unloadUser(User user) {
        UserManager.removeUser(user.getId());
    }
}
