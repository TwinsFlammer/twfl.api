package br.com.twinsflammer.api.spigot.hologram.factory;

import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.comphenix.protocol.wrappers.WrappedWatchableObject;
import br.com.twinsflammer.api.spigot.hologram.entity.Hologram;
import br.com.twinsflammer.api.spigot.hologram.util.NMS;
import net.minecraft.server.v1_8_R3.DataWatcher;
import net.minecraft.server.v1_8_R3.WorldServer;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventory;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.Inventory;

import java.util.function.Consumer;

/**
 *
 * @author Enzo
 */
public class HologramFactory {

    public static Object getIInventory(Inventory inventory) {
        return ((CraftInventory) inventory).getInventory();
    }

    public static <T extends Entity> T spawnEntity(Location location, Class<T> entityClass, Consumer<T> consumer) {
        CraftWorld craftWorld = (CraftWorld) location.getWorld();

        net.minecraft.server.v1_8_R3.Entity entity = craftWorld.createEntity(location, entityClass);
        consumer.accept((T) entity.getBukkitEntity());

        return craftWorld.addEntity(entity, CreatureSpawnEvent.SpawnReason.CUSTOM);
    }

    public static ArmorStand spawnArmorStand(Location location, Consumer<ArmorStand> consumer) {
        WorldServer nmsWorld = NMS.getWorld(location.getWorld());

        Hologram hologram = new Hologram(nmsWorld);
        hologram.setPosition(location.getX(), location.getY(), location.getZ());

        consumer.accept((ArmorStand) hologram.getBukkitEntity());

        return ((CraftWorld) location.getWorld()).addEntity(hologram, CreatureSpawnEvent.SpawnReason.CUSTOM);
    }

    public static WrappedWatchableObject buildWatchableObject(int index, Object value) {
        return new WrappedWatchableObject(new DataWatcher.WatchableObject(WrappedDataWatcher.getTypeID(value.getClass()), index, value));
    }

}
