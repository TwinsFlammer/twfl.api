package com.redecommunity.api.spigot.hologram;

import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;
import com.redecommunity.api.spigot.hologram.line.AbstractHologramLine;
import com.redecommunity.api.spigot.hologram.line.TextHologramLine;
import com.redecommunity.api.spigot.hologram.touch.TouchEvent;
import com.redecommunity.common.spigot.packet.wrapper.WrapperPlayServerEntityDestroy;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CustomHologram {

    public static final double LINE_SPACING = 0.05D;

    private final List<AbstractHologramLine> lines = Lists.newLinkedList();

    @Getter
    private Location location;

    @Setter
    @Getter
    private TouchEvent touchEvent;

    private EnumHologramDirection direction = EnumHologramDirection.UP;

    private boolean spawned = false;

    private long createTime = System.currentTimeMillis();

    public CustomHologram(Location location) {
        this.location = location;
    }

    public CustomHologram(Location location, EnumHologramDirection direction) {
        this(location);

        this.direction = direction;
    }

    public void refreshLines() {
        Location root = location.clone();
        boolean first = true;

        for (AbstractHologramLine hologramLine : lines) {
            if (first) {
                first = false;
            } else {
                root.add(0, (hologramLine.getHeight() + LINE_SPACING) * direction.getModifier(), 0);
            }

            if (hologramLine.hasSpawned()) {
                hologramLine.teleport(root);
            } else {
                hologramLine.spawn(root);
            }
        }
    }

    public void sendDestroyPacket(Player player) {
        if (hasSpawned() && !lines.isEmpty()) {
            int[] ids = Ints.concat(lines.stream().map(AbstractHologramLine::getEntityIds).toArray(int[][]::new));
            WrapperPlayServerEntityDestroy wrapper = new WrapperPlayServerEntityDestroy();
            wrapper.setEntityIds(ids);
            wrapper.sendPacket(player);
        }
    }

    public void sendCreatePacket(Player player) {
        if (hasSpawned()) {
            lines.forEach(hologramLine -> hologramLine.sendCreatePacket(player));
        }
    }

    public boolean hasSpawned() {
        return spawned;
    }

    public AbstractHologramLine getLine(int index) {
        return lines.size() <= index ? null : lines.get(index);
    }

    public void appendLine(AbstractHologramLine abstractHologramLine) {
        abstractHologramLine.setParent(this);
        this.lines.add(abstractHologramLine);

        if (hasSpawned()) {
            refreshLines();
        }
    }

    public void appendLines(String... lines) {
        Arrays.asList(lines).forEach(line -> {
            TextHologramLine textHologramLine = new TextHologramLine(line);

            this.appendLine(textHologramLine);
        });
    }

    public void updateLines(String... lines) {
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];

            AbstractHologramLine abstractHologramLine = this.getLine(i);

            if (abstractHologramLine == null) continue;

            if (abstractHologramLine instanceof TextHologramLine) {
                TextHologramLine textHologramLine = (TextHologramLine) abstractHologramLine;

                textHologramLine.setText(line);
            }
        }
    }

    public boolean removeLine(int index) {
        AbstractHologramLine line = this.lines.remove(index);

        if (line != null) {
            if (hasSpawned()) {
                line.despawn();
                refreshLines();
            }

            return true;
        }

        return false;
    }

    public boolean hasLine(AbstractHologramLine line) {
        return this.lines.contains(line);
    }

    public boolean removeLine(AbstractHologramLine line) {
        if (this.lines.remove(line)) {
            if (hasSpawned()) {
                line.despawn();
                refreshLines();
            }

            return true;
        }

        return false;
    }

    public void setDirection(EnumHologramDirection direction) {
        this.direction = direction;

        if (hasSpawned()) {
            refreshLines();
        }
    }

    public void spawn() {
        this.spawned = true;
        refreshLines();
    }

    public void despawn() {
        this.spawned = false;

        lines.forEach(AbstractHologramLine::despawn);
    }

    public void teleport(Location location) {
        this.location = location;

        if (hasSpawned()) {
            refreshLines();
        }
    }

    public void dispatchTouch(Player player) {
        if (touchEvent != null) {
            this.touchEvent.onTouch(player);
        }
    }

    public int size() {
        return lines.size();
    }

    public double getHeight() {
        double sum = lines.stream().map(AbstractHologramLine::getHeight).collect(Collectors.summingDouble(d -> d));
        return sum + (LINE_SPACING * (lines.size() - 1));
    }

    @RequiredArgsConstructor
    public static enum EnumHologramDirection {
        UP(1),
        DOWN(-1);

        @Getter
        private final double modifier;
    }
}
