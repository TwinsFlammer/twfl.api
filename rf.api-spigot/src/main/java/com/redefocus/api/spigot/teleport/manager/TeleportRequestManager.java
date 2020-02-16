package com.redefocus.api.spigot.teleport.manager;

import com.google.common.collect.Lists;
import com.redefocus.api.spigot.teleport.data.TeleportRequest;
import com.redefocus.api.spigot.util.serialize.LocationSerialize;
import com.redefocus.common.shared.permissions.user.data.User;
import org.bukkit.Location;
import org.json.simple.JSONObject;

import java.util.List;

/**
 * Created by @SrGutyerrez
 */
public class TeleportRequestManager {
    public static final String CHANNEL_NAME = "teleport_channel";

    private static final List<TeleportRequest> teleportRequests = Lists.newArrayList(),
            waitingJoin = Lists.newArrayList();

    public static List<TeleportRequest> getTeleportRequests() {
        return TeleportRequestManager.teleportRequests;
    }

    public static List<TeleportRequest> getWaitingJoin() {
        return TeleportRequestManager.waitingJoin;
    }

    public static TeleportRequest getTeleportRequest(User user) {
        return TeleportRequestManager.waitingJoin
                .stream()
                .filter(teleportRequest -> teleportRequest.getUserId().equals(user.getId()))
                .findFirst()
                .orElse(null);
    }

    public static TeleportRequest toTeleportRequest(JSONObject jsonObject) {
        Integer userId = ((Long) jsonObject.get("user_id")).intValue();
        Integer targetServerId = ((Long) jsonObject.get("server_id")).intValue();
        String serializedLocation = (String) jsonObject.get("serialized_location");

        Location targetLocation = LocationSerialize.toLocation(serializedLocation);

        return new TeleportRequest(
                userId,
                null,
                targetLocation,
                targetServerId,
                System.currentTimeMillis()
        );
    }
}
