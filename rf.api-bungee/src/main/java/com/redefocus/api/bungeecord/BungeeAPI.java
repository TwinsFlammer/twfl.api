package com.redefocus.api.bungeecord;

import com.redefocus.api.bungeecord.manager.StartManager;
import com.redefocus.api.bungeecord.reflection.Reflection;
import com.redefocus.api.shared.API;

/**
 * Created by @SrGutyerrez
 */
public class BungeeAPI extends FocusPlugin {
    private static BungeeAPI instance;

    public BungeeAPI() {
        BungeeAPI.instance = this;
    }

    private Reflection reflection;

    private API api;

    @Override
    public void onEnablePlugin() {
        this.reflection = new Reflection();

        new StartManager();

        this.api = new API();
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

    public API getApi() {
        return this.api;
    }
}
