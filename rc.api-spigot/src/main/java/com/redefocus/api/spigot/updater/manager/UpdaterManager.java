package com.redefocus.api.spigot.updater.manager;

import com.redefocus.api.spigot.updater.file.PluginFile;
import com.redefocus.common.shared.Common;
import com.redefocus.common.shared.updater.data.Updater;

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
