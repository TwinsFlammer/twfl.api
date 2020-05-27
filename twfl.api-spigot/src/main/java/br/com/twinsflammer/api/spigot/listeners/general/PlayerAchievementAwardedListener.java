package br.com.twinsflammer.api.spigot.listeners.general;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;

/**
 * @author SrGutyerrez
 */
public class PlayerAchievementAwardedListener implements Listener {
    @EventHandler
    public void onAward(PlayerAchievementAwardedEvent event) {
        event.setCancelled(true);
    }
}
