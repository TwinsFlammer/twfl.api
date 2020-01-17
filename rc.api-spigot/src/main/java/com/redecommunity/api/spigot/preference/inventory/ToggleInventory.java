package com.redecommunity.api.spigot.preference.inventory;

import com.redecommunity.api.spigot.inventory.CustomInventory;
import com.redecommunity.api.spigot.inventory.CustomPaginateInventory;
import com.redecommunity.api.spigot.inventory.item.CustomItem;
import com.redecommunity.common.shared.permissions.group.GroupNames;
import com.redecommunity.common.shared.permissions.user.data.User;
import com.redecommunity.common.shared.preference.Preference;
import org.bukkit.Material;

import java.util.Arrays;

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

        Arrays.stream(Preference.values())
                .filter(preference -> preference.getPage().equals(page))
                .forEach(preference -> {
                    if (preference != Preference.CHAT_STAFF && preference != Preference.JOIN_MESSAGE) {
                        this.setItem(
                                user,
                                preference
                        );
                    } else if (preference == Preference.CHAT_STAFF && user.hasGroup(GroupNames.HELPER)) {
                        this.setItem(
                                user,
                                preference
                        );
                    } else if (preference == Preference.JOIN_MESSAGE && user.hasGroup(GroupNames.HELPER)) {
                        this.setItem(
                                user,
                                preference
                        );
                    }
                });

        if (page != 1) {
            CustomInventory customInventory = new ToggleInventory(user, page - 1).build();

            CustomItem previousItem = this.getPaginateItem(customInventory, false);

            Integer previousSlot = this.getPreviousSlot();

            this.setItem(
                    previousSlot,
                    previousItem
            );
        }

        if (page != 3) {
            CustomInventory customInventory = new ToggleInventory(user, page + 1).build();

            CustomItem nextItem = this.getPaginateItem(customInventory, true);

            Integer nextSlot = this.getNextSlot();

            this.setItem(
                    nextSlot,
                    nextItem
            );
        }
    }

    private void setItem(User user, Preference preference) {
        Boolean value = user.isEnabled(preference);

        this.setItem(
                preference.getSlot(),
                new CustomItem(Material.getMaterial(preference.getId()))
                        .data(preference.getData())
                        .name("§" + preference.getColor(user) + preference.getDisplayName())
                        .lore(preference.getDescription())
                        .onClick(event -> {
                            user.togglePreference(
                                    preference,
                                    value
                            );
                        })
        );
        this.setItem(
                preference.getStatusSlot(),
                new CustomItem(Material.INK_SACK)
                        .data(value ? 10 : 8)
                        .name("§" + preference.getColor(user) + preference.getDisplayName())
                        .lore(preference.getDescription())
                        .onClick(event -> {
                            user.togglePreference(
                                    preference,
                                    value
                            );
                        })
        );
    }
}
