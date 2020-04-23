package com.redefocus.api.spigot.util.jsontext.listener;

import com.redefocus.api.spigot.SpigotAPI;
import com.redefocus.api.spigot.user.data.SpigotUser;
import com.redefocus.api.spigot.util.jsontext.data.JSONText;
import com.redefocus.common.shared.databases.redis.handler.JedisMessageListener;
import com.redefocus.common.shared.databases.redis.handler.annonation.ChannelName;
import com.redefocus.common.shared.databases.redis.handler.event.JedisMessageEvent;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 * @author SrGutyerrez
 */
public class JSONTextJedisMessageListener implements JedisMessageListener {
    @ChannelName(name = "")
    public void onMessage(JedisMessageEvent event) {
        String message = event.getMessage();

        JSONObject jsonObject = (JSONObject) JSONValue.parse(message);

        Integer userId = ((Long) jsonObject.get("user_id")).intValue();
        String text = (String) jsonObject.get("json_text");

        SpigotUser spigotUser = SpigotAPI.getSpigotUserFactory().getUser(userId);

        Player player = spigotUser.getPlayer();

        if (player != null) {
            JSONText jsonText = JSONText.fromString(text);

            jsonText.send(player);
        }
    }
}
