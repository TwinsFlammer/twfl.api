package com.redecommunity.api.spigot.manager;

import com.redecommunity.api.spigot.commands.registry.CommandRegistry;
import com.redecommunity.api.spigot.inventory.manager.CustomInventoryManager;
import com.redecommunity.api.shared.connection.manager.ProxyServerManager;
import com.redecommunity.api.spigot.SpigotAPI;
import com.redecommunity.api.spigot.commands.CustomCommand;
import com.redecommunity.api.spigot.spawn.manager.SpawnManager;
import com.redecommunity.common.shared.Common;
import com.redecommunity.common.shared.databases.mysql.dao.Table;
import com.redecommunity.common.shared.databases.redis.channel.data.Channel;
import com.redecommunity.common.shared.databases.redis.handler.JedisMessageListener;
import com.redecommunity.common.shared.util.ClassGetter;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

/**
 * Created by @SrGutyerrez
 */
public class StartManager {
    public StartManager() {
        new ListenerManager();
        // COMMAND REGISTER
        new CommandRegistry();

        new TableManager();

        new DataManager();

        new CommandManager();

        new ChannelManager();

        new JedisMessageListenerManager();
    }
}

class ListenerManager {
    ListenerManager() {
        ClassGetter.getClassesForPackage(SpigotAPI.class).forEach(clazz -> {
            if (Listener.class.isAssignableFrom(clazz)) {
                try {
                    Listener listener = (Listener) clazz.newInstance();

                    Bukkit.getPluginManager().registerEvents(
                            listener,
                            SpigotAPI.getInstance()
                    );
                } catch (InstantiationException | IllegalAccessException exception) {
                    exception.printStackTrace();
                }
            }
        });
    }
}

class DataManager {
    DataManager() {
        new ProxyServerManager(
                null,
                null
        );

        new CustomInventoryManager();
        new SpawnManager();
    }
}

class TableManager {
    TableManager() {
        ClassGetter.getClassesForPackage(SpigotAPI.class).forEach(clazz -> {
            if (Table.class.isAssignableFrom(clazz)) {
                try {
                    Table table = (Table) clazz.newInstance();

                    table.createTable();
                } catch (InstantiationException | IllegalAccessException exception) {
                    exception.printStackTrace();
                }
            }
        });
    }
}

class CommandManager {
    CommandManager() {
        ClassGetter.getClassesForPackage(SpigotAPI.class).forEach(clazz -> {
            if (CustomCommand.class.isAssignableFrom(clazz) && !clazz.equals(CustomCommand.class)) {
                try {
                    CustomCommand customCommand = (CustomCommand) clazz.newInstance();

                    CommandRegistry.registerCommand(
                            SpigotAPI.getInstance(),
                            customCommand
                    );
                } catch (InstantiationException | IllegalAccessException exception) {
                    exception.printStackTrace();
                }
            }
        });
    }
}

class ChannelManager {
    ChannelManager() {
        ClassGetter.getClassesForPackage(SpigotAPI.class).forEach(clazz -> {
            if (Channel.class.isAssignableFrom(clazz)) {
                try {
                    Channel channel = (Channel) clazz.newInstance();

                    Common.getInstance().getChannelManager().register(channel);
                } catch (InstantiationException | IllegalAccessException exception) {
                    exception.printStackTrace();
                }
            }
        });
    }
}

class JedisMessageListenerManager {
    JedisMessageListenerManager() {
        ClassGetter.getClassesForPackage(SpigotAPI.class).forEach(clazz -> {
            if (JedisMessageListener.class.isAssignableFrom(clazz)) {
                try {
                    JedisMessageListener jedisMessageListener = (JedisMessageListener) clazz.newInstance();

                    Common.getInstance().getJedisMessageManager().registerListener(jedisMessageListener);
                } catch (InstantiationException | IllegalAccessException exception) {
                    exception.printStackTrace();
                }
            }
        });
    }
}