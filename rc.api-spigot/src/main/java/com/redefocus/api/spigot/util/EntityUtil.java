package com.redefocus.api.spigot.util;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class EntityUtil {

    public static Entity getLookingAt(Player player, EntityType entityType) {
        return EntityUtil.getLookingAt(player, entityType, 5);
    }

    public static Entity getLookingAt(Player player, EntityType entityType, int range) {
        Vector direction = player.getLocation().getDirection();
        Location location = player.getEyeLocation().clone();

        location.add(direction.getX(), direction.getY(), direction.getZ());
        try {
            for (int i = 1; i <= range; i++) {
                location.add(direction.getX(), direction.getY(), direction.getZ());

                Collection<Entity> nearby = location.getWorld().getNearbyEntities(location, 1, 1, 1);

                if (nearby != null && !nearby.isEmpty()) {
                    Optional<Entity> entityFound = nearby.stream()
                            .filter(Objects::nonNull)
                            .filter(ent -> ent.getType().equals(entityType))
                            .findFirst();
                    if (entityFound.isPresent()) {
                        return entityFound.get();
                    }
                }
            }
        } catch (NullPointerException e) {
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
