package com.redefocus.api.spigot.restart.channel;

import com.redefocus.common.shared.databases.redis.channel.data.Channel;
import com.redefocus.common.shared.databases.redis.data.Redis;
import com.redefocus.common.shared.databases.redis.manager.RedisManager;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisDataException;

/**
 * Created by @SrGutyerrez
 */
public class RestartChannel extends Channel {
    @Override
    public String getName() {
        return "restart_channel";
    }

    @Override
    public void sendMessage(String message) {
        Redis redis = RedisManager.getDefaultRedis();

        try (Jedis jedis = redis.getJedisPool().getResource()) {
            jedis.publish(
                    this.getName(),
                    message
            );
        } catch (JedisDataException exception) {
            exception.printStackTrace();
        }
    }
}
