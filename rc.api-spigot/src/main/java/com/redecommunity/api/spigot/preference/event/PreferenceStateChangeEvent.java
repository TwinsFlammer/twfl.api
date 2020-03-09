package com.redecommunity.api.spigot.preference.event;

import com.redecommunity.api.spigot.event.CommunityEvent;
import com.redecommunity.common.shared.permissions.user.data.User;
import com.redecommunity.common.shared.preference.Preference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

/**
 * Created by @SrGutyerrez
 */
@RequiredArgsConstructor
public class PreferenceStateChangeEvent extends CommunityEvent implements Cancellable {
    @Getter
    private final User user;
    @Getter
    private final Preference preference;

    private Boolean cancelled = false;

    public Player getPlayer() {
        return Bukkit.getPlayer(this.user.getUniqueId());
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }
}
