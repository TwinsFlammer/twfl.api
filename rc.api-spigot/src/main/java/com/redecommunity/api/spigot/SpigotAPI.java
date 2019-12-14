package com.redecommunity.api.spigot;

import com.redecommunity.api.spigot.reflection.Reflection;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by @SrGutyerrez
 */
public class SpigotAPI extends JavaPlugin {
    private static SpigotAPI instance;

    private Reflection reflection;

    public SpigotAPI() {
        SpigotAPI.instance = this;
    }

    @Override
    public void onEnable() {
        this.reflection = new Reflection(this);

    }

    public static SpigotAPI getInstance() {
        return SpigotAPI.instance;
    }

    public Reflection getReflection() {
        return this.reflection;
    }
}
