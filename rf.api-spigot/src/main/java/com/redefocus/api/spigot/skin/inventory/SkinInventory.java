package com.redefocus.api.spigot.skin.inventory;

import com.redefocus.api.spigot.inventory.CustomPaginateInventory;
import com.redefocus.api.spigot.inventory.item.BlockColor;
import com.redefocus.api.spigot.inventory.item.CustomItem;
import com.redefocus.api.spigot.skin.command.argument.SkinHelpCommand;
import com.redefocus.api.spigot.skin.manager.SkinManager;
import com.redefocus.common.shared.language.enums.Language;
import com.redefocus.common.shared.permissions.user.data.User;
import com.redefocus.common.shared.permissions.user.manager.UserManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;

/**
 * Created by @SrGutyerrez
 */
public class SkinInventory extends CustomPaginateInventory {
    public SkinInventory(User user) {
        super(
                "Skins",
                6,
                "XXXXXXXXX",
                "XOOOOOOOX",
                "XOOOOOOOX",
                "XOOOOOOOX",
                "XXXXXXXXX",
                "XXXXXXXXX"
        );

        this.setCancelled(true);

        Language language = user.getLanguage();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("DD/MM/YYYY");

        user.getSkins()
                .stream()
                .sorted((skin1, skin2) -> skin2.getLastUse().compareTo(skin1.getLastUse()))
                .sorted((skin1, skin2) -> skin2.isActive().compareTo(skin1.isActive()))
                .forEach(skin -> {
                    User user1 = UserManager.getUser(skin.getOwner());

                    this.addItem(
                            new CustomItem(Material.SKULL_ITEM)
                                    .data(3)
                                    .name("§e" + (user1 == null ? skin.getOwner() : user1.getDisplayName()))
                                    .lore("§fUsada pela última vez em: §7" + simpleDateFormat.format(skin.getLastUse()),
                                            "",
                                            (skin.isActive() ? "§aSelecionada." : "§eClique para utilizar essa skin."))
                                    .owner(skin.getValue())
                                    .onClick(event -> {
                                        if (skin.isActive()) return;

                                        Player player = (Player) event.getWhoClicked();

                                        SkinManager.change(
                                                player,
                                                user,
                                                skin.getOwner()
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

                            new Thread(() -> {
                                SkinManager.change(
                                        player,
                                        user,
                                        player.getName(),
                                        true
                                );
                            }).start();
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
    }
}
