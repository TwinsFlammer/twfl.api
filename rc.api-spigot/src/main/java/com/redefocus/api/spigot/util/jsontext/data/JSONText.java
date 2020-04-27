package com.redefocus.api.spigot.util.jsontext.data;

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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.IntStream;

/**
 * Created by @SrGutyerrez
 */
public class JSONText {
    private TextComponent component;
    private TextComponent textComponent;
    private List<BaseComponent> baseComponent = new ArrayList<>();

    public JSONText() {
        this.component = new TextComponent();
    }

    public JSONText text(String text) {
        this.textComponent = new TextComponent(TextComponent.fromLegacyText(text));
        return this;
    }

    public JSONText hoverText(String text) {
        BaseComponent[] hover = {
                new TextComponent(text)
        };

        this.textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hover));

        return this;
    }

    public JSONText hoverItem(ItemStack item) {
        return this.hoverItem(this.convertItemStackToJson(item));
    }

    public JSONText hoverItem(String jsonItemStack) {
        BaseComponent[] hover = {
                new TextComponent(jsonItemStack)
        };

        this.textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, hover));

        return this;
    }

    private String convertItemStackToJson(ItemStack itemStack) {
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

    @Deprecated
    public JSONText clickOpenURL(String url) {
        return this.open(url);
    }

    public JSONText open(String url) {
        this.textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, (!url.startsWith("http") ? "https://" : "") + url));
        return this;
    }

    @Deprecated
    public JSONText clickRunCommand(String command) {
        return this.execute(command);
    }

    public JSONText execute(String command) {
        this.textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command));
        return this;
    }

    @Deprecated
    public JSONText clickSuggest(String suggest) {
        return this.suggest(suggest);
    }

    public JSONText suggest(String suggest) {
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

    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();

        JSONArray jsonArray = new JSONArray();

        IntStream.range(0, this.baseComponent.size())
                .forEach(index -> {
                    JSONObject jsonObject1 = new JSONObject();

                    BaseComponent baseComponent = this.baseComponent.get(index);

                    String text = baseComponent.toLegacyText();

                    jsonObject1.put("text", text);

                    ClickEvent clickEvent = baseComponent.getClickEvent();

                    System.out.println("Click: " + (clickEvent == null));
                    
                    if (clickEvent != null) {
                        JSONObject jsonObject2 = new JSONObject();

                        jsonObject2.put("action", clickEvent.getAction().toString());
                        jsonObject2.put("value", clickEvent.getValue());

                        jsonObject1.put("click_event", jsonObject2);
                    } else jsonObject1.put("click_event", null);

                    HoverEvent hoverEvent = baseComponent.getHoverEvent();

                    System.out.println("Hover: " + (clickEvent == null));

                    if (hoverEvent != null) {
                        JSONObject jsonObject2 = new JSONObject();

                        jsonObject2.put("action", hoverEvent.getAction().toString());

                        BaseComponent[] baseComponents = hoverEvent.getValue();

                        JSONArray jsonArray1 = new JSONArray();

                        for (BaseComponent baseComponent1 : baseComponents)
                            jsonArray1.add(baseComponent1.toLegacyText());

                        System.out.println(jsonArray.toString());

                        jsonObject2.put("value", (baseComponents.length >= 1 ? baseComponents[0] : ""));

                        jsonObject1.put("hover_event", jsonObject2);
                    } else jsonObject1.put("hover_event", null);

                    jsonArray.add(jsonObject1);
                });

        jsonObject.put("texts", jsonArray);

        return jsonObject.toString();
    }

    public static JSONText fromString(String string) {
        JSONText jsonText = new JSONText();

        JSONObject jsonObject = (JSONObject) JSONValue.parse(string);

        JSONArray jsonArray = (JSONArray) jsonObject.get("texts");

        jsonArray.forEach(object -> {
            JSONObject jsonObject1 = (JSONObject) object;

            System.out.println(jsonObject1);

            String text = (String) jsonObject1.get("text");

            jsonText.text(text);

            JSONObject jsonObject2 = (JSONObject) jsonObject1.get("hover_event");

            System.out.println(jsonObject2 == null);

            if (jsonObject2 != null) {
                String preAction = (String) jsonObject2.get("action");

                HoverEvent.Action action = HoverEvent.Action.valueOf(preAction);

                String value = (String) jsonObject2.get("value");

                switch (action) {
                    case SHOW_ITEM: {
                        jsonText.hoverItem(value);
                        break;
                    }
                    case SHOW_TEXT: {
                        jsonText.hoverText(value);
                        break;
                    }
                }
            }

            JSONObject jsonObject3 = (JSONObject) jsonObject1.get("click_event");

            if (jsonObject3 != null) {
                String preAction = (String) jsonObject3.get("action");

                ClickEvent.Action action = ClickEvent.Action.valueOf(preAction);

                String value = (String) jsonObject3.get("value");

                switch (action) {
                    case OPEN_URL: {
                        jsonText.open(value);
                        break;
                    }
                    case RUN_COMMAND: {
                        jsonText.execute(value);
                        break;
                    }
                    case SUGGEST_COMMAND: {
                        jsonText.suggest(value);
                        break;
                    }
                }
            }

            jsonText.next();
        });

        return jsonText;
    }
}
