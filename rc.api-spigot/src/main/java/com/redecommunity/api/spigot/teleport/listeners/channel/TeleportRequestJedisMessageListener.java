package com.redecommunity.api.spigot.teleport.listeners.channel;

import com.redecommunity.api.spigot.teleport.data.TeleportRequest;
import com.redecommunity.api.spigot.SpigotAPI;
import com.redecommunity.api.spigot.teleport.manager.TeleportRequestManager;
import com.redecommunity.common.shared.databases.redis.handler.annonation.ChannelName;
import com.redecommunity.common.shared.databases.redis.handler.event.JedisMessageEvent;
import com.redecommunity.common.shared.server.data.Server;
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
