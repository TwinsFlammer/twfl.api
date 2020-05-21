package br.com.twinsflammer.api.spigot.teleport.event;

import br.com.twinsflammer.api.spigot.teleport.data.TeleportRequest;
import br.com.twinsflammer.api.spigot.event.TwinsEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.Cancellable;

/**
 * Created by @SrGutyerrez
 */
@RequiredArgsConstructor
public class UserTeleportEvent extends TwinsEvent implements Cancellable {
    @Getter
    private final TeleportRequest teleportRequest;

    private Boolean cancelled = false;

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
