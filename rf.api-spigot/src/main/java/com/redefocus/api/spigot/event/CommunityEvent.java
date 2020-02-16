package com.redefocus.api.spigot.event;

import com.redefocus.api.spigot.teleport.event.UserTeleportEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by @SrGutyerrez
 */
public class CommunityEvent extends Event {
    public static HandlerList handler = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return UserTeleportEvent.handler;
    }

    public void run() {
        Bukkit.getPluginManager().callEvent(this);
    }
}
