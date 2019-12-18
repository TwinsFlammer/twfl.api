package com.redecommunity.api.bungeecord.message.channel;

import com.redecommunity.common.shared.databases.redis.channel.data.Channel;
import com.redecommunity.common.shared.util.Constants;

/**
 * Created by @SrGutyerrez
 */
public class MessageChannel extends Channel {
    @Override
    public String getName() {
        return Constants.MESSAGE_CHANNEL;
    }
}
