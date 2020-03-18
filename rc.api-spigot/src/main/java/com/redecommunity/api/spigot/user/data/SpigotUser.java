package com.redecommunity.api.spigot.user.data;

import com.redecommunity.common.shared.permissions.user.data.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Created by @SrGutyerrez
 */
public class SpigotUser extends User {
    public SpigotUser(User user) {
        super(
                user.getId(),
                user.getName(),
                user.getDisplayName(),
                user.getUniqueId(),
                user.getEmail(),
                user.getPassword(),
                user.getDiscordId(),
                user.isTwoFactorAuthenticationEnabled(),
                user.getTwoFactorAuthenticationCode(),
                user.getCreatedAt(),
                user.getFirstLogin(),
                user.getLastLogin(),
                user.getLastAddress(),
                user.getLastLobbyId(),
                user.getLanguageId(),
                user.getTwitterAccessToken(),
                user.getTwitterTokenSecret(),
                user.getGroups(),
                user.getPreferences(),
                user.getFriends(),
                user.getIgnored(),
                user.getReports(),
                user.getSkins(),
                user.isChangingSkin(),
                user.isWaitingTabListRefresh()
        );
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(this.getUniqueId());
    }
}
