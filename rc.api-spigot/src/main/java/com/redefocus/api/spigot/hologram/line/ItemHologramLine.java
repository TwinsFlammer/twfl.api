package com.redefocus.api.spigot.hologram.line;

import com.redefocus.common.spigot.packet.wrapper.AbstractPacket;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

public class ItemHologramLine extends AbstractHologramLine {

    @Getter
    private ItemStack itemStack;

    @Getter
    private Item entity;

    public ItemHologramLine(ItemStack itemStack) {
        super(0.7D);

        this.itemStack = itemStack;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;

        if (hasSpawned()) {
            entity.setItemStack(itemStack);
        }
    }

    @Override
    public void spawn(Location location) {
        super.spawn(location);

    }

    @Override
    public void despawn() {
        super.despawn();
    }

    @Override
    public void teleport(Location location) {

    }

    @Override
    public AbstractPacket buildCreatePacket() {
        return null;
    }

    @Override
    public AbstractPacket buildUpdatePacket() {
        return null;
    }

    @Override
    public int[] getEntityIds() {
        return new int[0];
    }
}
