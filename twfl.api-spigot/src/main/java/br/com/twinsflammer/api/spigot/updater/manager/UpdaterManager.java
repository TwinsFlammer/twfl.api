package br.com.twinsflammer.api.spigot.updater.manager;

import br.com.twinsflammer.api.spigot.updater.file.PluginFile;
import br.com.twinsflammer.common.shared.Common;
import br.com.twinsflammer.common.shared.updater.data.Updater;

import java.io.IOException;

/**
 * Created by @SrGutyerrez
 */
public class UpdaterManager {
    public UpdaterManager() {
        PluginFile pluginFile = new PluginFile();

        pluginFile.getFiles().forEach(file -> {
            Updater updater = new Updater(file, Common.getBranch());

            try {
                updater.download();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });
    }
}
