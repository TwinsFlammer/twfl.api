package com.redecommunity.api.spigot.skin.listeners;

import com.redecommunity.api.spigot.skin.manager.SkinManager;
import com.redecommunity.common.shared.permissions.user.data.User;
import com.redecommunity.common.shared.permissions.user.manager.UserManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * Created by @SrGutyerrez
 */
public class ChangeSkinAsyncPlayerChatListener implements Listener {
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        User user = UserManager.getUser(player.getUniqueId());

        if (!user.isChangingSkin()) return;

        String skinName = event.getMessage();

        SkinManager.change(
                player,
                user,
                skinName
        );
    }
}
