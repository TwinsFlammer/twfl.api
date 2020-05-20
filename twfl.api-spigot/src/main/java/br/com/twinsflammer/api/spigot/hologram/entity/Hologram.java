package br.com.twinsflammer.api.spigot.hologram.entity;

import br.com.twinsflammer.api.spigot.hologram.util.NullBoundingBox;
import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.World;

public class Hologram extends EntityArmorStand {

    public Hologram(World world) {
        super(world);

        this.a(new NullBoundingBox());
        this.setSize(0.0f, 0.0f);
        this.setGravity(false);
    }
}
