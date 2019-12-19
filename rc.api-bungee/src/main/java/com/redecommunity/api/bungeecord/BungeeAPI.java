package com.redecommunity.api.bungeecord;

import com.redecommunity.api.bungeecord.manager.StartManager;
import com.redecommunity.api.bungeecord.reflection.Reflection;

import java.io.File;

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

        new StartManager();
    }

    @Override
    public void onDisablePlugin() {

    }

    @Override
    public File getAbsoluteFile() {
        return BungeeAPI.this.getFile();
    }

    public static BungeeAPI getInstance() {
        return BungeeAPI.instance;
    }

    public Reflection getReflection() {
        return this.reflection;
    }
}
