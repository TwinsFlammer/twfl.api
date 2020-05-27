package br.com.twinsflammer.api.bungeecord.user.data;

import br.com.twinsflammer.api.bungeecord.util.JSONText;
import br.com.twinsflammer.api.shared.text.jsontext.channel.JSONTextChannel;
import br.com.twinsflammer.common.shared.permissions.user.data.User;
import org.json.simple.JSONObject;

/**
 * Created by @SrGutyerrez
 */
public class BungeeUser extends User {
    public BungeeUser(User user) {
        super(
                user.getId(),
                user.getName(),
                user.getDisplayName(),
                user.getUniqueId(),
                user.getEmail(),
                user.getPassword(),
                user.getCash(),
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

    public void sendMessage(JSONText jsonText) {
        String fromJSONText = jsonText.toString();

        JSONTextChannel jsonTextChannel = new JSONTextChannel();

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user_id", this.getId());
        jsonObject.put("json_text", fromJSONText);

        jsonTextChannel.sendMessage(jsonObject.toString());
    }
}
