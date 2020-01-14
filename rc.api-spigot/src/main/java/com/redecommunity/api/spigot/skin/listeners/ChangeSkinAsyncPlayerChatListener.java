package com.redecommunity.api.spigot.skin.listeners;

import com.redecommunity.api.spigot.SpigotAPI;
import com.redecommunity.api.spigot.skin.manager.SkinManager;
import com.redecommunity.common.shared.language.enums.Language;
import com.redecommunity.common.shared.permissions.user.data.User;
import com.redecommunity.common.shared.permissions.user.manager.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * Created by @SrGutyerrez
 */
public class ChangeSkinAsyncPlayerChatListener implements Listener {
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
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

        Bukkit.getScheduler().runTaskAsynchronously(
                SpigotAPI.getInstance(),
                () -> {
                    Boolean result = SkinManager.change(
                            player,
                            user,
                            skinName
                    );

                    user.setChangingSkin(result);
                }
        );
    }
}
