package com.redefocus.api.bungeecord.commands.registry;

import com.redefocus.api.bungeecord.FocusPlugin;
import com.redefocus.api.bungeecord.commands.CustomCommand;
import net.md_5.bungee.api.ProxyServer;

/**
 * Created by @SrGutyerrez
 */
public class CommandRegistry {
    public static void registerCommand(FocusPlugin focusPlugin, CustomCommand customCommand) {
        System.out.println("[CommandRegistry] Registering " + customCommand.getName() + " command by " + focusPlugin.getFile().getName() + ".");

        ProxyServer.getInstance().getPluginManager().registerCommand(focusPlugin, customCommand);
    }
}
