package br.com.twinsflammer.api.bungeecord;

import br.com.twinsflammer.api.bungeecord.manager.StartManager;
import br.com.twinsflammer.api.bungeecord.reflection.Reflection;
import br.com.twinsflammer.api.bungeecord.user.data.BungeeUser;
import br.com.twinsflammer.api.bungeecord.user.factory.BungeeUserFactory;
import br.com.twinsflammer.api.shared.API;
import lombok.Getter;

/**
 * Created by @SrGutyerrez
 */
public class BungeeAPI extends TwinsPlugin {
    private static BungeeAPI instance;

    public BungeeAPI() {
        BungeeAPI.instance = this;
    }

    private Reflection reflection;

    private API api;

    @Getter
    private BungeeUserFactory<? extends BungeeUser> bungeeUserFactory;
    
    @Override
    public void onEnablePlugin() {
        this.reflection = new Reflection();

        new StartManager();

        this.api = new API();

        this.bungeeUserFactory = new BungeeUserFactory<>();
    }

    @Override
    public void onDisablePlugin() {

    }

    public static BungeeAPI getInstance() {
        return BungeeAPI.instance;
    }

    public Reflection getReflection() {
        return this.reflection;
    }

    public API getApi() {
        return this.api;
    }
}
