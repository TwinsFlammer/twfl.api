package com.redefocus.api.spigot.util;

import com.redefocus.api.spigot.reflection.Reflection;
import com.redefocus.api.spigot.SpigotAPI;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Warning;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * Created by @SrGutyerrez
 */
public class JSONText {
    private TextComponent component;
    private TextComponent textComponent;
    private List<BaseComponent> baseComponent = new ArrayList<>();

    public JSONText() {
        this.component = new TextComponent("");
    }

    public JSONText text(String text) {
        this.textComponent = new TextComponent(TextComponent.fromLegacyText(text));
        return this;
    }

    public JSONText hoverText(String text) {
        BaseComponent[] hover = { new TextComponent(text) };
        this.textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hover));
        return this;
    }

    public JSONText hoverItem(ItemStack item){
        BaseComponent[] hover = { new TextComponent(convertItemStackToJson(item)) };
        this.textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, hover));
        return this;
    }

    private static String convertItemStackToJson(ItemStack itemStack) {
        Reflection reflection = SpigotAPI.getInstance().getReflection();

        Class<?> craftItemStackClazz = reflection.getOBCClass("inventory.CraftItemStack");
        Method asNMSCopyMethod = reflection.getMethod(craftItemStackClazz, "asNMSCopy", ItemStack.class);
        Class<?> nmsItemStackClazz = reflection.getNMSClass("ItemStack");
        Class<?> nbtTagCompoundClazz = reflection.getNMSClass("NBTTagCompound");
        Method saveNmsItemStackMethod = reflection.getMethod(nmsItemStackClazz, "save", nbtTagCompoundClazz);
        Object itemAsJsonObject;

        try {
            Object nmsNbtTagCompoundObj = nbtTagCompoundClazz.newInstance();
            Object nmsItemStackObj = asNMSCopyMethod.invoke(null, itemStack);
            itemAsJsonObject = saveNmsItemStackMethod.invoke(nmsItemStackObj, nmsNbtTagCompoundObj);
        } catch (Throwable t) {
            Bukkit.getLogger().log(Level.SEVERE, "failed to serialize itemstack to nms item", t);
            return null;
        }

        return itemAsJsonObject.toString();
    }

    public JSONText clickOpenURL(String url) {
        this.textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, (!url.startsWith("http") ? "https://" : "") + url));
        return this;
    }

    public JSONText clickRunCommand(String command) {
        this.textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command));
        return this;
    }

    public JSONText clickSuggest(String suggest) {
        this.textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, suggest));
        return this;
    }

    public JSONText next() {
        if (this.textComponent == null) return this;
        baseComponent.add(this.textComponent);
        return this;
    }

    public void send(Player player) {
        this.component.setExtra(baseComponent);
        player.spigot().sendMessage(this.component);
    }

    public void send(CommandSender sender) {
        this.component.setExtra(baseComponent);
        sender.sendMessage(this.component.getText());
    }

    @Warning
    public void sendEveryOne() {
        this.component.setExtra(baseComponent);
    }
}
