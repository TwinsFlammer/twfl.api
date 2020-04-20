package com.redefocus.api.spigot.commands.defaults.tps.manager;

import com.google.common.collect.Lists;
import com.redefocus.api.spigot.SpigotAPI;
import com.redefocus.api.spigot.commands.defaults.tps.runnable.TicksPerSecond;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;

/**
 * Created by @SrGutyerrez
 */
public class TPSManager {
    public static List<Integer> TICKS, TICKS_AVG;

    private static BukkitTask TPS;

    static {
        TICKS = Lists.newArrayListWithCapacity(49);
        TICKS_AVG = Lists.newArrayListWithCapacity(1801);
    }

    public static String getTPSString(double tps) {
        String colored = ((tps >= 18.0) ? "&a" : ((tps >= 16.0) ? "&e" : ((tps >= 5.0) ? "&c" : "&4"))) + tps;
        return (colored.length() > 7) ? colored.substring(0, 7) : colored;
    }

    public static ChatColor getTPSColor(double tps) {
        return tps > 18.0 ? ChatColor.GREEN : tps >= 16.0 ? ChatColor.YELLOW : tps >= 5.0 ? ChatColor.RED : ChatColor.DARK_RED;
    }

    public static String getTPSString() {
        return getTPSString(getTPS());
    }

    public static double getTPS() {
        return TPSManager.TICKS.get(0);
    }

    public static void addTick(int tickCount) {
        TPSManager.TICKS.add(0, tickCount);
        try {
            while (TPSManager.TICKS.size() > 48) {
                TPSManager.TICKS.remove(48);
            }
        } catch (Exception ignored) { }
        TPSManager.TICKS_AVG.add(0, tickCount);
        try {
            while (TPSManager.TICKS_AVG.size() > 1800) {
                TPSManager.TICKS_AVG.remove(1800);
            }
        } catch (Exception ignored) { }
    }

    public static void start() {
        TPSManager.TICKS.clear();

        for (int i = 0; i < 48; ++i) {
            TPSManager.TICKS.add(0);
        }

        TPSManager.TICKS_AVG.clear();

        for (int i = 0; i < 1800; ++i) {
            TPSManager.TICKS_AVG.add(0);
        }

        if (TPSManager.TPS != null) {
            TPSManager.TPS.cancel();
            TPSManager.TPS = null;
        }

        TPSManager.TPS = Bukkit.getScheduler().runTaskTimerAsynchronously(SpigotAPI.getInstance(), new TicksPerSecond(), 0L, 1L);
    }
}
