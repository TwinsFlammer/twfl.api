package com.redecommunity.api.bungeecord.util;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.List;

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

    public JSONText(String prefix) {
        this.component = new TextComponent(prefix);
    }

    public JSONText prefix(String prefix) {
        text(prefix);
        return this;
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

    public void send(ProxiedPlayer proxiedPlayer) {
        this.component.setExtra(baseComponent);
        proxiedPlayer.sendMessage(this.component);
    }

    public void send(CommandSender sender) {
        this.component.setExtra(baseComponent);
        sender.sendMessage(this.component);
    }

    public void sendEveryOne() {
        this.component.setExtra(baseComponent);
    }
}
