package com.redecommunity.api.spigot.util;

import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;

/**
 * Created by @SrGutyerrez
 */
@RequiredArgsConstructor
public class ColorData {
    private final String message;
    private final ChatColor color, featuredColor;

    private Integer colorPosition = 0;

    private String newMessage;

    public void next() {
        if (this.colorPosition >= this.message.length()) this.colorPosition = 0;

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(this.color + this.getMessage(0, this.colorPosition))
                .append(this.featuredColor).append(this.getMessage(this.colorPosition, Math.min(this.message.length(), this.colorPosition + 1)))
                .append(this.message.length() > this.colorPosition +1 ? this.getMessage(this.colorPosition+1, this.message.length()) : this.message.substring(this.message.length()));

        this.newMessage = stringBuilder.toString();

        this.colorPosition++;
    }

    private String getMessage(Integer index, Integer maxIndex) {
        return this.message.substring(index, maxIndex);
    }

    public String getMessage() {
        return this.newMessage;
    }
}
