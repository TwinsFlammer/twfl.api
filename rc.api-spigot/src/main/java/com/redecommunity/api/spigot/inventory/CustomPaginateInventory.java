package com.redecommunity.api.spigot.inventory;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.redecommunity.api.spigot.inventory.item.CustomItem;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

import java.util.HashMap;
import java.util.List;

/**
 * Created by @SrGutyerrez
 */
public class CustomPaginateInventory {
    private List<CustomInventory> pages = Lists.newArrayList();

    @Setter
    private Boolean cancelled = false;

    @Getter
    private HashMap<Integer, CustomItem> customItems = Maps.newHashMap();

    @Getter
    protected String[] design;

    public CustomPaginateInventory(String name, Integer rows) {
        CustomInventory customInventory = new CustomInventory(
                name,
                rows
        );

        this.pages.add(customInventory);
    }

    public CustomPaginateInventory(String name, Integer rows, String... design) {
        CustomInventory customInventory = new CustomInventory(
                name,
                rows,
                design
        );

        this.pages.add(customInventory);

        this.design = design;
    }

    public CustomPaginateInventory(String name, InventoryType inventoryType) {
        CustomInventory customInventory = new CustomInventory(
                name,
                inventoryType
        );

        this.pages.add(customInventory);
    }

    public CustomPaginateInventory(String name, InventoryType inventoryType, String... design) {
        CustomInventory customInventory = new CustomInventory(
                name,
                inventoryType,
                design
        );

        this.pages.add(customInventory);

        this.design = design;
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

        System.out.println("Adiciona o item");

        this.getCurrentInventory().addItem(
                customItem
        );

        return this;
    }

    public CustomPaginateInventory setDesign(String... design) {
        this.pages.forEach(customInventory -> customInventory.setDesign(design));

        return this;
    }

    public CustomInventory build() {
        CustomInventory customInventory = this.pages.get(0);

        customInventory.setCancelled(this.cancelled);

        if (customInventory.isEmpty()) {
            Integer emptySlot = this.getEmptySlot();

            CustomItem emptyItem = this.getEmptyItem();

            customInventory.setItem(
                    emptySlot,
                    emptyItem
            );
        }
        this.customItems.forEach(customInventory::setItem);

        this.pages.forEach(CustomInventory::organize);

        return customInventory;
    }

    private void validate() {
        CustomInventory customInventory = this.getCurrentInventory();

        System.out.println(customInventory.getItemCount());
        System.out.println(customInventory.getMaxSize());

        if (customInventory.getItemCount() + 1 >= customInventory.getMaxSize()) {
            System.out.println("Cria inventário novo.");
            CustomInventory customInventory1 = new CustomInventory(
                    customInventory.getName(),
                    customInventory.getRows(),
                    this.getDesign()
            );

            customInventory1.setCancelled(this.cancelled);

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

    private Integer getEmptySlot() {
        switch (this.getCurrentInventory().getRows()) {
            case 1:
                return 4;
            case 2:
            case 3:
            case 4:
                return 13;
            case 5:
            case 6:
                return 22;
            default:
                return 0;
        }
    }

    private CustomItem getNextItem(CustomInventory customInventory, Boolean next) {
        return new CustomItem(Material.ARROW)
                .editable(false)
                .cancelled(true)
                .name("§aPágina " + (next ? this.pages.size() + 1 : this.pages.size()))
                .onClick(event -> {
                    Player player = (Player) event.getWhoClicked();

                    this.customItems.forEach(customInventory::setItem);

                    player.openInventory(customInventory);
                });
    }


    private CustomItem getEmptyItem() {
        return new CustomItem(Material.WEB)
                .editable(false)
                .cancelled(true)
                .name("§c--/--");
    }
}
