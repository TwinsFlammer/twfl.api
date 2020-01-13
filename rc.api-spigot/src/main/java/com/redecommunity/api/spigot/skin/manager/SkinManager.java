package com.redecommunity.api.spigot.skin.manager;

import com.google.common.collect.Maps;
import com.redecommunity.api.spigot.book.CustomBook;
import com.redecommunity.common.shared.language.enums.Language;
import com.redecommunity.common.shared.permissions.user.data.User;
import com.redecommunity.common.shared.skin.dao.SkinDao;
import com.redecommunity.common.shared.skin.data.Skin;
import com.redecommunity.common.shared.skin.factory.SkinFactory;
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

        SkinDao skinDao = new SkinDao();

        HashMap<String, String> keys = Maps.newHashMap();

        keys.put("owner", skinName.toLowerCase());

        Skin skin = skinDao.findOne(keys);

        if (skin == null) skin = SkinFactory.getSkin(user.getName());

        if (skin == null) {
            sender.sendMessage(
                    language.getMessage("skin.can\'t_find_an_skin_with_this_username")
            );
            return false;
        }

        user.setSkin(skin);

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
                                .add(" Data de atualização: §7" + simpleDateFormat.format(System.currentTimeMillis()))
                                .newLine()
                                .newLine()
                                .newLine()
                                .add("Você precisará esperar um total de 15 minutos para poder alterar sua skin novamente.")
                                .newLine()
                                .newLine()
                                .add("Para que ela seja aplicada é necessário que você reconecte ao servidor.")
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
