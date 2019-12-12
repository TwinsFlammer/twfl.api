package com.redecommunity.api.spigot.commands.enums;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by @SrGutyerrez
 */
public enum CommandRestriction {
    CONSOLE,
    IN_GAME,
    ALL;

    public boolean isValid(CommandSender sender) {
        switch (this) {
            case CONSOLE: return !(sender instanceof Player);
            case IN_GAME: return (sender instanceof Player);
            case ALL: return true;
            default: return false;
        }
    }
}
