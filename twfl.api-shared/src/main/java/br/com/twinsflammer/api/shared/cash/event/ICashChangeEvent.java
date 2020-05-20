package br.com.twinsflammer.api.shared.cash.event;

import br.com.twinsflammer.common.shared.permissions.user.data.User;

/**
 * @author SrGutyerrez
 */
public interface ICashChangeEvent {
    void dispatchCashChange(User user, Integer newAmount, Integer oldAmount);
}
