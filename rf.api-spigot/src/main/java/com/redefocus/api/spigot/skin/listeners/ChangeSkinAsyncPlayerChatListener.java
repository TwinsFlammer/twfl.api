package com.redefocus.api.spigot.skin.listeners;

import com.redefocus.api.spigot.skin.manager.SkinManager;
import com.redefocus.common.shared.language.enums.Language;
import com.redefocus.common.shared.permissions.user.data.User;
import com.redefocus.common.shared.permissions.user.manager.UserManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * Created by @SrGutyerrez
 */
public class ChangeSkinAsyncPlayerChatListener implements Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        User user = UserManager.getUser(player.getUniqueId());

        if (!user.isChangingSkin()) return;

        event.setCancelled(true);

        String skinName = event.getMessage();

        Language language = user.getLanguage();

        if (skinName.equalsIgnoreCase("cancelar")) {
            user.setChangingSkin(false);

            player.sendMessage(
                    language.getMessage("skin.cancelled")
            );
            return;
        }

        new Thread(() -> {
            Boolean result = SkinManager.change(
                    player,
                    user,
                    skinName
            );

            user.setChangingSkin(result);
        }).start();
    }
}
