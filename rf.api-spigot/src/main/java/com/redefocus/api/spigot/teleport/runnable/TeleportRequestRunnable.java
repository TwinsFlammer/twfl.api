package com.redefocus.api.spigot.teleport.runnable;

import com.redefocus.api.spigot.teleport.data.TeleportRequest;
import com.redefocus.api.spigot.teleport.exception.InvalidTeleportTargetServerException;
import com.redefocus.api.spigot.teleport.manager.TeleportRequestManager;

/**
 * Created by @SrGutyerrez
 */
public class TeleportRequestRunnable implements Runnable {
    @Override
    public void run() {
        TeleportRequestManager.getTeleportRequests()
                .stream()
                .filter(TeleportRequest::canTeleport)
                .forEach(teleportRequest -> {
                    try {
                        teleportRequest.teleport();
                    } catch (InvalidTeleportTargetServerException exception) {
                        exception.printStackTrace();
                    }
                });
    }
}
