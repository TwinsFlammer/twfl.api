package com.redefocus.api.shared.cash.listener;

import com.redefocus.api.shared.API;
import com.redefocus.api.shared.cash.event.ICashChangeEvent;
import com.redefocus.common.shared.databases.redis.handler.JedisMessageListener;
import com.redefocus.common.shared.databases.redis.handler.annonation.ChannelName;
import com.redefocus.common.shared.databases.redis.handler.event.JedisMessageEvent;
import com.redefocus.common.shared.permissions.user.data.User;
import com.redefocus.common.shared.permissions.user.manager.UserManager;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 * @author SrGutyerrez
 */
public class CashChannelJedisMessageListener implements JedisMessageListener {
    @ChannelName(name = "cash_channel")
    public void onMessage(JedisMessageEvent event) {
        String message = event.getMessage();

        JSONObject jsonObject = (JSONObject) JSONValue.parse(message);

        Integer userId = ((Long) jsonObject.get("user_id")).intValue();
        Integer newAmount = ((Long) jsonObject.get("new_amount")).intValue();
        Integer oldAmount = ((Long) jsonObject.get("old_amount")).intValue();

        User user = UserManager.getUser(userId);

        user.setCash(newAmount);

        ICashChangeEvent ICashChangeEvent = API.getInstance().getICashChangeEvent();

        if (ICashChangeEvent != null)
            ICashChangeEvent.dispatchCashChange(
                    newAmount,
                    oldAmount
            );
    }
}
