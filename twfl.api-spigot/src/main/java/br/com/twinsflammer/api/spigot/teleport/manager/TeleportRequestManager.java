package br.com.twinsflammer.api.spigot.teleport.manager;

import br.com.twinsflammer.api.spigot.teleport.runnable.TeleportRequestRunnable;
import com.google.common.collect.Lists;
import br.com.twinsflammer.api.spigot.teleport.data.TeleportRequest;
import br.com.twinsflammer.api.spigot.util.serialize.LocationSerialize;
import br.com.twinsflammer.common.shared.Common;
import br.com.twinsflammer.common.shared.permissions.user.data.User;
import org.bukkit.Location;
import org.json.simple.JSONObject;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by @SrGutyerrez
 */
public class TeleportRequestManager {
    public static final String CHANNEL_NAME = "teleport_channel";

    public TeleportRequestManager() {
        Common.getInstance().getScheduler().scheduleWithFixedDelay(
                new TeleportRequestRunnable(),
                0,
                250,
                TimeUnit.MILLISECONDS
        );
    }

    private static final List<TeleportRequest> TELEPORT_REQUESTS = Lists.newArrayList(),
            WAITING_JOIN = Lists.newArrayList();

    public static List<TeleportRequest> getTeleportRequests() {
        return TeleportRequestManager.TELEPORT_REQUESTS;
    }

    public static List<TeleportRequest> getWaitingJoin() {
        return TeleportRequestManager.WAITING_JOIN;
    }

    public static TeleportRequest getTeleportRequest(Integer userId) {
        return TeleportRequestManager.WAITING_JOIN
                .stream()
                .filter(teleportRequest -> teleportRequest.getUserId().equals(userId))
                .findFirst()
                .orElse(null);
    }

    public static TeleportRequest getTeleportRequest(User user) {
        return TeleportRequestManager.getTeleportRequest(user.getId());
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
