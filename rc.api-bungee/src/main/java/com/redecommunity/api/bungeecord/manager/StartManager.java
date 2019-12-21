package com.redecommunity.api.bungeecord.manager;

import com.redecommunity.api.bungeecord.BungeeAPI;
import com.redecommunity.api.bungeecord.commands.CustomCommand;
import com.redecommunity.api.bungeecord.commands.registry.CommandRegistry;
import com.redecommunity.common.shared.Common;
import com.redecommunity.common.shared.databases.mysql.dao.Table;
import com.redecommunity.common.shared.databases.redis.channel.data.Channel;
import com.redecommunity.common.shared.databases.redis.handler.JedisMessageListener;
import com.redecommunity.common.shared.util.ClassGetter;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Listener;

/**
 * Created by @SrGutyerrez
 */
public class StartManager {
    public StartManager() {
        new ListenerManager();
        // COMMAND REGISTER
        new CommandRegistry();

        new DaoManager();

        new DataManager();

        new CommandManager();

        new ChannelManager();

        new JedisMessageListenerManager();
    }
}

class ListenerManager {
    ListenerManager() {
        ClassGetter.getClassesForPackage(BungeeAPI.class).forEach(clazz -> {
            if (Listener.class.isAssignableFrom(clazz)) {
                try {
                    Listener listener = (Listener) clazz.newInstance();

                    ProxyServer.getInstance().getPluginManager().registerListener(
                            BungeeAPI.getInstance(),
                            listener
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
    }
}

class DaoManager {
    DaoManager() {
        ClassGetter.getClassesForPackage(BungeeAPI.class).forEach(clazz -> {
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
        ClassGetter.getClassesForPackage(BungeeAPI.class).forEach(clazz -> {
            if (CustomCommand.class.isAssignableFrom(clazz) && !clazz.equals(CustomCommand.class)) {
                try {
                    CustomCommand customCommand = (CustomCommand) clazz.newInstance();

                    CommandRegistry.registerCommand(
                            BungeeAPI.getInstance(),
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
        ClassGetter.getClassesForPackage(BungeeAPI.class).forEach(clazz -> {
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
        ClassGetter.getClassesForPackage(BungeeAPI.class).forEach(clazz -> {
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