package br.com.twinsflammer.api.bungeecord.message.channel;

import br.com.twinsflammer.common.shared.databases.redis.channel.data.Channel;
import br.com.twinsflammer.common.shared.util.Constants;

/**
 * Created by @SrGutyerrez
 */
public class MessageChannel extends Channel {
    @Override
    public String getName() {
        return Constants.MESSAGE_CHANNEL;
    }
}
