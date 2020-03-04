package com.redecommunity.api.spigot.hologram.entity;

import com.redecommunity.api.spigot.hologram.util.NullBoundingBox;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.EntityArmorStand;

/**
 * @author Enzo
 */
public class Hologram extends EntityArmorStand {

    public Hologram(World world) {
        super(world);

        this.a(new NullBoundingBox());
        this.setSize(0.0f, 0.0f);
    }
}
