package br.com.twinsflammer.api.spigot.teleport.listeners.channel;

import br.com.twinsflammer.api.spigot.teleport.data.TeleportRequest;
import br.com.twinsflammer.api.spigot.SpigotAPI;
import br.com.twinsflammer.api.spigot.teleport.manager.TeleportRequestManager;
import br.com.twinsflammer.common.shared.databases.redis.handler.annonation.ChannelName;
import br.com.twinsflammer.common.shared.databases.redis.handler.event.JedisMessageEvent;
import br.com.twinsflammer.common.shared.server.data.Server;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 * Created by @SrGutyerrez
 */
public class TeleportRequestJedisMessageListener {
    @ChannelName(name = TeleportRequestManager.CHANNEL_NAME)
    public void onMessage(JedisMessageEvent event) {
        String message = event.getMessage();

        JSONObject jsonObject = (JSONObject) JSONValue.parse(message);

        TeleportRequest teleportRequest = TeleportRequestManager.toTeleportRequest(jsonObject);

        Integer targetServerId = teleportRequest.getTargetServerId();

        Server currentServer = SpigotAPI.getCurrentServer();

        if (currentServer == null || !currentServer.getId().equals(targetServerId)) return;

        TeleportRequestManager.getWaitingJoin().add(teleportRequest);
    }
}
