package br.com.twinsflammer.api.bungeecord.text.jsontext.listener;

import br.com.twinsflammer.api.bungeecord.util.JSONText;
import br.com.twinsflammer.api.shared.text.jsontext.channel.JSONTextChannel;
import br.com.twinsflammer.common.shared.databases.redis.handler.JedisMessageListener;
import br.com.twinsflammer.common.shared.databases.redis.handler.annonation.ChannelName;
import br.com.twinsflammer.common.shared.databases.redis.handler.event.JedisMessageEvent;
import br.com.twinsflammer.common.shared.permissions.user.data.User;
import br.com.twinsflammer.common.shared.permissions.user.manager.UserManager;
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

        User user = UserManager.getUser(userId);

        JSONText jsonText = JSONText.fromString(text);

        jsonText.send(user);
    }
}
