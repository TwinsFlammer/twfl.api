package br.com.twinsflammer.api.spigot;

import br.com.twinsflammer.common.shared.Common;
import br.com.twinsflammer.common.shared.updater.data.Updater;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

/**
 * Created by @SrGutyerrez
 */
@RequiredArgsConstructor
public abstract class FocusPlugin extends JavaPlugin {
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

        if (!this.isEnabled())
            Bukkit.shutdown();

        this.onDisablePlugin();
    }

    public abstract void onEnablePlugin();

    public abstract void onDisablePlugin();

    public void log(String message) {
        Common.getInstance().log(message);
    }
}