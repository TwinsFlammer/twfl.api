package com.redefocus.api.shared.cash.event;

/**
 * @author SrGutyerrez
 */
public interface ICashChangeEvent {
    void dispatchCashChange(Integer newAmount, Integer oldAmount);
}
