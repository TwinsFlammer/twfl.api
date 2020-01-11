package com.redecommunity.api.spigot.commands.registry;

import com.redecommunity.api.spigot.CommunityPlugin;
import com.redecommunity.api.spigot.commands.CustomCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created by @SrGutyerrez
 */
public class CommandRegistry {
    public static void registerCommand(CommunityPlugin communityPlugin, CustomCommand customCommand) {
        System.out.println("[CommandRegistry] Registering " + customCommand.getName() + " command by " + communityPlugin.getName() + ".");

        CraftServer craftServer = (CraftServer) Bukkit.getServer();

        SimpleCommandMap simpleCommandMap = craftServer.getCommandMap();

        try {
            Field commandMapField = SimpleCommandMap.class.getDeclaredField("knownCommands");

            commandMapField.setAccessible(true);


            Field knowCommandsField = simpleCommandMap.getClass().getDeclaredField("knownCommands");

            knowCommandsField.setAccessible(true);

            Map<String, Command> commands = (Map<String, Command>) knowCommandsField.get("knownCommands");

            simpleCommandMap.getCommands()
                    .stream()
                    .filter(command -> command.getName().equalsIgnoreCase(customCommand.getName()))
                    .forEach(command -> {
                        command.unregister(simpleCommandMap);
                        commands.remove(command.getName());
                        command.getAliases().forEach(commands::remove);
                    });
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException exception) {
            exception.printStackTrace();
        }

        simpleCommandMap.register(
                customCommand.getName(),
                customCommand
        );
    }
}
