package com.redefocus.api.spigot.util.jsontext.channel;

import com.redefocus.common.shared.databases.redis.channel.data.Channel;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisDataException;

/**
 * @author SrGutyerrez
 */
public class JSONTextChannel extends Channel {
    public static final String CHANNEL_NAME = "json_text_channel";

    @Override
    public String getName() {
        return JSONTextChannel.CHANNEL_NAME;
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
