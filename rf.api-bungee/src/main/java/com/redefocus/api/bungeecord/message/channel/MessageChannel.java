package com.redefocus.api.bungeecord.message.channel;

import com.redefocus.common.shared.databases.redis.channel.data.Channel;
import com.redefocus.common.shared.util.Constants;

/**
 * Created by @SrGutyerrez
 */
public class MessageChannel extends Channel {
    @Override
    public String getName() {
        return Constants.MESSAGE_CHANNEL;
    }
}
