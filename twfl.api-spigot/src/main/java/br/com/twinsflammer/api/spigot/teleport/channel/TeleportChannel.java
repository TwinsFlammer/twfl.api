package br.com.twinsflammer.api.spigot.teleport.channel;

import br.com.twinsflammer.api.spigot.teleport.manager.TeleportRequestManager;
import br.com.twinsflammer.common.shared.databases.redis.channel.data.Channel;
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
