package com.redecommunity.api.spigot.hologram.line;

import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.redecommunity.api.spigot.hologram.factory.HologramFactory;
import com.redecommunity.common.spigot.packet.wrapper.AbstractPacket;
import com.redecommunity.common.spigot.packet.wrapper.WrapperPlayServerEntityMetadata;
import com.redecommunity.common.spigot.packet.wrapper.WrapperPlayServerSpawnEntity;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        List<Player> players = Bukkit.getOnlinePlayers()
                .stream()
                .filter(player -> !player.isDead())
                .collect(Collectors.toList());

        this.sendUpdatePacket(players);
    }

    @Override
    public void spawn(Location location) {
        super.spawn(location);

        this.entity = HologramFactory.spawnArmorStand(location, (ArmorStand armorStand) -> {
            armorStand.setVisible(false);
            armorStand.setMarker(false);

            this.registerEntity(armorStand);

            armorStand.setArms(false);
            armorStand.setGravity(false);
            armorStand.setBasePlate(false);
            armorStand.setSmall(true);
            armorStand.setCustomNameVisible(true);
            armorStand.setCustomName(ChatColor.GRAY + "Carregando...");
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
        this.entity.teleport(location);
    }

    @Override
    public AbstractPacket buildCreatePacket() {
        return new WrapperPlayServerSpawnEntity(this.entity, WrapperPlayServerSpawnEntity.ObjectTypes.ARMORSTAND, 0);
    }

    @Override
    public AbstractPacket buildUpdatePacket() {
        if (this.entity == null) return null;

        WrapperPlayServerEntityMetadata wrapper = new WrapperPlayServerEntityMetadata();
        wrapper.setEntityID(this.entity.getEntityId());

        WrappedDataWatcher watcher = WrappedDataWatcher.getEntityWatcher(this.entity);
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
                this.entity.getEntityId()
        };
    }
}
