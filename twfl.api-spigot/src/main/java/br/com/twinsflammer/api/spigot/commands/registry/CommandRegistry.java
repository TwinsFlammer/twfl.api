package br.com.twinsflammer.api.spigot.commands.registry;

import br.com.twinsflammer.api.spigot.TwinsPlugin;
import com.google.common.collect.Lists;
import br.com.twinsflammer.api.spigot.commands.CustomCommand;
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
    public static void registerCommand(TwinsPlugin twinsPlugin, CustomCommand customCommand) {
        System.out.println("[CommandRegistry] Registering " + customCommand.getName() + " command by " + twinsPlugin.getName() + ".");

        CraftServer craftServer = (CraftServer) Bukkit.getServer();

        SimpleCommandMap simpleCommandMap = craftServer.getCommandMap();

        try {
            Field commandMapField = SimpleCommandMap.class.getDeclaredField("knownCommands");

            commandMapField.setAccessible(true);


            Field knowCommandsField = simpleCommandMap.getClass().getDeclaredField("knownCommands");

            knowCommandsField.setAccessible(true);

            Map<String, Command> commands = (Map<String, Command>) knowCommandsField.get(simpleCommandMap);

            Lists.newArrayList(simpleCommandMap.getCommands())
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
