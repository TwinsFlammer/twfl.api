package com.redecommunity.api.spigot;

import com.redecommunity.api.spigot.reflection.Reflection;

/**
 * Created by @SrGutyerrez
 */
public class SpigotAPI extends CommunityPlugin {
    private static SpigotAPI instance;

    private Reflection reflection;

    public SpigotAPI() {
        SpigotAPI.instance = this;
    }

    @Override
    public void onEnablePlugin() {
        this.reflection = new Reflection(this);
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
}
