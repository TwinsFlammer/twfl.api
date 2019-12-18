package com.redecommunity.api.bungeecord.commands.registry;

import com.redecommunity.api.bungeecord.CommunityPlugin;
import com.redecommunity.api.bungeecord.commands.CustomCommand;
import net.md_5.bungee.api.ProxyServer;

/**
 * Created by @SrGutyerrez
 */
public class CommandRegistry {
    public static void registerCommand(CommunityPlugin communityPlugin, CustomCommand customCommand) {
        System.out.println("[CommandRegistry] Registering " + customCommand.getName() + " command by " + communityPlugin.getFile().getName() + ".");

        ProxyServer.getInstance().getPluginManager().registerCommand(communityPlugin, customCommand);
    }
}
