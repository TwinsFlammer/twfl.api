package com.redecommunity.api.spigot.commands.registry;

import com.redecommunity.api.spigot.CommunityPlugin;
import com.redecommunity.api.spigot.commands.CustomCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;

/**
 * Created by @SrGutyerrez
 */
public class CommandRegistry {
    public static void registerCommand(CommunityPlugin communityPlugin, CustomCommand customCommand) {
        System.out.println("[CommandRegistry] Registering " + customCommand.getName() + " command by " + communityPlugin.getName() + ".");

        CraftServer craftServer = (CraftServer) Bukkit.getServer();

        SimpleCommandMap simpleCommandMap = craftServer.getCommandMap();

//        try {
//            Field commandMapField = SimpleCommandMap.class.getDeclaredField("knowCommands");
//
//            commandMapField.setAccessible(true);
//
//
//            Field knowCommandsField = simpleCommandMap.getClass().getDeclaredField("knowCommands");
//
//            knowCommandsField.setAccessible(true);
//
//            Map<String, Command> commands = (Map<String, Command>) knowCommandsField.get("knowCommands");
//
//
//        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException exception) {
//            exception.printStackTrace();
//        }

        simpleCommandMap.register(
                customCommand.getName(),
                customCommand
        );
    }
}
