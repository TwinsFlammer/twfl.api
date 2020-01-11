package com.redecommunity.api.spigot.skin.inventory;

import com.redecommunity.api.spigot.inventory.CustomInventory;
import com.redecommunity.api.spigot.inventory.item.CustomItem;
import com.redecommunity.common.shared.permissions.user.data.User;
import org.bukkit.Material;

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
        );

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
