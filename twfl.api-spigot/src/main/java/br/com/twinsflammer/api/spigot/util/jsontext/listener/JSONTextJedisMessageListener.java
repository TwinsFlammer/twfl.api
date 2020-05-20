package br.com.twinsflammer.api.spigot.util.jsontext.listener;

import br.com.twinsflammer.api.spigot.SpigotAPI;
import br.com.twinsflammer.api.spigot.util.jsontext.channel.JSONTextChannel;
import br.com.twinsflammer.api.spigot.user.data.SpigotUser;
import br.com.twinsflammer.api.spigot.util.jsontext.data.JSONText;
import br.com.twinsflammer.common.shared.databases.redis.handler.JedisMessageListener;
import br.com.twinsflammer.common.shared.databases.redis.handler.annonation.ChannelName;
import br.com.twinsflammer.common.shared.databases.redis.handler.event.JedisMessageEvent;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 * @author SrGutyerrez
 */
public class JSONTextJedisMessageListener implements JedisMessageListener {
    @ChannelName(name = JSONTextChannel.CHANNEL_NAME)
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
