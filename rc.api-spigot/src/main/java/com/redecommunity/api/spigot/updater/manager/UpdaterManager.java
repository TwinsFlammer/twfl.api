package com.redecommunity.api.spigot.updater.manager;

import com.redecommunity.api.spigot.updater.file.PluginFile;
import com.redecommunity.common.shared.Common;
import com.redecommunity.common.shared.updater.data.Updater;

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
