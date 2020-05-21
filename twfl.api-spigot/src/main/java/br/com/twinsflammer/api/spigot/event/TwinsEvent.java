package br.com.twinsflammer.api.spigot.event;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by @SrGutyerrez
 */
public class TwinsEvent extends Event {
    public static HandlerList handler = new HandlerList();

    public static HandlerList getHandlerList() {
        return TwinsEvent.handler;
    }

    @Override
    public HandlerList getHandlers() {
        return TwinsEvent.handler;
    }

    public void run() {
        Bukkit.getPluginManager().callEvent(this);
    }
}
