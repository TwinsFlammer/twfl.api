package com.redecommunity.api.bungeecord.manager;

import com.redecommunity.api.bungeecord.BungeeAPI;
import com.redecommunity.api.bungeecord.commands.defaults.disable.manager.DisabledCommandManager;
import com.redecommunity.api.bungeecord.commands.manager.CommandManager;
import com.redecommunity.common.shared.databases.mysql.dao.Table;
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
        new CommandManager();
        new DataManager();

        new DaoManager();
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
        new DisabledCommandManager();
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