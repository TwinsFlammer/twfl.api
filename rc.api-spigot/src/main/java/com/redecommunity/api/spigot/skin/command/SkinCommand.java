package com.redecommunity.api.spigot.skin.command;

import com.google.common.collect.Maps;
import com.redecommunity.api.spigot.commands.CustomCommand;
import com.redecommunity.api.spigot.commands.enums.CommandRestriction;
import com.redecommunity.api.spigot.skin.command.argument.SkinHelpCommand;
import com.redecommunity.api.spigot.skin.command.argument.SkinRefreshCommand;
import com.redecommunity.api.spigot.skin.inventory.SkinInventory;
import com.redecommunity.common.shared.language.enums.Language;
import com.redecommunity.common.shared.permissions.user.data.User;
import com.redecommunity.common.shared.skin.dao.SkinDao;
import com.redecommunity.common.shared.skin.data.Skin;
import com.redecommunity.common.shared.skin.factory.SkinFactory;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

/**
 * Created by @SrGutyerrez
 */
public class SkinCommand extends CustomCommand {
    public SkinCommand() {
        super("skin", CommandRestriction.IN_GAME, "default");

        this.addArgument(
                new SkinRefreshCommand(),
                new SkinHelpCommand()
        );
    }

    @Override
    public void onCommand(CommandSender sender, User user, String[] args) {
        Language language = user.getLanguage();

        Inventory inventory = new SkinInventory(user);

        Player player = (Player) sender;

        if (args.length == 0) {
            player.openInventory(inventory);
            return;
        } else if (args.length == 1) {
            String skinName = args[0];

            SkinDao skinDao = new SkinDao();

            HashMap<String, String> keys = Maps.newHashMap();

            keys.put("owner", skinName.toLowerCase());

            Skin skin = skinDao.findOne(keys);

            if (skin == null) skin = SkinFactory.getSkin(user.getName());

            if (skin == null) {
                sender.sendMessage(
                        language.getMessage("skin.can\'t_find_an_skin_with_this_username")
                );
                return;
            }

            user.setSkin(skin);

            sender.sendMessage(
                    language.getMessage("skin.changed")
            );
            return;
        } else {
            sender.sendMessage(
                    String.format(
                            language.getMessage("messages.default_commands.invalid_usage"),
                            this.getName(),
                            "<nome>"
                    )
            );
            return;
        }
    }
}
