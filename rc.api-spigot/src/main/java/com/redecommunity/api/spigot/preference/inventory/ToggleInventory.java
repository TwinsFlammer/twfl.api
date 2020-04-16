package com.redecommunity.api.spigot.preference.inventory;

import com.redecommunity.api.spigot.inventory.CustomInventory;
import com.redecommunity.api.spigot.inventory.CustomPaginateInventory;
import com.redecommunity.api.spigot.inventory.item.CustomItem;
import com.redecommunity.api.spigot.preference.event.PreferenceStateChangeEvent;
import com.redecommunity.common.shared.cooldown.manager.CooldownManager;
import com.redecommunity.common.shared.permissions.group.GroupNames;
import com.redecommunity.common.shared.permissions.user.data.User;
import com.redecommunity.common.shared.preference.Preference;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Arrays;
import java.util.function.Consumer;

/**
 * Created by @SrGutyerrez
 */
public class ToggleInventory extends CustomPaginateInventory {
    public ToggleInventory(User user, Integer page) {
        super(
                "Preferências",
                4
        );

        this.setCancelled(true);
        this.setCustomInventory(true);

        Arrays.stream(Preference.values())
                .filter(preference -> preference.getPage().equals(page))
                .forEach(preference -> {
                    if (preference != Preference.CHAT_STAFF && preference != Preference.JOIN_MESSAGE) {
                        this.setItem(
                                user,
                                preference
                        );
                    } else if (preference == Preference.CHAT_STAFF) {
                        if (user.hasGroup(GroupNames.HELPER))
                            this.setItem(
                                    user,
                                    preference
                            );
                        else {
                            this.setItem(
                                    user,
                                    preference,
                                    false
                            );
                        }
                    } else if (user.hasGroup(GroupNames.HELPER)) {
                        this.setItem(
                                user,
                                preference
                        );
                    } else {
                        this.setItem(
                                user,
                                preference,
                                false
                        );
                    }
                });

        if (page > 1) {
            CustomItem previousItem = this.getPaginateItem(null, false)
                    .name("§aPágina " + (page - 1))
                    .onClick(event -> {
                        Player player = (Player) event.getWhoClicked();

                        CustomInventory customInventory = new ToggleInventory(user, page - 1).build();

                        player.openInventory(customInventory);
                    });

            Integer previousSlot = this.getPreviousSlot();

            this.setItem(
                    previousSlot,
                    previousItem
            );
        }

        if (page < 3) {
            CustomItem nextItem = this.getPaginateItem(null, false)
                    .name("§aPágina " + (page + 1))
                    .onClick(event -> {
                        Player player = (Player) event.getWhoClicked();

                        CustomInventory customInventory = new ToggleInventory(user, page + 1).build();

                        player.openInventory(customInventory);
                    });

            Integer nextSlot = this.getNextSlot();

            this.setItem(
                    nextSlot,
                    nextItem
            );
        }
    }

    private void setItem(User user, Preference preference) {
        this.setItem(
                user,
                preference,
                true
        );
    }

    private void setItem(User user, Preference preference, Boolean has) {
        Boolean value = user.isEnabled(preference);

        if (!has) {
            this.setItem(
                    preference.getSlot(),
                    new CustomItem(Material.BARRIER)
                            .name("§c" + preference.getDisplayName())
                            .lore(preference.getDescription())
            );
            this.setItem(
                    preference.getStatusSlot(),
                    new CustomItem(Material.BARRIER)
                            .name("§c" + preference.getDisplayName())
                            .lore(preference.getDescription())
            );
            return;
        }

        Consumer<InventoryClickEvent> inventoryClickEventConsumer = (event) -> {
            if (CooldownManager.inCooldown(user, preference)) return;

            user.togglePreference(
                    preference,
                    value
            );

            PreferenceStateChangeEvent preferenceStateChangeEvent = new PreferenceStateChangeEvent(
                    user,
                    preference
            );

            preferenceStateChangeEvent.run();

            if (preferenceStateChangeEvent.isCancelled()) {
                user.togglePreference(
                        preference,
                        user.isEnabled(preference)
                );
                return;
            }

            this.setItem(
                    user,
                    preference,
                    true
            );
        };

        this.setItem(
                preference.getSlot(),
                new CustomItem(Material.getMaterial(preference.getId()))
                        .data(preference.getData())
                        .name("§" + preference.getColor(user) + preference.getDisplayName())
                        .lore(preference.getDescription())
                        .onClick(inventoryClickEventConsumer)
        );
        this.setItem(
                preference.getStatusSlot(),
                new CustomItem(Material.INK_SACK)
                        .data(value ? 10 : 8)
                        .name("§" + preference.getColor(user) + preference.getDisplayName())
                        .lore(preference.getDescription())
                        .onClick(inventoryClickEventConsumer)
        );
    }
}
