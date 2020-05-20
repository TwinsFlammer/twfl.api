package br.com.twinsflammer.api.spigot.util.title.listener;

import br.com.twinsflammer.api.spigot.util.title.data.CustomTitle;
import br.com.twinsflammer.common.shared.databases.redis.handler.JedisMessageListener;
import br.com.twinsflammer.common.shared.databases.redis.handler.annonation.ChannelName;
import br.com.twinsflammer.common.shared.databases.redis.handler.event.JedisMessageEvent;
import br.com.twinsflammer.common.shared.permissions.user.data.User;
import br.com.twinsflammer.common.shared.permissions.user.manager.UserManager;
import br.com.twinsflammer.common.shared.util.Constants;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 * Created by @SrGutyerrez
 */
public class CustomTitleJedisMessageListener implements JedisMessageListener {
    @ChannelName(name = Constants.CUSTOM_TITLE_CHANNEL)
    public void onMessage(JedisMessageEvent event) {
        String message = event.getMessage();

        JSONObject jsonObject = (JSONObject) JSONValue.parse(message);

        Integer userId = ((Long) jsonObject.get("user_id")).intValue();
        String title = (String) jsonObject.get("title");
        String subtitle = (String) jsonObject.get("subtitle");
        Integer fadeIn = ((Long) jsonObject.get("fade_in")).intValue();
        Integer fadeOut = ((Long) jsonObject.get("fade_out")).intValue();
        Integer stay = ((Long) jsonObject.get("stay")).intValue();

        User user = UserManager.getUser(userId);

        Player player = Bukkit.getPlayer(user.getUniqueId());

        if (player == null) return;

        CustomTitle.sendTitle(
                player,
                fadeIn,
                fadeOut,
                stay,
                title,
                subtitle
        );
    }
}
