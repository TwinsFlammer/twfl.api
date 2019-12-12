package com.redecommunity.api.bungeecord;

import net.md_5.bungee.api.plugin.Plugin;

/**
 * Created by @SrGutyerrez
 */
public class BungeeAPI extends Plugin {
    private static BungeeAPI instance;

    public BungeeAPI() {
        BungeeAPI.instance = this;
    }

    @Override
    public void onEnable() {
    }

    public static BungeeAPI getInstance() {
        return BungeeAPI.instance;
    }
}
