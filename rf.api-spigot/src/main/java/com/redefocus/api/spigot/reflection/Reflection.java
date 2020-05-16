package com.redefocus.api.spigot.reflection;

import com.google.common.collect.Maps;
import com.redefocus.api.spigot.SpigotAPI;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Created by @SrGutyerrez
 */
@RequiredArgsConstructor
public class Reflection {
//    private final JavaPlugin plugin;
//
//    private String versionPrefix = "";
//    @Getter
//    private Class<?> class_ItemStack,
//            class_NBTBase,
//            class_NBTTagCompound,
//            class_NBTTagList,
//            class_CraftItemStack,
//            class_NBTCompressedStreamTools,
//            class_EntityArmorStand,
//            class_PacketPlayOutSpawnEntityLiving,
//            class_EntityLiving,
//            class_PlayerConnection,
//            class_Packet,
//            class_PacketPlayOutEntityDestroy,
//            class_World,
//            class_CraftWorld,
//            class_Entity;
//    @Getter
//    private Method method_asNMSCopy,
//            method_SaveItem,
//            method_Add,
//            method_Save,
//            method_A,
//            method_CreateStack,
//            method_AsBukkitCopy,
//            method_SendPacket,
//            method_GetId,
//            method_Die,
//            method_SetCustomName,
//            method_SetCustomNameVisible,
//            method_SetInvisible,
//            method_SetGravity,
//            method_GetHandle;
//    @Getter
//    private Constructor<?> constructor_PacketPlayOutSpawnEntityLiving,
//            constructor_PacketPlayOutEntityDestroy,
//            constructor_EntityArmorStand;
//
//    private Map<String, Class<?>> loadedNMSClasses = Maps.newHashMap(),
//            loadedOBCClasses = Maps.newHashMap();
//
//    private Map<Class<?>, Map<String, Method>> loadedMethods = Maps.newHashMap();
//    private Map<Class<?>, Map<String, Field>> loadedFields = Maps.newHashMap();
//
//    public void initialize() {
//        this.loadClasses();
//        this.loadMethods();
//        this.loadConstructors();
//    }
//
//    public void loadClasses() {
//        try {
//            String className = SpigotAPI.getInstance().getServer().getClass().getName();
//            String[] packages = className.split("\\.");
//            if (packages.length == 5) {
//                this.versionPrefix = packages[3] + ".";
//            }
//            this.class_ItemStack = this.fixBukkitClass("net.minecraft.server.ItemStack");
//            this.class_NBTBase = this.fixBukkitClass("net.minecraft.server.NBTBase");
//            this.class_NBTTagCompound = this.fixBukkitClass("net.minecraft.server.NBTTagCompound");
//            this.class_NBTTagList = this.fixBukkitClass("net.minecraft.server.NBTTagList");
//            this.class_CraftItemStack = this.fixBukkitClass("org.bukkit.craftbukkit.inventory.CraftItemStack");
//            this.class_NBTCompressedStreamTools = this.fixBukkitClass("net.minecraft.server.NBTCompressedStreamTools");
//            //Hologram classes
//            this.class_EntityArmorStand = this.fixBukkitClass("net.minecraft.server.EntityArmorStand");
//            this.class_PacketPlayOutSpawnEntityLiving = this.fixBukkitClass("net.minecraft.server.PacketPlayOutSpawnEntityLiving");
//            this.class_EntityLiving = this.fixBukkitClass("net.minecraft.server.EntityLiving");
//            this.class_PlayerConnection = this.fixBukkitClass("net.minecraft.server.PlayerConnection");
//            this.class_Packet = this.fixBukkitClass("net.minecraft.server.Packet");
//            this.class_PacketPlayOutEntityDestroy = this.fixBukkitClass("net.minecraft.server.PacketPlayOutEntityDestroy");
//            this.class_World = this.fixBukkitClass("net.minecraft.server.World");
//            this.class_CraftWorld = this.fixBukkitClass("org.bukkit.craftbukkit.CraftWorld");
//            this.class_Entity = this.fixBukkitClass("net.minecraft.server.Entity");
//        } catch (Throwable ex) {
//            ex.printStackTrace();
//        }
//    }
//
//    public void loadMethods() {
//        try {
//            this.method_asNMSCopy = this.getClass_CraftItemStack().getMethod("asNMSCopy", ItemStack.class);
//            this.method_SaveItem = this.getClass_ItemStack().getMethod("save", this.getClass_NBTTagCompound());
//            this.method_Add = this.getClass_NBTTagList().getMethod("add", this.getClass_NBTBase());
//            this.method_Save = this.getClass_NBTCompressedStreamTools().getMethod("a", this.getClass_NBTTagCompound(), DataOutput.class);
//            this.method_A = this.getClass_NBTCompressedStreamTools().getMethod("a", DataInputStream.class);
//            this.method_CreateStack = this.getClass_ItemStack().getMethod("createStack", this.getClass_NBTTagCompound());
//            this.method_AsBukkitCopy = this.getClass_CraftItemStack().getMethod("asBukkitCopy", this.getClass_ItemStack());
//            this.method_SendPacket = this.getClass_PlayerConnection().getMethod("sendPacket", this.getClass_Packet());
//            this.method_GetId = this.getClass_Entity().getMethod("getId");
//            this.method_Die = this.getClass_Entity().getMethod("die");
//            this.method_SetCustomName = this.getClass_EntityArmorStand().getMethod("setCustomName", String.class);
//            this.method_SetCustomNameVisible = this.getClass_EntityArmorStand().getMethod("setCustomNameVisible", boolean.class);
//            this.method_SetInvisible = this.getClass_EntityArmorStand().getMethod("setInvisible", boolean.class);
//            this.method_SetGravity = this.getClass_EntityArmorStand().getMethod("setGravity", boolean.class);
//            this.method_GetHandle = this.getClass_CraftWorld().getMethod("getHandle");
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
//    }
//
//    public void loadConstructors() {
//        try {
//            this.constructor_PacketPlayOutSpawnEntityLiving = this.class_PacketPlayOutSpawnEntityLiving.getConstructor(this.class_EntityLiving);
//            this.constructor_PacketPlayOutEntityDestroy = this.class_PacketPlayOutEntityDestroy.getConstructor(int[].class);
//            this.constructor_EntityArmorStand = this.class_EntityArmorStand.getConstructor(this.class_World, double.class, double.class, double.class);
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
//    }
//
//    private Class<?> fixBukkitClass(String className) {
//        className = className.replace("org.bukkit.craftbukkit.", "org.bukkit.craftbukkit." + this.versionPrefix);
//        className = className.replace("net.minecraft.server.", "net.minecraft.server." + this.versionPrefix);
//
//        try {
//            return Class.forName(className);
//        } catch (ClassNotFoundException exception) {
//            exception.printStackTrace();
//            return null;
//        }
//    }
//
//    public Class<?> getNMSClass(String nmsClassName) {
//        if (this.loadedNMSClasses.containsKey(nmsClassName)) return this.loadedNMSClasses.get(nmsClassName);
//
//        String clazzName = "net.minecraft.server." + this.versionPrefix + nmsClassName;
//        Class<?> clazz;
//
//        try {
//            clazz = Class.forName(clazzName);
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//            return this.loadedNMSClasses.put(nmsClassName, null);
//        }
//
//        this.loadedNMSClasses.put(nmsClassName, clazz);
//        return clazz;
//    }
//
//    public synchronized Class<?> getOBCClass(String obcClassName) {
//        if (this.loadedOBCClasses.containsKey(obcClassName)) return this.loadedOBCClasses.get(obcClassName);
//
//        String clazzName = "org.bukkit.craftbukkit." + this.versionPrefix + obcClassName;
//        Class<?> clazz;
//
//        try {
//            clazz = Class.forName(clazzName);
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//            this.loadedOBCClasses.put(obcClassName, null);
//            return null;
//        }
//
//        this.loadedOBCClasses.put(obcClassName, clazz);
//        return clazz;
//    }
//
//    public Object getConnection(Player player) {
//        Method getHandleMethod = getMethod(player.getClass(), "getHandle", (Class<?>[]) new Class[0]);
//        if (getHandleMethod != null) {
//            try {
//                Object nmsPlayer = getHandleMethod.invoke(player, new Object[0]);
//                Field playerConField = getField(nmsPlayer.getClass(), "playerConnection");
//                return playerConField.get(nmsPlayer);
//            } catch (NoSuchElementException | IllegalAccessException | InvocationTargetException exception) {
//                exception.printStackTrace();
//            }
//        }
//        return null;
//    }
//
//    public Constructor<?> getConstructor(Class<?> clazz, Class<?>... params) {
//        try {
//            return clazz.getConstructor(params);
//        } catch (NoSuchMethodException exception) {
//            return null;
//        }
//    }
//
//    public Method getMethod(Class<?> clazz, String methodName, Class<?>... params) {
//        if (!this.loadedMethods.containsKey(clazz)) this.loadedMethods.put(clazz, Maps.newHashMap());
//
//        Map<String, Method> methods = this.loadedMethods.get(clazz);
//
//        if (methods.containsKey(methodName)) return methods.get(methodName);
//
//        try {
//            Method method = clazz.getMethod(methodName, params);
//            methods.put(methodName, method);
//            this.loadedMethods.put(clazz, methods);
//            return method;
//        } catch (NoSuchMethodException exception) {
//            exception.printStackTrace();
//            methods.put(methodName, null);
//            this.loadedMethods.put(clazz, methods);
//            return null;
//        }
//    }
//
//    public Field getField(Class<?> clazz, String fieldName) {
//        if (!this.loadedFields.containsKey(clazz)) this.loadedFields.put(clazz, Maps.newHashMap());
//
//        Map<String, Field> fields = this.loadedFields.get(clazz);
//
//        if (fields.containsKey(fieldName)) return fields.get(fieldName);
//
//        try {
//            Field field = clazz.getField(fieldName);
//            fields.put(fieldName, field);
//            this.loadedFields.put(clazz, fields);
//            return field;
//        } catch (NoSuchFieldException exception) {
//            exception.printStackTrace();
//            fields.put(fieldName, null);
//            this.loadedFields.put(clazz, fields);
//            return null;
//        }
//    }
    private static String versionPrefix = "";
    private static Class<?> class_ItemStack, class_NBTBase, class_NBTTagCompound, class_NBTTagList, class_CraftItemStack, class_NBTCompressedStreamTools
            , class_EntityArmorStand, class_PacketPlayOutSpawnEntityLiving, class_EntityLiving, class_PlayerConnection, class_Packet
            , class_PacketPlayOutEntityDestroy, class_World, class_CraftWorld, class_Entity;
    private static Method method_asNMSCopy, method_SaveItem, method_Add, method_Save, method_A, method_CreateStack, method_AsBukkitCopy
            , method_SendPacket, method_GetId, method_Die, method_SetCustomName, method_SetCustomNameVisible, method_SetInvisible, method_SetGravity
            , method_GetHandle;
    private static Constructor<?> constructor_PacketPlayOutSpawnEntityLiving, constructor_PacketPlayOutEntityDestroy, constructor_EntityArmorStand;

    private static JavaPlugin plugin;

    public Reflection(JavaPlugin m) {
        plugin = m;
        loadClasses();
        loadMethods();
        loadConstructors();
    }

    public static void loadClasses(){
        try {
            String className = plugin.getServer().getClass().getName();
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
