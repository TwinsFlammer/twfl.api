package com.redecommunity.api.spigot.hologram;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedWatchableObject;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.redecommunity.api.spigot.hologram.line.AbstractHologramLine;
import com.redecommunity.api.spigot.hologram.line.TextHologramLine;
import com.redecommunity.common.shared.util.Helper;
import com.redecommunity.common.spigot.packet.wrapper.WrapperPlayServerEntityDestroy;
import com.redecommunity.common.spigot.packet.wrapper.WrapperPlayServerEntityMetadata;
import com.redecommunity.common.spigot.packet.wrapper.WrapperPlayServerSpawnEntity;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class HologramProtocol extends PacketAdapter {

    public final static Multimap<String, AbstractHologramLine> INVISIBLE = HashMultimap.create();

    public HologramProtocol(Plugin plugin) {
        super(PacketAdapter
                .params()
                .plugin(plugin)
                .types(
                        PacketType.Play.Server.SPAWN_ENTITY,
                        PacketType.Play.Server.ENTITY_METADATA)
                .clientSide()
                .serverSide()
                .listenerPriority(ListenerPriority.HIGHEST)
        );
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        PacketContainer packet = event.getPacket();
        // spawn
        if (packet.getType() == PacketType.Play.Server.SPAWN_ENTITY) {
            WrapperPlayServerSpawnEntity spawnEntityPacket = new WrapperPlayServerSpawnEntity(packet);
            Entity entity = spawnEntityPacket.getEntity(event);

            if (entity == null || !(entity instanceof ArmorStand)) {
                return;
            }

            AbstractHologramLine hologram = getHologramLine(entity);

            if (hologram == null || !(hologram instanceof TextHologramLine)) {
                return;
            }

            String text = ((TextHologramLine) hologram).getText(event.getPlayer());

            // handshake
            // se não houver texto o holograma não é spawnado no cliente do player
            // é então adicionado a uma lista para não receber updates desse holograma
            if (text == null) {
                event.setCancelled(true);
                INVISIBLE.put(event.getPlayer().getName(), hologram);
                return;
            }

            INVISIBLE.remove(event.getPlayer().getName(), hologram);
            //update
        } else if (packet.getType() == PacketType.Play.Server.ENTITY_METADATA) {

            WrapperPlayServerEntityMetadata entityMetadataPacket = new WrapperPlayServerEntityMetadata(packet);
            Entity entity = entityMetadataPacket.getEntity(event);

            if (entity == null || !(entity instanceof ArmorStand)) {
                return;
            }

            AbstractHologramLine hologram = getHologramLine(entity);

            if (hologram == null || !(hologram instanceof TextHologramLine)) {
                return;
            }

            String text = ((TextHologramLine) hologram).getText(event.getPlayer());

            boolean invisible = INVISIBLE.containsEntry(event.getPlayer().getName(), hologram);

            if (text == null) {
                event.setCancelled(true);

                if (!invisible) {
                    INVISIBLE.put(event.getPlayer().getName(), hologram);
                    WrapperPlayServerEntityDestroy destroy = new WrapperPlayServerEntityDestroy();
                    if (((TextHologramLine) hologram).getEntity() != null) {
                        destroy.setEntityIds(hologram.getEntityIds());
                        destroy.sendPacket(event.getPlayer());
                    }
                }

                return;
            }

            text = Helper.colorize(text);

            if (!entity.getCustomName().contains("Carreg") && event.getPlayer().isOp()) {
                //Printer.INFO.print(entity.getCustomName());
            }

            if (invisible) {
                INVISIBLE.remove(event.getPlayer().getName(), hologram);
                WrapperPlayServerSpawnEntity spawnEntityPacket = (WrapperPlayServerSpawnEntity) hologram.buildCreatePacket();
                spawnEntityPacket.sendPacket(event.getPlayer());
            }

            List<WrappedWatchableObject> objectList = Lists.newArrayList();

            for (WrappedWatchableObject object : entityMetadataPacket.getMetadata()) {
                if (object.getIndex() == 2) {
                    WrappedWatchableObject newObject = HologramUtils.buildWatchableObject(2, text);
                    objectList.add(newObject);
                } else {
                    objectList.add(object);
                }
            }

            entityMetadataPacket.setMetadata(objectList);
        }
    }

    public static AbstractHologramLine getHologramLine(Entity entity) {
        return AbstractHologramLine.ID_TO_LINE.get(entity.getEntityId());
    }
}
