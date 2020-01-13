package com.redecommunity.api.spigot.skin.inventory;

import com.redecommunity.api.spigot.inventory.CustomInventory;
import com.redecommunity.api.spigot.inventory.item.CustomItem;
import com.redecommunity.common.shared.permissions.user.data.User;
import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 * Created by @SrGutyerrez
 */
public class SkinInventory extends CustomInventory {
    public SkinInventory(User user) {
        super("Skins", 6);

        user.getSkins().forEach(skin -> {
            this.addItem(
                    new CustomItem(Material.SKULL_ITEM)
                            .data(3)
                            .name("§e" + skin.getOwner())
                            .owner(skin.getTexture())
            );
        });

        this.setItem(
                49,
                new CustomItem(Material.BOOK_AND_QUILL)
                        .name("§cTestando")
                        .lore(
                                "SAKDASKDAS",
                                "SAK@OKSAD"
                        )
                        .editable(false)
                        .onClick(event -> {
                            Player player = (Player) event.getWhoClicked();

                            System.out.println("Clicou porra!!!");

                            player.closeInventory();
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
