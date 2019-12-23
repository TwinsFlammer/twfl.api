package com.redecommunity.api.shared.commands.defaults.disable.listener;

import com.redecommunity.api.shared.commands.defaults.disable.data.DisabledCommand;
import com.redecommunity.api.shared.commands.defaults.disable.manager.DisabledCommandManager;
import com.redecommunity.common.shared.databases.redis.handler.JedisMessageListener;
import com.redecommunity.common.shared.databases.redis.handler.annonation.ChannelName;
import com.redecommunity.common.shared.databases.redis.handler.event.JedisMessageEvent;
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
        Integer id = (Integer) jsonObject.get("id");
        String name = (String) jsonObject.get("name");

        if (!Arrays.asList(actions).contains(action)) return;

        if (action.equalsIgnoreCase("enable")) {
            DisabledCommandManager.removeDisabledCommand(name);
        } else {
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
