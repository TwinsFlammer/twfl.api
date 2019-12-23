package com.redecommunity.api.shared.manager;

import com.redecommunity.api.shared.API;
import com.redecommunity.api.shared.commands.defaults.disable.manager.DisabledCommandManager;
import com.redecommunity.common.shared.Common;
import com.redecommunity.common.shared.databases.mysql.dao.Table;
import com.redecommunity.common.shared.databases.redis.channel.data.Channel;
import com.redecommunity.common.shared.databases.redis.handler.JedisMessageListener;
import com.redecommunity.common.shared.util.ClassGetter;

/**
 * Created by @SrGutyerrez
 */
public class StartManager {
    public StartManager() {
        new TableManager();

        new DataManager();

        new ChannelManager();
        new JedisMessageListenerManager();
    }
}

class DataManager {
    DataManager() {
        new DisabledCommandManager();
    }
}

class TableManager {
    TableManager() {
        ClassGetter.getClassesForPackage(API.class).forEach(clazz -> {
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

class ChannelManager {
    ChannelManager() {
        ClassGetter.getClassesForPackage(API.class).forEach(clazz -> {
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
        ClassGetter.getClassesForPackage(API.class).forEach(clazz -> {
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