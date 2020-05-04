package com.redefocus.api.bungeecord.message.listeners;

import com.redefocus.common.shared.databases.redis.handler.JedisMessageListener;
import com.redefocus.common.shared.databases.redis.handler.annonation.ChannelName;
import com.redefocus.common.shared.databases.redis.handler.event.JedisMessageEvent;
import com.redefocus.common.shared.permissions.user.data.User;
import com.redefocus.common.shared.permissions.user.manager.UserManager;
import com.redefocus.common.shared.util.Constants;
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

    @ChannelName(name = Constants.MESSAGE_CHANNEL)
    public void onMessage(JedisMessageEvent event) {
        String message = event.getMessage();

        JSONObject jsonObject = (JSONObject) JSONValue.parse(message);

        String platform = (String) jsonObject.get("platform");
        Integer userId = ((Long) jsonObject.get("user_id")).intValue();
        String receivedMessage = (String) jsonObject.get("received_message");

        User user = UserManager.getUser(userId);

        if (!Arrays.asList(this.platforms).contains(platform)) return;

        if (user.getId() == 1) {
            ProxyServer.getInstance().getConsole().sendMessage(receivedMessage);
            return;
        }

        ProxiedPlayer proxiedPlayer = ProxyServer.getInstance().getPlayer(user.getName());

        if (proxiedPlayer == null) return;

        proxiedPlayer.sendMessage(receivedMessage);
    }
}
