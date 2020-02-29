package com.redecommunity.api.spigot.teleport.data;

import com.redecommunity.api.spigot.teleport.channel.TeleportChannel;
import com.redecommunity.api.spigot.teleport.exception.InvalidTeleportTargetServerException;
import com.redecommunity.api.spigot.util.serialize.LocationSerialize;
import com.redecommunity.common.shared.server.data.Server;
import com.redecommunity.common.shared.server.manager.ServerManager;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.json.simple.JSONObject;

/**
 * Created by @SrGutyerrez
 */
@RequiredArgsConstructor
@Getter
public class TeleportRequest {
    private final Integer userId, targetId;
    private final Location targetLocation;
    private final Integer targetServerId;
    private final Long teleportTime;

    public void teleport() throws InvalidTeleportTargetServerException {
        Server server = ServerManager.getServer(this.targetServerId);

        if (server == null)
            throw new InvalidTeleportTargetServerException("Can\'t find the target server id: " + this.targetServerId);

        String serializedLocation = targetLocation == null ? null : LocationSerialize.toString(this.targetLocation);

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user_id", this.userId);
        jsonObject.put("target_id", targetId);
        jsonObject.put("server_id", this.targetServerId);
        jsonObject.put("serialized_location", serializedLocation);

        TeleportChannel teleportChannel = new TeleportChannel();

        teleportChannel.sendMessage(jsonObject.toString());
    }

    public Boolean canTeleport() {
        return System.currentTimeMillis() >= this.teleportTime;
    }
}
