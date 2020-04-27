package com.redefocus.api.spigot.user.data;

import com.redefocus.api.spigot.util.jsontext.channel.JSONTextChannel;
import com.redefocus.api.spigot.util.jsontext.data.JSONText;
import com.redefocus.common.shared.permissions.user.data.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;

/**
 * Created by @SrGutyerrez
 */
public class SpigotUser extends User {
    public SpigotUser(User user) {
        super(
                user.getId(),
                user.getName(),
                user.getDisplayName(),
                user.getUniqueId(),
                user.getEmail(),
                user.getPassword(),
                user.getDiscordId(),
                user.isTwoFactorAuthenticationEnabled(),
                user.getTwoFactorAuthenticationCode(),
                user.getCreatedAt(),
                user.getFirstLogin(),
                user.getLastLogin(),
                user.getLastAddress(),
                user.getLastLobbyId(),
                user.getLanguageId(),
                user.getTwitterAccessToken(),
                user.getTwitterTokenSecret(),
                user.getGroups(),
                user.getPreferences(),
                user.getFriends(),
                user.getIgnored(),
                user.getReports(),
                user.getSkins(),
                user.isChangingSkin(),
                user.isWaitingTabListRefresh()
        );
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(this.getUniqueId());
    }

    public void sendMessage(JSONText jsonText) {
        String fromJSONText = jsonText.toString();

        System.out.println(fromJSONText);

        JSONTextChannel jsonTextChannel = new JSONTextChannel();

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user_id", this.getId());
        jsonObject.put("json_text", fromJSONText);

        jsonTextChannel.sendMessage(jsonObject.toString());
    }
}
