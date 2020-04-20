package com.redefocus.api.spigot.teleport.runnable;

import com.redefocus.api.spigot.teleport.exception.InvalidTeleportTargetServerException;
import com.redefocus.api.spigot.teleport.manager.TeleportRequestManager;

/**
 * Created by @SrGutyerrez
 */
public class TeleportRequestRunnable implements Runnable {
    @Override
    public void run() {
        TeleportRequestManager.getTeleportRequests()
                .removeIf(teleportRequest -> {
                    Boolean canTeleport = teleportRequest.canTeleport();

                    if (canTeleport) {
                        try {
                            teleportRequest.teleport();
                        } catch (InvalidTeleportTargetServerException exception) {
                            exception.printStackTrace();
                        }
                    }

                    return canTeleport;
                });
    }
}
