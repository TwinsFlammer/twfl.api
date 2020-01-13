package com.redecommunity.api.spigot.skin.inventory;

import com.redecommunity.api.spigot.inventory.CustomInventory;
import com.redecommunity.api.spigot.inventory.item.CustomItem;
import com.redecommunity.common.shared.permissions.user.data.User;
import org.bukkit.Material;

import java.text.SimpleDateFormat;

/**
 * Created by @SrGutyerrez
 */
public class SkinInventory extends CustomInventory {
    public SkinInventory(User user) {
        super("Skins", 6);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("DD/MM/YYYY");

        user.getSkins().forEach(skin -> {
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
