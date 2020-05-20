package br.com.twinsflammer.api.shared.commands.defaults.disable.channel;

import br.com.twinsflammer.api.shared.commands.defaults.disable.manager.DisabledCommandManager;
import br.com.twinsflammer.common.shared.databases.redis.channel.data.Channel;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisDataException;

/**
 * Created by @SrGutyerrez
 */
public class DisabledCommandChannel extends Channel {
    @Override
    public String getName() {
        return DisabledCommandManager.CHANNEL_NAME;
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
