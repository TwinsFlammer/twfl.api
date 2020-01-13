package com.redecommunity.api.spigot.skin.listeners;

import com.redecommunity.api.spigot.skin.manager.SkinManager;
import com.redecommunity.common.shared.language.enums.Language;
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

        Language language = user.getLanguage();

        if (!user.isChangingSkin()) return;

        if (!user.canChangeSkin()) {
            player.sendMessage(
                    String.format(
                            language.getMessage("skin.wait_to_change_skin"),
                            user.getTheTimeToTheNextSkinChange()
                    )
            );
            return;
        }

        String skinName = event.getMessage();

        if (!skinName.matches("[A-z0-9]") || skinName.length() > 16) {
            player.sendMessage(
                    language.getMessage("skin.invalid_skin_name")
            );
            return;
        }

        SkinManager.change(
                player,
                user,
                skinName
        );
    }
}
