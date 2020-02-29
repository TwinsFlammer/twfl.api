package com.redecommunity.api.spigot.util;

import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

/**
 * Created by @SrGutyerrez
 */
public class NBTTag {
    public static NBTTagCompound getNBTTag(ItemStack itemStack) {
        net.minecraft.server.v1_8_R3.ItemStack itemStack1 = CraftItemStack.asNMSCopy(itemStack);
        if (itemStack1.hasTag()) return itemStack1.getTag();
        return new NBTTagCompound();
    }

    public static ItemStack setNBTTag(ItemStack itemStack, NBTTagCompound ntb) {
        net.minecraft.server.v1_8_R3.ItemStack itemStack1 = CraftItemStack.asNMSCopy(itemStack);
        itemStack1.setTag(ntb);
        return CraftItemStack.asBukkitCopy(itemStack1);
    }

    public static boolean hasTag(ItemStack itemStack) {
        return CraftItemStack.asNMSCopy(itemStack).hasTag();
    }

    public static boolean hasKey(ItemStack is, String key) {
        return NBTTag.hasTag(is) && NBTTag.getNBTTag(is).hasKey(key);
    }
}
