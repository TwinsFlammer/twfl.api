package br.com.twinsflammer.api.bungeecord.commands.enums;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * Created by @SrGutyerrez
 */
public enum CommandRestriction {
    CONSOLE,
    IN_GAME,
    ALL;

    public boolean isValid(CommandSender sender) {
        switch (this) {
            case CONSOLE: return !(sender instanceof ProxiedPlayer);
            case IN_GAME: return (sender instanceof ProxiedPlayer);
            case ALL: return true;
            default: return false;
        }
    }
}
