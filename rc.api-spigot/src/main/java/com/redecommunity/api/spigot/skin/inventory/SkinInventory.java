package com.redecommunity.api.spigot.skin.inventory;

import com.redecommunity.api.spigot.inventory.CustomInventory;
import com.redecommunity.api.spigot.inventory.item.CustomItem;
import com.redecommunity.api.spigot.skin.command.argument.SkinHelpCommand;
import com.redecommunity.api.spigot.skin.manager.SkinManager;
import com.redecommunity.common.shared.language.enums.Language;
import com.redecommunity.common.shared.permissions.user.data.User;
import com.redecommunity.common.shared.skin.data.Skin;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by @SrGutyerrez
 */
public class SkinInventory extends CustomInventory {
    public SkinInventory(User user) {
        super("Skins", 6);

        Language language = user.getLanguage();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("DD/MM/YYYY");

        for (int i = 0; i < 50; i++) {
            user.getSkins()
                    .stream()
                    .sorted((o1, o2) -> o2.getLastUse().compareTo(o1.getLastUse()))
                    .forEach(skin -> {
                        this.addItem(
                                new CustomItem(Material.SKULL_ITEM)
                                        .data(3)
                                        .name("§e" + skin.getOwner())
                                        .lore("§fUsada pela última vez em: §7" + simpleDateFormat.format(skin.getLastUse()),
                                                "",
                                                (skin.isActive() ? "§aSelecionada." : "§eClique para utilizar essa skin."))
                                        .owner(skin.getValue())
                        );
                    });
        }

        this.setItem(
                48,
                new CustomItem(Material.BOOK_AND_QUILL)
                        .name("§eEscolher uma nova skin")
                        .lore(
                                "§7Você pode escolher uma nova skin",
                                "§7para ser utilizada em sua conta.",
                                "",
                                "§fComando: §7/skin <nome>",
                                "",
                                "§aClique para escolher."
                        )
                        .editable(false)
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
                                    player.getName()
                            );
                        })
        );
        this.setItem(
                50,
                new CustomItem(Material.SKULL_ITEM)
                        .data(3)
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
