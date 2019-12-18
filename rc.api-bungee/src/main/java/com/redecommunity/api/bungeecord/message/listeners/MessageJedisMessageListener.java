package com.redecommunity.api.bungeecord.message.listeners;

import com.redecommunity.api.bungeecord.message.Message;
import com.redecommunity.api.bungeecord.user.ProxyUser;
import com.redecommunity.common.shared.databases.redis.handler.JedisMessageListener;
import com.redecommunity.common.shared.databases.redis.handler.annonation.ChannelName;
import com.redecommunity.common.shared.databases.redis.handler.event.JedisMessageEvent;
import com.redecommunity.common.shared.permissions.user.manager.UserManager;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.util.Arrays;

/**
 * Created by @SrGutyerrez
 */
public class MessageJedisMessageListener implements JedisMessageListener {
    private String[] platforms = new String[] { "bukkit-server" };

    @ChannelName(name = Message.CHANNEL_NAME)
    public void onMessage(JedisMessageEvent event) {
        String message = event.getMessage();

        JSONObject jsonObject = (JSONObject) JSONValue.parse(message);

        String platform = (String) jsonObject.get("platform");
        Integer userId = (Integer) jsonObject.get("user_id");
        String receivedMessage = (String) jsonObject.get("received_message");

        ProxyUser proxyUser = (ProxyUser) UserManager.getUser(userId);

        if (!Arrays.asList(this.platforms).contains(platform)) return;

        if (proxyUser.getId() == 0) {
            ProxyServer.getInstance().getConsole().sendMessage(receivedMessage);
            return;
        }

        ProxiedPlayer proxiedPlayer = proxyUser.getProxiedPlayer();

        if (proxiedPlayer == null) return;

        proxiedPlayer.sendMessage(receivedMessage);
    }
}
