package com.redefocus.api.spigot.teleport.runnable;

import com.redefocus.api.spigot.SpigotAPI;
import com.redefocus.api.spigot.teleport.exception.InvalidTeleportTargetServerException;
import com.redefocus.api.spigot.teleport.manager.TeleportRequestManager;
import com.redefocus.api.spigot.user.data.SpigotUser;
import com.redefocus.api.spigot.util.title.data.CustomTitle;
import com.redefocus.common.shared.util.TimeFormatter;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

/**
 * Created by @SrGutyerrez
 */
public class TeleportRequestRunnable implements Runnable {
    @Override
    public void run() {
        TeleportRequestManager.getTeleportRequests()
                .removeIf(teleportRequest -> {
                    SpigotUser spigotUser = SpigotAPI.getSpigotUserFactory().getUser(teleportRequest.getUserId());

                    Player player = spigotUser.getPlayer();

                    String teleportRequestSecond = TimeFormatter.formatMinimized(
                            teleportRequest.getRemainingTime()
                    );

                    CustomTitle.sendTitle(
                            player,
                            0,
                            15,
                            0,
                            "§eTeletransportando",
                            "§eEm " + teleportRequestSecond + "..."
                    );

                    if (!spigotUser.getLastTeleportRequestSecond().equals(teleportRequestSecond))
                        player.playSound(
                                player.getLocation(),
                                Sound.NOTE_PLING,
                                1.0F,
                                1.0F
                        );

                    spigotUser.setLastTeleportRequestSecond(teleportRequestSecond);

                    try {
                        if (teleportRequest.canTeleport()) {
                            try {
                                teleportRequest.teleport();
                            } catch (InvalidTeleportTargetServerException exception) {
                                exception.printStackTrace();
                            }

                            return true;
                        }
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }

                    return false;
                });
    }
}
