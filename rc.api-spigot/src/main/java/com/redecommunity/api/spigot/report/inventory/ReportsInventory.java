package com.redecommunity.api.spigot.report.inventory;

import com.google.common.collect.Lists;
import com.redecommunity.api.spigot.inventory.CustomPaginateInventory;
import com.redecommunity.api.spigot.teleport.data.TeleportRequest;
import com.redecommunity.api.spigot.SpigotAPI;
import com.redecommunity.api.spigot.inventory.item.CustomItem;
import com.redecommunity.common.shared.permissions.user.data.User;
import com.redecommunity.common.shared.permissions.user.manager.UserManager;
import com.redecommunity.common.shared.skin.data.Skin;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Created by @SrGutyerrez
 */
public class ReportsInventory extends CustomPaginateInventory {
    public ReportsInventory() {
        super(
                "Denúncias",
                5,
                "XXXXXXXXX",
                "XXOOOOOXX",
                "XXOOOOOXX",
                "XXOOOOOXX",
                "XXXXXXXXX"
        );

        SpigotAPI.getUsers()
                .stream()
                .filter(User::isOnline)
                .filter(user -> !user.getReports().isEmpty())
                .forEach(user -> {
                    Skin skin = user.getSkin();

                    List<String> lore = Lists.newArrayList();
                    List<Integer> alreadyInserted = Lists.newArrayList();

                    user.getReports().forEach(reportReason -> {
                        Integer count = (int) user.getReports()
                                .stream()
                                .filter(reportReason1 -> reportReason1.getId().equals(reportReason.getId()))
                                .count();

                        if (!alreadyInserted.contains(reportReason.getId())) {
                            alreadyInserted.add(reportReason.getId());

                            lore.add("  §f• " + reportReason.getDisplayName() + ": §7" + count);
                        }
                    });

                    CustomItem customItem = new CustomItem(Material.SKULL_ITEM)
                            .owner(skin == null ? user.getDisplayName() : skin.getValue())
                            .name(user.getPrefix() + user.getDisplayName())
                            .lore(lore)
                            .onClick(event -> {
                                Player player = (Player) event.getWhoClicked();
                                User user1 = UserManager.getUser(player.getUniqueId());

                                TeleportRequest teleportRequest = new TeleportRequest(
                                        user1.getId(),
                                        user.getId(),
                                        null,
                                        user.getServer().getId(),
                                        0L
                                );
                            });

                    this.addItem(
                            customItem
                    );
                });
    }
}
