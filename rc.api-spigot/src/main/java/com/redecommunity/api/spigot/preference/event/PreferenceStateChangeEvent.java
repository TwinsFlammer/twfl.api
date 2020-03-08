package com.redecommunity.api.spigot.preference.event;

import com.redecommunity.api.spigot.event.CommunityEvent;
import com.redecommunity.common.shared.permissions.user.data.User;
import com.redecommunity.common.shared.preference.Preference;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.Cancellable;

/**
 * Created by @SrGutyerrez
 */
@RequiredArgsConstructor
public class PreferenceStateChangeEvent extends CommunityEvent implements Cancellable {
    private final User user;
    private final Preference preference;
    private Boolean cancelled = false;

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }
}
