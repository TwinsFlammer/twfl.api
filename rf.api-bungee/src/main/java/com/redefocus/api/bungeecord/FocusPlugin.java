package com.redefocus.api.bungeecord;

import com.redefocus.common.shared.Common;
import com.redefocus.common.shared.updater.data.Updater;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.IOException;

/**
 * Created by @SrGutyerrez
 */
@RequiredArgsConstructor
public abstract class FocusPlugin extends Plugin {
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