package com.redecommunity.api.spigot.skin.inventory;

import com.google.common.collect.Maps;
import com.redecommunity.api.spigot.inventory.CustomPaginateInventory;
import com.redecommunity.api.spigot.inventory.item.BlockColor;
import com.redecommunity.api.spigot.inventory.item.CustomItem;
import com.redecommunity.api.spigot.skin.command.argument.SkinHelpCommand;
import com.redecommunity.api.spigot.skin.manager.SkinManager;
import com.redecommunity.common.shared.language.enums.Language;
import com.redecommunity.common.shared.permissions.user.data.User;
import com.redecommunity.common.shared.permissions.user.manager.UserManager;
import com.redecommunity.common.shared.skin.dao.SkinDao;
import com.redecommunity.common.shared.skin.data.Skin;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.HashMap;

/**
 * Created by @SrGutyerrez
 */
public class SkinInventory extends CustomPaginateInventory {
    public SkinInventory(User user) {
        super("Skins", 6);

        Language language = user.getLanguage();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("DD/MM/YYYY");

        user.getSkins()
                .stream()
                .sorted((skin1, skin2) -> skin2.getLastUse().compareTo(skin1.getLastUse()))
                .forEach(skin -> {
                    this.addItem(
                            new CustomItem(Material.SKULL_ITEM)
                                    .data(3)
                                    .name("§e" + skin.getOwner())
                                    .lore("§fUsada pela última vez em: §7" + simpleDateFormat.format(skin.getLastUse()),
                                            "",
                                            (skin.isActive() ? "§aSelecionada." : "§eClique para utilizar essa skin."))
                                    .owner(skin.getValue())
                                    .onClick(event -> {
                                        if (skin.isActive()) return;

                                        Player player = (Player) event.getWhoClicked();

                                        SkinDao skinDao = new SkinDao();

                                        HashMap<String, Object> keys = Maps.newHashMap();

                                        Skin active = user.getSkin();

                                        active.setActive(false);

                                        keys.put("active", false);

                                        skinDao.update(
                                                keys,
                                                "id",
                                                active.getId()
                                        );

                                        skin.setActive(true);
                                        skin.setLastUse(System.currentTimeMillis());

                                        keys.put("active", skin.isActive());
                                        keys.put("last_use", skin.getLastUse());

                                        skinDao.update(
                                                keys,
                                                "id",
                                                skin.getId()
                                        );

                                        User user1 = UserManager.getUser(skin.getOwner());

                                        SkinManager.openBook(
                                                player,
                                                user1 == null ? skin.getOwner() : user1.getDisplayName()
                                        );
                                    })
                    );
                });

        this.setItem(
                48,
                new CustomItem(Material.BOOK_AND_QUILL)
                        .editable(false)
                        .name("§eEscolher uma nova skin")
                        .lore(
                                "§7Você pode escolher uma nova skin",
                                "§7para ser utilizada em sua conta.",
                                "",
                                "§fComando: §7/skin <nome>",
                                "",
                                "§aClique para escolher."
                        )
                        .onClick(event -> {
                            Player player = (Player) event.getWhoClicked();

                            player.closeInventory();

                            player.sendMessage(
                                    language.getMessage("skin.write_the_name_of_skin")
                            );

                            user.setChangingSkin(true);
                        })
        );
        this.setItem(
                49,
                new CustomItem(Material.BARRIER)
                        .editable(false)
                        .name("§eAtualizar skin")
                        .lore("§7Isso irá restaurar a sua skin para a skin",
                                "§7utilizada em sua conta do Minecraft. Caso",
                                "§7você não possua uma conta ficará com a",
                                "§7skin padrão do minecraft.",
                                "",
                                "§fComando: §7/skin atualizar",
                                "",
                                "§aClique para atualizar")
                        .onClick(event -> {
                            Player player = (Player) event.getWhoClicked();

                            player.closeInventory();

                            SkinManager.change(
                                    player,
                                    user,
                                    player.getName(),
                                    true
                            );
                        })
        );
        this.setItem(
                50,
                new CustomItem(Material.SKULL_ITEM)
                        .editable(false)
                        .data(3)
                        .owner(BlockColor.CYAN)
                        .name("§eAjuda")
                        .lore("§7As ações disponíveis neste menu também",
                                "§7podem ser realizadas por comando.",
                                "",
                                "§fComando: §7/skin ajuda",
                                "",
                                "§aClique para listar os comandos")
                        .onClick(event -> {
                            Player player = (Player) event.getWhoClicked();

                            player.closeInventory();

                            new SkinHelpCommand().onCommand(
                                    player,
                                    user,
                                    null
                            );
                        })
        );

        this.setCancelled(true);

        this.setDesign(
                "XXXXXXXXX",
                "XOOOOOOOX",
                "XOOOOOOOX",
                "XOOOOOOOX",
                "XXXXXXXXX",
                "XXXXXXXXX"
        );
    }
}
