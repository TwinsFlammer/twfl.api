package br.com.twinsflammer.api.shared.commands.defaults.disable.listener;

import br.com.twinsflammer.api.shared.commands.defaults.disable.data.DisabledCommand;
import br.com.twinsflammer.api.shared.commands.defaults.disable.manager.DisabledCommandManager;
import br.com.twinsflammer.common.shared.databases.redis.handler.JedisMessageListener;
import br.com.twinsflammer.common.shared.databases.redis.handler.annonation.ChannelName;
import br.com.twinsflammer.common.shared.databases.redis.handler.event.JedisMessageEvent;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.util.Arrays;

/**
 * Created by @SrGutyerrez
 */
public class DisabledCommandJedisMessageListener implements JedisMessageListener {
    private String[] actions = new String[] { "enable", "disable" };

    @ChannelName(name = DisabledCommandManager.CHANNEL_NAME)
    public void onMessage(JedisMessageEvent event) {
        String message = event.getMessage();

        JSONObject jsonObject = (JSONObject) JSONValue.parse(message);

        String action = (String) jsonObject.get("action");
        Integer id = ((Long) jsonObject.get("id")).intValue();
        String name = (String) jsonObject.get("name");

        if (!Arrays.asList(actions).contains(action)) return;

        if (action.equalsIgnoreCase("enable")) {
            DisabledCommandManager.removeDisabledCommand(name);
        } else if (action.equalsIgnoreCase("disable")) {
            Integer userId = ((Long) jsonObject.get("user_id")).intValue();
            Long time = (Long) jsonObject.get("time");

            DisabledCommand disabledCommand = new DisabledCommand(
                    id,
                    userId,
                    name,
                    time
            );

            DisabledCommandManager.setCommandDisabled(disabledCommand);
        }
    }
}
