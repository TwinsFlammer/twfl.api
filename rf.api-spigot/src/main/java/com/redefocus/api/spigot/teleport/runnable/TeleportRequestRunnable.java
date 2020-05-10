package com.redefocus.api.spigot.teleport.runnable;

import com.redefocus.api.spigot.SpigotAPI;
import com.redefocus.api.spigot.teleport.exception.InvalidTeleportTargetServerException;
import com.redefocus.api.spigot.teleport.manager.TeleportRequestManager;
import com.redefocus.api.spigot.user.data.SpigotUser;
import com.redefocus.api.spigot.util.title.data.CustomTitle;
import com.redefocus.common.shared.util.TimeFormatter;

/**
 * Created by @SrGutyerrez
 */
public class TeleportRequestRunnable implements Runnable {
    @Override
    public void run() {
        TeleportRequestManager.getTeleportRequests()
                .removeIf(teleportRequest -> {
                    SpigotUser spigotUser = SpigotAPI.getSpigotUserFactory().getUser(teleportRequest.getUserId());

                    CustomTitle.sendTitle(
                            spigotUser.getPlayer(),
                            0,
                            15,
                            0,
                            "§eTeletransportando",
                            "§eEm " + TimeFormatter.formatMinimized(
                                    teleportRequest.getRemainingTime()
                            ) + "..."
                    );

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
