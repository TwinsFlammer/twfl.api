package com.redecommunity.api.spigot.hologram.line;

import com.google.common.collect.Maps;
import com.redecommunity.api.spigot.hologram.data.CustomHologram;
import com.redecommunity.common.spigot.packet.wrapper.AbstractPacket;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Stream;

@RequiredArgsConstructor
public abstract class AbstractHologramLine {

    public static final Map<Integer, AbstractHologramLine> ID_TO_LINE = Maps.newHashMap();

    @Getter
    private final double height;

    @Getter
    @Setter
    private CustomHologram parent;

    private boolean spawned;

    public boolean hasSpawned() {
        return spawned;
    }

    public void spawn(Location location) {
        if (hasSpawned()) {
            despawn();
        }

        spawned = true;
    }

    public void despawn() {
        spawned = false;
    }

    public abstract void teleport(Location location);

    public abstract AbstractPacket buildCreatePacket();

    public void sendCreatePacket(Stream<Player> stream) {
        AbstractPacket container = buildCreatePacket();
        stream.forEach(container::sendPacket);
    }

    public void sendCreatePacket(Player... players) {
        sendCreatePacket(Stream.of(players));
    }

    public void sendCreatePacket(Collection<Player> players) {
        sendCreatePacket(players.stream());
    }

    public abstract AbstractPacket buildUpdatePacket();

    public void sendUpdatePacket(Stream<Player> stream) {
        AbstractPacket packet = buildUpdatePacket();
        stream.forEach(packet::sendPacket);
    }

    public void sendUpdatePacket(Player... players) {
        sendUpdatePacket(Stream.of(players));
    }

    public void sendUpdatePacket(Collection<Player> players) {
        sendUpdatePacket(players.stream());
    }

    public abstract int[] getEntityIds();

    protected void registerEntity(Entity entity) {
        ID_TO_LINE.put(entity.getEntityId(), this);
    }
}
