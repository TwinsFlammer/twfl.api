package br.com.twinsflammer.api.spigot.teleport.data;

import br.com.twinsflammer.api.spigot.teleport.channel.TeleportChannel;
import br.com.twinsflammer.api.spigot.teleport.exception.InvalidTeleportTargetServerException;
import br.com.twinsflammer.api.spigot.teleport.manager.TeleportRequestManager;
import br.com.twinsflammer.api.spigot.SpigotAPI;
import br.com.twinsflammer.api.spigot.teleport.event.UserTeleportEvent;
import br.com.twinsflammer.api.spigot.util.serialize.InventorySerialize;
import br.com.twinsflammer.api.spigot.util.serialize.LocationSerialize;
import br.com.twinsflammer.common.shared.permissions.user.data.User;
import br.com.twinsflammer.common.shared.permissions.user.manager.UserManager;
import br.com.twinsflammer.common.shared.server.data.Server;
import br.com.twinsflammer.common.shared.server.manager.ServerManager;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
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
        TeleportRequest teleportRequest = TeleportRequestManager.getTeleportRequest(this.userId);

        if (teleportRequest != null) return;

        TeleportRequestManager.getTeleportRequests().add(this);
    }

    public void teleport() throws InvalidTeleportTargetServerException {
        UserTeleportEvent userTeleportEvent = new UserTeleportEvent(
                this
        );

        userTeleportEvent.run();

        if (userTeleportEvent.isCancelled())
            return;

        Server server = ServerManager.getServer(this.targetServerId);

        if (server == null)
            throw new InvalidTeleportTargetServerException("Can\'t find the target server id: " + this.targetServerId);

        User user = UserManager.getUser(this.userId);
        Player player = Bukkit.getPlayer(user.getUniqueId());

        if (server.getId().equals(SpigotAPI.getCurrentServer().getId())) {
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

        JSONObject jsonObject1 = new JSONObject();

        Server fromServer = user.getServer();

        Integer fromServerId = fromServer.getId();

        Location fromLocation = player.getLocation();

        String fromLocationSerialized = LocationSerialize.toString(fromLocation);

        jsonObject1.put("server_id", fromServerId);
        jsonObject1.put("serialized_location", fromLocationSerialized);

        jsonObject.put("from", jsonObject1);

        PlayerInventory playerInventory = player.getInventory();

        JSONObject jsonObject2 = InventorySerialize.toJsonObject(playerInventory);

        jsonObject.put("inventory", jsonObject2);

        System.out.println(jsonObject.toString());

        TeleportChannel teleportChannel = new TeleportChannel();

        teleportChannel.sendMessage(jsonObject.toString());
    }

    public Boolean canTeleport() {
        return System.currentTimeMillis() >= this.teleportTime;
    }

    public Long getRemainingTime() {
        return this.teleportTime - System.currentTimeMillis();
    }
}
