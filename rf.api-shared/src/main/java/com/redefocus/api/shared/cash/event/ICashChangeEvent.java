package com.redefocus.api.shared.cash.event;

import com.redefocus.common.shared.permissions.user.data.User;

/**
 * @author SrGutyerrez
 */
public interface ICashChangeEvent {
    void dispatchCashChange(User user, Integer newAmount, Integer oldAmount);
}
