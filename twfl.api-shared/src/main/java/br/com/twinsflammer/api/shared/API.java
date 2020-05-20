package br.com.twinsflammer.api.shared;

import br.com.twinsflammer.api.shared.cash.event.ICashChangeEvent;
import br.com.twinsflammer.api.shared.manager.StartManager;
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
