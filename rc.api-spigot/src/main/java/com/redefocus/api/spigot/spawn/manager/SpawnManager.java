package com.redefocus.api.spigot.spawn.manager;

import com.redefocus.api.spigot.spawn.storage.SpawnStorage;
import org.bukkit.Location;

/**
 * Created by @SrGutyerrez
 */
public class SpawnManager {
    public static Location DEFAULT_SPAWN;

    public SpawnManager() {
        SpawnStorage spawnStorage = new SpawnStorage();

        SpawnManager.DEFAULT_SPAWN = spawnStorage.findOne();
    }
}
