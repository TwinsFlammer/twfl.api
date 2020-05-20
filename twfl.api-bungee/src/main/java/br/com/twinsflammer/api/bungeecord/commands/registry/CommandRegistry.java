package br.com.twinsflammer.api.bungeecord.commands.registry;

import br.com.twinsflammer.api.bungeecord.FocusPlugin;
import br.com.twinsflammer.api.bungeecord.commands.CustomCommand;
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
