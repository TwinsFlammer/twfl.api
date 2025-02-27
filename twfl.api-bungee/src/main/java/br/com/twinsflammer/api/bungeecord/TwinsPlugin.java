package br.com.twinsflammer.api.bungeecord;

import br.com.twinsflammer.common.shared.Common;
import br.com.twinsflammer.common.shared.updater.data.Updater;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.IOException;

/**
 * Created by @SrGutyerrez
 */
@RequiredArgsConstructor
public abstract class TwinsPlugin extends Plugin {
    @Override
    public void onLoad() {
    }

    @Override
    public final void onEnable() {
        this.onEnablePlugin();
    }

    @Override
    public final void onDisable() {
        Updater updater = new Updater(this.getFile(), Common.getBranch());

        try {
            updater.download();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        this.onDisablePlugin();
    }

    public abstract void onEnablePlugin();

    public abstract void onDisablePlugin();
}