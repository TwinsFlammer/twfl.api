package br.com.twinsflammer.api.spigot.preference.event;

import br.com.twinsflammer.api.spigot.event.TwinsEvent;
import br.com.twinsflammer.common.shared.permissions.user.data.User;
import br.com.twinsflammer.common.shared.preference.Preference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

/**
 * Created by @SrGutyerrez
 */
@RequiredArgsConstructor
public class PreferenceStateChangeEvent extends TwinsEvent implements Cancellable {
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
