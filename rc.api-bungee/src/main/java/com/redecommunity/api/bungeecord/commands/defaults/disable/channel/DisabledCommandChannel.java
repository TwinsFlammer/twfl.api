package com.redecommunity.api.bungeecord.commands.defaults.disable.channel;

import com.redecommunity.common.shared.databases.redis.channel.data.Channel;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisDataException;

/**
 * Created by @SrGutyerrez
 */
public class DisabledCommandChannel extends Channel {
    @Override
    public String getName() {
        return "disabled_command";
    }

    @Override
    public void sendMessage(String message) {
        try (Jedis jedis = this.getJedisPool().getResource()) {
            jedis.publish(this.getName(), message);
        } catch (JedisDataException exception) {
            exception.printStackTrace();
        }
    }
}
