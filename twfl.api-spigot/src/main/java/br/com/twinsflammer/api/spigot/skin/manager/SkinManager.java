package br.com.twinsflammer.api.spigot.skin.manager;

import com.google.common.collect.Maps;
import br.com.twinsflammer.api.spigot.book.CustomBook;
import br.com.twinsflammer.common.shared.language.enums.Language;
import br.com.twinsflammer.common.shared.permissions.user.data.User;
import br.com.twinsflammer.common.shared.skin.dao.SkinDao;
import br.com.twinsflammer.common.shared.skin.data.Skin;
import br.com.twinsflammer.common.shared.skin.factory.SkinFactory;
import br.com.twinsflammer.common.shared.util.TimeFormatter;
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
        return SkinManager.change(
                sender,
                user,
                skinName,
                false
        );
    }

    public static Boolean change(CommandSender sender, User user, String skinName, Boolean refresh) {
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

        Player player = (Player) sender;

        SkinDao skinDao = new SkinDao();

        HashMap<String, Object> keys = Maps.newHashMap();

        if (refresh) {
            Skin skin = SkinFactory.getSkin(skinName.toLowerCase());

            if (skin == null) return false;

            keys.put("user_id", user.getId());
            keys.put("owner", skinName.toLowerCase());

            Skin find = skinDao.findOne(keys);

            if (find != null) {
                keys.clear();

                keys.put("active", true);
                keys.put("signature", skin.getSignature());
                keys.put("value", skin.getValue());
                keys.put("last_use", skin.getLastUse());

                skinDao.update(
                        keys,
                        "id",
                        find.getId()
                );
            } else {
                user.setSkin(skin);
            }

            SkinManager.openBook(
                    player,
                    skinName
            );

            return true;
        }

        keys.clear();

        keys.put("owner", skinName.toLowerCase());

        Skin skin = skinDao.findOne(keys);

        if (skin == null) skin = SkinFactory.getSkin(skinName.toLowerCase());

        if (skin == null) {
            sender.sendMessage(
                    language.getMessage("skin.can\'t_find_an_skin_with_this_username")
            );
            return false;
        }

        if (skin.getUserId() != null && skin.getUserId().equals(user.getId())) {
            user.getSkins().stream()
                    .filter(Skin::isActive)
                    .forEach(Skin::deactivate);

            skin.active();
        } else {
            user.setSkin(skin);
        }

        SkinManager.openBook(
                player,
                skinName
        );

        return true;
    }

    public static void openBook(Player player, String skinName) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        ItemStack itemStack = CustomBook.writtenBook()
                .title("Skin")
                .author(player.getName())
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

        CustomBook.openPlayer(
                player,
                itemStack
        );
    }
}
