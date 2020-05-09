package com.redefocus.api.shared;

import com.redefocus.api.shared.cash.event.ICashChangeEvent;
import com.redefocus.api.shared.manager.StartManager;
import lombok.Getter;

/**
 * Created by @SrGutyerrez
 */
public class API {
    @Getter
    private static API instance;

    @Getter
    private ICashChangeEvent ICashChangeEvent;

    public API() {
        API.instance = this;

        new StartManager();
    }

    public void setICashChangeEvent(ICashChangeEvent ICashChangeEvent) {
        this.ICashChangeEvent = ICashChangeEvent;
    }
}
