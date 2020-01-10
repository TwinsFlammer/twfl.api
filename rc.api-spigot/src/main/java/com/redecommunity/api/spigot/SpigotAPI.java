package com.redecommunity.api.spigot;

import com.redecommunity.api.shared.API;
import com.redecommunity.api.spigot.manager.StartManager;
import com.redecommunity.api.spigot.reflection.Reflection;

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

    public Reflection getReflection() {
        return this.reflection;
    }

    public API getApi() {
        return this.api;
    }
}
