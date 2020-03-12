package com.redecommunity.api.spigot.teleport.data;

import com.redecommunity.api.spigot.SpigotAPI;
import com.redecommunity.api.spigot.teleport.channel.TeleportChannel;
import com.redecommunity.api.spigot.teleport.exception.InvalidTeleportTargetServerException;
import com.redecommunity.api.spigot.teleport.manager.TeleportRequestManager;
import com.redecommunity.api.spigot.util.serialize.LocationSerialize;
import com.redecommunity.common.shared.permissions.user.data.User;
import com.redecommunity.common.shared.permissions.user.manager.UserManager;
import com.redecommunity.common.shared.server.data.Server;
import com.redecommunity.common.shared.server.manager.ServerManager;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
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

    public void start() {
        TeleportRequestManager.getTeleportRequests().add(this);
    }

    public void teleport() throws InvalidTeleportTargetServerException {
        Server server = ServerManager.getServer(this.targetServerId);

        if (server == null)
            throw new InvalidTeleportTargetServerException("Can\'t find the target server id: " + this.targetServerId);

        if (server.getId().equals(SpigotAPI.getCurrentServer().getId())) {
            User user = UserManager.getUser(this.userId);

            Player player = Bukkit.getPlayer(user.getUniqueId());

            if (this.targetId == null) {
                player.teleport(this.targetLocation);
            } else {
                User user1 = UserManager.getUser(this.targetId);

                Player player1 = Bukkit.getPlayer(user1.getUniqueId());

                player.teleport(player1);
            }
            return;
        }

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
