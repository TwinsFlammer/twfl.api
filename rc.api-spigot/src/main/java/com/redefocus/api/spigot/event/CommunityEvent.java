package com.redefocus.api.spigot.event;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by @SrGutyerrez
 */
public class CommunityEvent extends Event {
    public static HandlerList handler = new HandlerList();

    public static HandlerList getHandlerList() {
        return CommunityEvent.handler;
    }

    @Override
    public HandlerList getHandlers() {
        return CommunityEvent.handler;
    }

    public void run() {
        Bukkit.getPluginManager().callEvent(this);
    }
}
