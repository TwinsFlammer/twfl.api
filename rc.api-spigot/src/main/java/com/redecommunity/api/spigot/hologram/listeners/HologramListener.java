package com.redecommunity.api.spigot.hologram.listeners;

import com.redecommunity.api.spigot.hologram.HologramProtocol;
import com.redecommunity.api.spigot.hologram.line.AbstractHologramLine;
import com.redecommunity.api.spigot.util.EntityUtil;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by @SrGutyerrez
 */
public class HologramListener implements Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();

        if (action.equals(Action.LEFT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_AIR)) {

            Entity entityLookingAt = EntityUtil.getLookingAt(player, EntityType.ARMOR_STAND);
            if (entityLookingAt != null) {
                AbstractHologramLine hologramLine = HologramProtocol.getHologramLine(entityLookingAt);
                if (hologramLine != null) {
                    hologramLine.getParent().dispatchTouch(player);
                }
            }
        }
    }
}
