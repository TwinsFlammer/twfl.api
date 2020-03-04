package com.redecommunity.api.spigot.hologram.line;

import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.redecommunity.api.spigot.hologram.factory.HologramFactory;
import com.redecommunity.common.shared.util.Helper;
import com.redecommunity.common.spigot.packet.wrapper.AbstractPacket;
import com.redecommunity.common.spigot.packet.wrapper.WrapperPlayServerEntityMetadata;
import com.redecommunity.common.spigot.packet.wrapper.WrapperPlayServerSpawnEntity;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

import java.util.stream.Stream;
import org.bukkit.ChatColor;

public class TextHologramLine extends AbstractHologramLine {

    protected String text;

    @Getter
    private ArmorStand entity;

    public TextHologramLine(String text) {
        super(0.23D);

        this.text = text;
    }

    public String getText() {
        return text;
    }

    public String getText(Player player) {
        return getText();
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void spawn(Location location) {
        super.spawn(location);

        this.entity = HologramFactory.spawnArmorStand(location, (ArmorStand armorStand) -> {
            armorStand.setVisible(false);
            armorStand.setMarker(true);
            registerEntity(armorStand);

            armorStand.setArms(false);
            armorStand.setGravity(false);
            armorStand.setBasePlate(false);
            armorStand.setSmall(true);
            armorStand.setCustomNameVisible(true);
            armorStand.setCustomName(Helper.colorize(this.text));
        });
    }

    @Override
    public void despawn() {
        super.despawn();

        this.entity.remove();
        this.entity = null;
    }

    @Override
    public void teleport(Location location) {
        entity.teleport(location);
    }

    @Override
    public AbstractPacket buildCreatePacket() {
        return new WrapperPlayServerSpawnEntity(entity, WrapperPlayServerSpawnEntity.ObjectTypes.ARMORSTAND, 0);
    }

    @Override
    public AbstractPacket buildUpdatePacket() {
        WrapperPlayServerEntityMetadata wrapper = new WrapperPlayServerEntityMetadata();
        wrapper.setEntityID(entity.getEntityId());

        WrappedDataWatcher watcher = WrappedDataWatcher.getEntityWatcher(entity);
        wrapper.setMetadata(watcher.getWatchableObjects());
        return wrapper;
    }

    @Override
    public void sendUpdatePacket(Stream<Player> stream) {

        super.sendUpdatePacket(stream);
    }

    @Override
    public int[] getEntityIds() {
        return new int[]{
            entity.getEntityId()
        };
    }
}
