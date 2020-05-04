package com.redefocus.api.spigot.skin.command.argument;

import com.google.common.collect.Maps;
import com.redefocus.api.spigot.commands.CustomArgumentCommand;
import com.redefocus.common.shared.language.enums.Language;
import com.redefocus.common.shared.permissions.user.data.User;
import com.redefocus.common.shared.skin.dao.SkinDao;
import com.redefocus.common.shared.skin.data.Skin;
import com.redefocus.common.shared.skin.factory.SkinFactory;
import org.bukkit.command.CommandSender;

import java.util.HashMap;

/**
 * Created by @SrGutyerrez
 */
public class SkinRefreshCommand extends CustomArgumentCommand {
    public SkinRefreshCommand() {
        super(0, "atualizar");
    }

    @Override
    public void onCommand(CommandSender sender, User user, String[] args) {
        Language language = user.getLanguage();

        sender.sendMessage(
                language.getMessage("skin.refreshing_your_skin")
        );

        SkinDao skinDao = new SkinDao();

        HashMap<String, String> keys = Maps.newHashMap();

        keys.put("owner", user.getName().toLowerCase());

        Skin skin = skinDao.findOne(keys);

        if (skin == null) skin = SkinFactory.getSkin(user.getName());

        if (skin == null) {
            sender.sendMessage(
                    language.getMessage("skin.can\'t_find_your_skin")
            );
            return;
        }

        user.setSkin(skin);

        sender.sendMessage(
                language.getMessage("skin.refreshed")
        );
    }
}
