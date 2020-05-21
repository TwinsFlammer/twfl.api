package br.com.twinsflammer.api.bungeecord.commands.registry;

import br.com.twinsflammer.api.bungeecord.TwinsPlugin;
import br.com.twinsflammer.api.bungeecord.commands.CustomCommand;
import net.md_5.bungee.api.ProxyServer;

/**
 * Created by @SrGutyerrez
 */
public class CommandRegistry {
    public static void registerCommand(TwinsPlugin twinsPlugin, CustomCommand customCommand) {
        System.out.println("[CommandRegistry] Registering " + customCommand.getName() + " command by " + twinsPlugin.getFile().getName() + ".");

        ProxyServer.getInstance().getPluginManager().registerCommand(twinsPlugin, customCommand);
    }
}
