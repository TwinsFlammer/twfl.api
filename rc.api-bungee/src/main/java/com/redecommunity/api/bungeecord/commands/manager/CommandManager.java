package com.redecommunity.api.bungeecord.commands.manager;

import com.redecommunity.api.bungeecord.CommunityPlugin;
import com.redecommunity.api.bungeecord.commands.CustomCommand;
import net.md_5.bungee.api.ProxyServer;

/**
 * Created by @SrGutyerrez
 */
public class CommandManager {
    public static void registerCommand(CommunityPlugin communityPlugin, CustomCommand customCommand) {
        ProxyServer.getInstance().getPluginManager().registerCommand(communityPlugin, customCommand);
    }
}
