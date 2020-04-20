package com.redefocus.api.spigot.teleport.channel;

import com.redefocus.api.spigot.teleport.manager.TeleportRequestManager;
import com.redefocus.common.shared.databases.redis.channel.data.Channel;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisDataException;

/**
 * Created by @SrGutyerrez
 */
public class TeleportChannel extends Channel {
    @Override
    public String getName() {
        return TeleportRequestManager.CHANNEL_NAME;
    }

    @Override
    public void sendMessage(String message) {
        try (Jedis jedis = this.getJedisPool().getResource()) {
            jedis.publish(
                    this.getName(),
                    message
            );
        } catch (JedisDataException exception) {
            exception.printStackTrace();
        }
    }
}
