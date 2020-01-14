package com.redecommunity.api.spigot.skin.manager;

import com.google.common.collect.Maps;
import com.redecommunity.api.spigot.book.CustomBook;
import com.redecommunity.common.shared.language.enums.Language;
import com.redecommunity.common.shared.permissions.user.data.User;
import com.redecommunity.common.shared.skin.dao.SkinDao;
import com.redecommunity.common.shared.skin.data.Skin;
import com.redecommunity.common.shared.skin.factory.SkinFactory;
import com.redecommunity.common.shared.util.TimeFormatter;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.text.SimpleDateFormat;
import java.util.HashMap;

/**
 * Created by @SrGutyerrez
 */
public class SkinManager {
    public static Boolean change(CommandSender sender, User user, String skinName) {
        Language language = user.getLanguage();

        if (!user.canChangeSkin()) {
            sender.sendMessage(
                    String.format(
                            language.getMessage("skin.wait_to_change_skin"),
                            TimeFormatter.format(user.getTheTimeToTheNextSkinChange())
                    )
            );
            return false;
        }

        if (!skinName.matches("[a-zA-Z0-9-_]*") || skinName.length() > 16) {
            sender.sendMessage(
                    language.getMessage("skin.invalid_skin_name")
            );
            return false;
        }

        SkinDao skinDao = new SkinDao();

        HashMap<String, String> keys = Maps.newHashMap();

        keys.put("owner", skinName.toLowerCase());

        Skin skin = skinDao.findOne(keys);

        if (skin == null) skin = SkinFactory.getSkin(skinName.toLowerCase());

        if (skin == null) {
            sender.sendMessage(
                    language.getMessage("skin.can\'t_find_an_skin_with_this_username")
            );
            return false;
        }

        if (skin.getUserId() == user.getId()) {
            final Skin skin1 = skin;

            skin = user.getSkins()
                    .stream()
                    .filter(skin2 -> skin2.getId().equals(skin1.getId()))
                    .findFirst()
                    .get();

            skin.setActive(true);
        } else {
            user.setSkin(skin);
        }

        if (!(sender instanceof Player)) return false;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("DD/MM/YYYY");

        ItemStack itemStack = CustomBook.writtenBook()
                .title("Skin")
                .author("Gutyerrez")
                .pages(
                        new CustomBook.PageBuilder()
                                .add("")
                                .newLine()
                                .add("§aSua skin foi alterada com sucesso!")
                                .newLine()
                                .newLine()
                                .add(" Sua nova skin: " + skinName)
                                .newLine()
                                .add(" Data de atualização: §7")
                                .newLine()
                                .add(" " + simpleDateFormat.format(System.currentTimeMillis()))
                                .newLine()
                                .newLine()
                                .newLine()
                                .add("Para que ela seja aplicada é necessário que você ")
                                .add(
                                        CustomBook.TextBuilder.of("reconecte")
                                                .color(ChatColor.GREEN)
                                                .style(ChatColor.UNDERLINE)
                                                .onClick(CustomBook.ClickAction.runCommand("/desconectar"))
                                                .build()
                                )
                                .add(" ao servidor.")
                                .build()
                )
                .build();

        Player player = (Player) sender;

        CustomBook.openPlayer(
                player,
                itemStack
        );

        return true;
    }
}
