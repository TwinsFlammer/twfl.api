package br.com.twinsflammer.api.spigot.user.data;

import br.com.twinsflammer.api.spigot.SpigotAPI;
import br.com.twinsflammer.api.shared.text.jsontext.channel.JSONTextChannel;
import br.com.twinsflammer.api.spigot.util.jsontext.data.JSONText;
import br.com.twinsflammer.common.shared.permissions.group.data.Group;
import br.com.twinsflammer.common.shared.permissions.user.data.User;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.json.simple.JSONObject;

/**
 * Created by @SrGutyerrez
 */
public class SpigotUser extends User {
    @Getter
    @Setter
    private String lastTeleportRequestSecond = "";

    public SpigotUser(User user) {
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

    public Player getPlayer() {
        return Bukkit.getPlayer(this.getUniqueId());
    }

    public void sendMessage(JSONText jsonText) {
        String fromJSONText = jsonText.toString();

        JSONTextChannel jsonTextChannel = new JSONTextChannel();

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("user_id", this.getId());
        jsonObject.put("json_text", fromJSONText);

        jsonTextChannel.sendMessage(jsonObject.toString());
    }

    public void applyPermissions() {
        Player player = this.getPlayer();

        if (player == null) return;

        player.getEffectivePermissions().clear();

        PermissionAttachment permissionAttachment = player.addAttachment(SpigotAPI.getInstance());

        this.getGroups().forEach(userGroup -> {
            Group group = userGroup.getGroup();

            group.getPermissions()
                    .forEach(permission -> {
                        String permissionName = permission.getName();
                        Integer rootServerId = SpigotAPI.getRootServerId();

                        if (permission.getServerId().equals(rootServerId) || permission.getServerId().equals(0))
                            permissionAttachment.setPermission(
                                    permissionName.replaceAll(
                                            "-",
                                            ""
                                    ),
                                    permissionName.contains("-")
                            );
                    });
        });
    }
}
