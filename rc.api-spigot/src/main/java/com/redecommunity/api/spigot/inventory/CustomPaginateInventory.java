package com.redecommunity.api.spigot.inventory;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.redecommunity.api.spigot.inventory.item.CustomItem;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;

/**
 * Created by @SrGutyerrez
 */
public class CustomPaginateInventory {
    private List<CustomInventory> pages = Lists.newArrayList();

    @Getter
    private HashMap<Integer, CustomItem> customItems = Maps.newHashMap();

    public CustomPaginateInventory(String name, Integer rows) {
        CustomInventory customInventory = new CustomInventory(
                name,
                rows
        );

        this.pages.add(customInventory);
    }

    private CustomInventory getCurrentInventory() {
        return this.pages.get(this.pages.size() - 1);
    }

    public CustomPaginateInventory setItem(Integer index, CustomItem customItem) {
        this.customItems.put(
                index,
                customItem
        );

        return this;
    }

    public CustomPaginateInventory addItem(CustomItem customItem) {
        this.validate();

        this.getCurrentInventory().addItem(
                customItem
        );

        return this;
    }

    public CustomPaginateInventory setDesign(String... design) {
        this.pages.forEach(customInventory -> customInventory.setDesign(design));

        return this;
    }

    public CustomPaginateInventory setCancelled(Boolean cancelled) {
        this.pages.forEach(customInventory -> customInventory.setCancelled(cancelled));

        return this;
    }

    public CustomInventory build() {
        return this.pages.get(0);
    }

    private void validate() {
        CustomInventory customInventory = this.getCurrentInventory();

        this.customItems.forEach(customInventory::setItem);

        if (customInventory.getItemCount() + 1 >= customInventory.getSize()) {
            CustomInventory customInventory1 = new CustomInventory(
                    customInventory.getName(),
                    customInventory.getRows()
            );

            CustomItem previousItem = this.getNextItem(customInventory, false);
            CustomItem nextItem = this.getNextItem(customInventory1, true);

            Integer previousSlot = this.getPreviousSlot();
            Integer nextSlot = this.getNextSlot();

            customInventory1.setItem(
                    previousSlot,
                    previousItem
            );

            customInventory.setItem(
                    nextSlot,
                    nextItem
            );

            this.pages.add(customInventory1);
        }
    }

    private Integer getPreviousSlot() {
        switch (this.getCurrentInventory().getRows()) {
            case 2:
            case 3:
            case 4:
                return 9;
            case 5:
            case 6:
                return 18;
            default:
                return 0;
        }
    }

    private Integer getNextSlot() {
        switch (this.getCurrentInventory().getRows()) {
            case 1:
                return 8;
            case 2:
            case 3:
            case 4:
                return 17;
            case 5:
            case 6:
                return 26;
            default:
                return 0;
        }
    }

    private CustomItem getNextItem(CustomInventory customInventory, Boolean next) {
        return new CustomItem(Material.ARROW)
                .editable(false)
                .name("§aPágina " + (next ? this.pages.size() + 1 : this.pages.size()))
                .onClick(event -> {
                    Player player = (Player) event.getWhoClicked();

                    player.openInventory(customInventory);
                });
    }
}
