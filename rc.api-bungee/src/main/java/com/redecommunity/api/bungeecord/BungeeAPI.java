package com.redecommunity.api.bungeecord;

import com.redecommunity.api.bungeecord.commands.manager.CommandManager;
import com.redecommunity.api.bungeecord.reflection.Reflection;

/**
 * Created by @SrGutyerrez
 */
public class BungeeAPI extends CommunityPlugin {
    private static BungeeAPI instance;

    public BungeeAPI() {
        BungeeAPI.instance = this;
    }

    private Reflection reflection;

    @Override
    public void onEnablePlugin() {
        this.reflection = new Reflection();

        new CommandManager();
    }

    @Override
    public void onDisablePlugin() {

    }

    public static BungeeAPI getInstance() {
        return BungeeAPI.instance;
    }

    public Reflection getReflection() {
        return this.reflection;
    }
}
