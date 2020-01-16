package com.redecommunity.api.spigot.teleport.event;

import com.redecommunity.api.spigot.event.CommunityEvent;
import com.redecommunity.api.spigot.teleport.data.TeleportRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.Cancellable;

/**
 * Created by @SrGutyerrez
 */
@RequiredArgsConstructor
public class UserTeleportEvent extends CommunityEvent implements Cancellable {
    @Getter
    private final TeleportRequest teleportRequest;

    private Boolean cancelled;

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
