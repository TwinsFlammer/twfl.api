package br.com.twinsflammer.api.spigot.util.serialize;

import br.com.twinsflammer.api.spigot.SpigotAPI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author MrPowerGamerBr
 * Edited by @SrGutyerrez
 */
public class ItemSerialize {
    public static String toBase64(org.bukkit.inventory.ItemStack itemStack) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutput = new DataOutputStream(outputStream);

        try {
            Object nbtTagListItems = Reflection.getClass_NBTTagList().newInstance();
            Object nbtTagCompoundItem = Reflection.getClass_NBTTagCompound().newInstance();

            Object nms = Reflection.getMethod_asNMSCopy().invoke(null, itemStack);

            Reflection.getMethod_SaveItem().invoke(nms, nbtTagCompoundItem);

            Reflection.getMethod_Add().invoke(nbtTagListItems, nbtTagCompoundItem);

            Reflection.getMethod_Save().invoke(null, nbtTagCompoundItem, dataOutput);
        } catch (Throwable ex) {
            ex.printStackTrace();
        }

        return new BigInteger(1, outputStream.toByteArray()).toString(32);
    }

    public static ItemStack fromBase64(String data) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(new BigInteger(data, 32).toByteArray());
        Object nbtTagCompoundRoot;
        Object nmsItem = null;
        Object toReturn = null;

        try {
            nbtTagCompoundRoot = Reflection.getMethod_A().invoke(null, new DataInputStream(inputStream));
            if (nbtTagCompoundRoot != null) {
                nmsItem = Reflection.getMethod_CreateStack().invoke(null, nbtTagCompoundRoot);
            }

            toReturn = Reflection.getMethod_AsBukkitCopy().invoke(null, nmsItem);
        } catch (Throwable ex) {
            ex.printStackTrace();
        }

        return (ItemStack) toReturn;
    }

    public static String toBase64List(List<ItemStack> items) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        BukkitObjectOutputStream dataOutput;
        try {
            dataOutput = new BukkitObjectOutputStream(outputStream);
            // Content Size
            // Contents
            dataOutput.writeInt(items.size());
            int index = 0;
            for (ItemStack is : items) {
                if (is != null && is.getType() != Material.AIR) {
                    dataOutput.writeObject(toBase64(is));
                } else {
                    dataOutput.writeObject(null);
                }
                dataOutput.writeInt(index);
                index++;
            }
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        }
        catch (Exception e) {
            throw new IllegalStateException("Unable to save item stacks.", e);
        }
    }

    public static List<ItemStack> fromBase64List(String items) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(items));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            int size = dataInput.readInt();
            List<ItemStack> list = new ArrayList<>();
            // Read the serialized inventory
            for (int i = 0; i < size; i++) {
                Object utf = dataInput.readObject();
                int slot = dataInput.readInt();
                if (utf != null) {
                    list.add(fromBase64((String) utf));
                }
            }
            dataInput.close();
            return list;
        }
        catch (Exception e) {
            throw new IllegalStateException("Unable to load item stacks.", e);
        }
    }

    static class Reflection {

        private static String versionPrefix = "";
        private static Class<?> class_ItemStack, class_NBTBase, class_NBTTagCompound, class_NBTTagList, class_CraftItemStack, class_NBTCompressedStreamTools
                , class_EntityArmorStand, class_PacketPlayOutSpawnEntityLiving, class_EntityLiving, class_PlayerConnection, class_Packet
                , class_PacketPlayOutEntityDestroy, class_World, class_CraftWorld, class_Entity;
        private static Method method_asNMSCopy, method_SaveItem, method_Add, method_Save, method_A, method_CreateStack, method_AsBukkitCopy
                , method_SendPacket, method_GetId, method_Die, method_SetCustomName, method_SetCustomNameVisible, method_SetInvisible, method_SetGravity
                , method_GetHandle;
        private static Constructor<?> constructor_PacketPlayOutSpawnEntityLiving, constructor_PacketPlayOutEntityDestroy, constructor_EntityArmorStand;

        static {
            loadClasses();
            loadMethods();
            loadConstructors();
        }

        public static void loadClasses(){
            try {
                String className = SpigotAPI.getInstance().getServer().getClass().getName();
                String[] packages = className.split("\\.");
                if (packages.length == 5) {
                    versionPrefix = packages[3] + ".";
                }
                class_ItemStack = fixBukkitClass("net.minecraft.server.ItemStack");
                class_NBTBase = fixBukkitClass("net.minecraft.server.NBTBase");
                class_NBTTagCompound = fixBukkitClass("net.minecraft.server.NBTTagCompound");
                class_NBTTagList = fixBukkitClass("net.minecraft.server.NBTTagList");
                class_CraftItemStack = fixBukkitClass("org.bukkit.craftbukkit.inventory.CraftItemStack");
                class_NBTCompressedStreamTools = fixBukkitClass("net.minecraft.server.NBTCompressedStreamTools");
                //Hologram classes
                class_EntityArmorStand = fixBukkitClass("net.minecraft.server.EntityArmorStand");
                class_PacketPlayOutSpawnEntityLiving = fixBukkitClass("net.minecraft.server.PacketPlayOutSpawnEntityLiving");
                class_EntityLiving = fixBukkitClass("net.minecraft.server.EntityLiving");
                class_PlayerConnection = fixBukkitClass("net.minecraft.server.PlayerConnection");
                class_Packet = fixBukkitClass("net.minecraft.server.Packet");
                class_PacketPlayOutEntityDestroy = fixBukkitClass("net.minecraft.server.PacketPlayOutEntityDestroy");
                class_World = fixBukkitClass("net.minecraft.server.World");
                class_CraftWorld = fixBukkitClass("org.bukkit.craftbukkit.CraftWorld");
                class_Entity = fixBukkitClass("net.minecraft.server.Entity");
            } catch (Throwable ex) {
                ex.printStackTrace();
            }
        }

        public static void loadMethods(){
            try {
                method_asNMSCopy = Reflection.getClass_CraftItemStack().getMethod("asNMSCopy", org.bukkit.inventory.ItemStack.class);
                method_SaveItem = Reflection.getClass_ItemStack().getMethod("save", Reflection.getClass_NBTTagCompound());
                method_Add = Reflection.getClass_NBTTagList().getMethod("add", Reflection.getClass_NBTBase());
                method_Save = Reflection.getClass_NBTCompressedStreamTools().getMethod("a", Reflection.getClass_NBTTagCompound(), DataOutput.class);
                method_A = Reflection.getClass_NBTCompressedStreamTools().getMethod("a", DataInputStream.class);
                method_CreateStack = Reflection.getClass_ItemStack().getMethod("createStack", Reflection.getClass_NBTTagCompound());
                method_AsBukkitCopy = Reflection.getClass_CraftItemStack().getMethod("asBukkitCopy", Reflection.getClass_ItemStack());
                //Hologram methods
                method_SendPacket = Reflection.getClass_PlayerConnection().getMethod("sendPacket", Reflection.getClass_Packet());
                method_GetId = Reflection.getClass_Entity().getMethod("getId");
                method_Die = Reflection.getClass_Entity().getMethod("die");
                method_SetCustomName = Reflection.getClass_EntityArmorStand().getMethod("setCustomName", String.class);
                method_SetCustomNameVisible = Reflection.getClass_EntityArmorStand().getMethod("setCustomNameVisible", boolean.class);
                method_SetInvisible = Reflection.getClass_EntityArmorStand().getMethod("setInvisible", boolean.class);
                method_SetGravity = Reflection.getClass_EntityArmorStand().getMethod("setGravity", boolean.class);
                method_GetHandle = Reflection.getClass_CraftWorld().getMethod("getHandle");
            } catch (Throwable ex) {
                ex.printStackTrace();
            }
        }

        public static void loadConstructors(){
            try {
                constructor_PacketPlayOutSpawnEntityLiving = class_PacketPlayOutSpawnEntityLiving.getConstructor(class_EntityLiving);
                constructor_PacketPlayOutEntityDestroy = class_PacketPlayOutEntityDestroy.getConstructor(int[].class);
                constructor_EntityArmorStand = class_EntityArmorStand.getConstructor(class_World, double.class, double.class, double.class);
            } catch (Throwable ex) {
                ex.printStackTrace();
            }
        }

        private static Class<?> fixBukkitClass(String className) {
            className = className.replace("org.bukkit.craftbukkit.", "org.bukkit.craftbukkit." + versionPrefix);
            className = className.replace("net.minecraft.server.", "net.minecraft.server." + versionPrefix);
            try {
                return Class.forName(className);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        }

        public static Object getConnection(Player p){
            Object toReturn = null;

            try {
                Method method_GetHandle = p.getClass().getMethod("getHandle");
                Object nmsPlayer = method_GetHandle.invoke(p);
                Field field_PlayerConnection = nmsPlayer.getClass().getField("playerConnection");
                toReturn = field_PlayerConnection.get(nmsPlayer);
            } catch (Exception ignored){}

            return toReturn;
        }

        private static Class<?> getClass_Entity() {
            return class_Entity;
        }

        public static Method getMethod_GetHandle() {
            return method_GetHandle;
        }

        private static Class<?> getClass_CraftWorld() {
            return class_CraftWorld;
        }

        public static Method getMethod_SetCustomName() {
            return method_SetCustomName;
        }

        public static Method getMethod_SetCustomNameVisible() {
            return method_SetCustomNameVisible;
        }

        public static Method getMethod_SetInvisible() {
            return method_SetInvisible;
        }

        public static Method getMethod_SetGravity() {
            return method_SetGravity;
        }

        public static Constructor<?> getConstructor_EntityArmorStand() {
            return constructor_EntityArmorStand;
        }

        public static Method getMethod_Die() {
            return method_Die;
        }

        public static Method getMethod_GetId() {
            return method_GetId;
        }

        public static Constructor<?> getConstructor_PacketPlayOutEntityDestroy() {
            return constructor_PacketPlayOutEntityDestroy;
        }

        private static Class<?> getClass_Packet() {
            return class_Packet;
        }

        public static Method getMethod_SendPacket() {
            return method_SendPacket;
        }

        private static Class<?> getClass_PlayerConnection() {
            return class_PlayerConnection;
        }

        public static Constructor<?> getConstructor_PacketPlayOutSpawnEntityLiving() {
            return constructor_PacketPlayOutSpawnEntityLiving;
        }

        //Serialize methods
        static Method getMethod_AsBukkitCopy() {
            return method_AsBukkitCopy;
        }

        static Method getMethod_CreateStack() {
            return method_CreateStack;
        }

        static Method getMethod_A() {
            return method_A;
        }

        static Method getMethod_asNMSCopy() {
            return method_asNMSCopy;
        }

        static Method getMethod_SaveItem() {
            return method_SaveItem;
        }

        static Method getMethod_Add() {
            return method_Add;
        }

        static Method getMethod_Save() {
            return method_Save;
        }

        private static Class<?> getClass_EntityArmorStand() {
            return class_EntityArmorStand;
        }

        private static Class<?> getClass_ItemStack() {
            return class_ItemStack;
        }

        private static Class<?> getClass_NBTBase() {
            return class_NBTBase;
        }

        static Class<?> getClass_NBTTagCompound() {
            return class_NBTTagCompound;
        }

        static Class<?> getClass_NBTTagList() {
            return class_NBTTagList;
        }

        private static Class<?> getClass_CraftItemStack() {
            return class_CraftItemStack;
        }

        private static Class<?> getClass_NBTCompressedStreamTools() {
            return class_NBTCompressedStreamTools;
        }
    }
}