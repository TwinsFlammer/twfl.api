package com.redecommunity.api.bungeecord.message.channel;

import com.redecommunity.api.bungeecord.message.Message;
import com.redecommunity.common.shared.databases.redis.channel.data.Channel;

/**
 * Created by @SrGutyerrez
 */
public class MessageChannel extends Channel {
    @Override
    public String getName() {
        return Message.CHANNEL_NAME;
    }
}
