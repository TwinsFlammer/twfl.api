package com.redecommunity.api.spigot.teleport.manager;

import com.google.common.collect.Lists;
import com.redecommunity.api.spigot.teleport.data.TeleportRequest;
import com.redecommunity.api.spigot.util.serialize.LocationSerialize;
import com.redecommunity.common.shared.permissions.user.data.User;
import org.bukkit.Location;
import org.json.simple.JSONObject;

import java.util.List;

/**
 * Created by @SrGutyerrez
 */
public class TeleportRequestManager {
    public static final String CHANNEL_NAME = "teleport_channel";

    private static final List<TeleportRequest> TELEPORT_REQUESTS = Lists.newArrayList(),
            WAITING_JOIN = Lists.newArrayList();

    public static List<TeleportRequest> getTeleportRequests() {
        return TeleportRequestManager.TELEPORT_REQUESTS;
    }

    public static List<TeleportRequest> getWaitingJoin() {
        return TeleportRequestManager.WAITING_JOIN;
    }

    public static TeleportRequest getTeleportRequest(User user) {
        return TeleportRequestManager.WAITING_JOIN
                .stream()
                .filter(teleportRequest -> teleportRequest.getUserId().equals(user.getId()))
                .findFirst()
                .orElse(null);
    }

    public static TeleportRequest toTeleportRequest(JSONObject jsonObject) {
        Integer userId = ((Long) jsonObject.get("user_id")).intValue();
        Integer targetId = jsonObject.containsKey("target_id") ? ((Long) jsonObject.get("target_id")).intValue() : null;
        Integer targetServerId = ((Long) jsonObject.get("server_id")).intValue();
        String serializedLocation = (String) jsonObject.get("serialized_location");

        Location targetLocation = LocationSerialize.toLocation(serializedLocation);

        return new TeleportRequest(
                userId,
                targetId,
                targetLocation,
                targetServerId,
                System.currentTimeMillis()
        );
    }
}
