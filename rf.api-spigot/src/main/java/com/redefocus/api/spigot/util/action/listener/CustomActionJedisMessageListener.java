package com.redefocus.api.spigot.util.action.listener;

import com.redefocus.api.spigot.SpigotAPI;
import com.redefocus.api.spigot.util.action.data.CustomAction;
import com.redefocus.common.shared.databases.redis.handler.JedisMessageListener;
import com.redefocus.common.shared.databases.redis.handler.annonation.ChannelName;
import com.redefocus.common.shared.databases.redis.handler.event.JedisMessageEvent;
import com.redefocus.common.shared.permissions.user.data.User;
import com.redefocus.common.shared.permissions.user.manager.UserManager;
import com.redefocus.common.shared.util.Constants;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 * Created by @SrGutyerrez
 */
public class CustomActionJedisMessageListener implements JedisMessageListener {
    @ChannelName(name = Constants.CUSTOM_ACTION_CHANNEL)
    public void onMessage(JedisMessageEvent event) {
        String message = event.getMessage();

        JSONObject jsonObject = (JSONObject) JSONValue.parse(message);

        Integer userId = ((Long) jsonObject.get("user_id")).intValue();
        Integer ticks = ((Long) jsonObject.get("ticks")).intValue();
        String message1 = (String) jsonObject.get("message");
        Boolean stay = (Boolean) jsonObject.get("stay");

        User user = UserManager.getUser(userId);

        if (user == null) return;

        Player player = Bukkit.getPlayer(user.getUniqueId());

        if (player == null) return;

        CustomAction customAction = new CustomAction()
                .text(message1);

        CustomAction.Spigot spigot = customAction.getSpigot();

        if (stay) {
            spigot.sendAndStay(
                    SpigotAPI.getInstance(),
                    ticks,
                    Bukkit.getOnlinePlayers()
            );
        } else {
            spigot.send(player);
        }
    }
}
