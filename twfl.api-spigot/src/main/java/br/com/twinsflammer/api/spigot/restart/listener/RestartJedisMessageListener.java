package br.com.twinsflammer.api.spigot.restart.listener;

import br.com.twinsflammer.api.spigot.SpigotAPI;
import br.com.twinsflammer.common.shared.databases.redis.handler.JedisMessageListener;
import br.com.twinsflammer.common.shared.databases.redis.handler.annonation.ChannelName;
import br.com.twinsflammer.common.shared.databases.redis.handler.event.JedisMessageEvent;
import br.com.twinsflammer.common.shared.server.data.Server;
import org.bukkit.Bukkit;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;

/**
 * Created by @SrGutyerrez
 */
public class RestartJedisMessageListener implements JedisMessageListener {
    @ChannelName(name = "restart_channel")
    public void onRestart(JedisMessageEvent event) {
        String message = event.getMessage();

        JSONArray jsonArray = (JSONArray) JSONValue.parse(message);

        Server server = SpigotAPI.getCurrentServer();

        assert server != null;

        jsonArray.forEach(o -> {
            Integer serverId = (Integer) o;

            if (serverId.equals(server.getId())) Bukkit.shutdown();
        });
    }
}
