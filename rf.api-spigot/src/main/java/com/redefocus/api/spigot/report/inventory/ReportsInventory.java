package com.redefocus.api.spigot.report.inventory;

import com.google.common.collect.Lists;
import com.redefocus.api.spigot.inventory.CustomPaginateInventory;
import com.redefocus.api.spigot.teleport.data.TeleportRequest;
import com.redefocus.api.spigot.SpigotAPI;
import com.redefocus.api.spigot.inventory.item.CustomItem;
import com.redefocus.common.shared.permissions.user.data.User;
import com.redefocus.common.shared.permissions.user.manager.UserManager;
import com.redefocus.common.shared.skin.data.Skin;
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

        this.setCancelled(true);

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
                            .data(3)
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

                                teleportRequest.start();

                                player.closeInventory();
                            });

                    this.addItem(
                            customItem
                    );
                });
    }
}
