package com.redecommunity.api.spigot;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by @SrGutyerrez
 */
public class SpigotAPI extends JavaPlugin {
    private static SpigotAPI instance;

    public SpigotAPI() {
        SpigotAPI.instance = this;
    }

    @Override
    public void onEnable() {
    }

    public static SpigotAPI getInstance() {
        return SpigotAPI.instance;
    }
}
