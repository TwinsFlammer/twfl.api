package com.redecommunity.api.spigot;

import com.google.common.io.ByteSource;
import com.google.common.io.Files;
import com.redecommunity.api.shared.API;
import com.redecommunity.api.spigot.manager.StartManager;
import com.redecommunity.api.spigot.reflection.Reflection;
import com.redecommunity.api.spigot.restart.data.Restart;
import com.redecommunity.api.spigot.updater.manager.UpdaterManager;
import com.redecommunity.common.shared.Common;
import com.redecommunity.common.shared.permissions.user.data.User;
import com.redecommunity.common.shared.permissions.user.manager.UserManager;
import com.redecommunity.common.shared.server.data.Server;
import com.redecommunity.common.shared.server.manager.ServerManager;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.Charsets;
import org.bukkit.Bukkit;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by @SrGutyerrez
 */
public class SpigotAPI extends CommunityPlugin {
    @Getter
    private static SpigotAPI instance;

    public SpigotAPI() {
        SpigotAPI.instance = this;
    }

    @Getter
    private Reflection reflection;

    @Getter
    private API api;

    @Setter
    @Getter
    private Restart restart;

    @Override
    public void onEnablePlugin() {
        this.reflection = new Reflection(this);

        new StartManager();

        this.api = new API();
    }

    @Override
    public void onDisablePlugin() {
        new UpdaterManager();
    }

    public static Server getCurrentServer() {
        String address = Bukkit.getIp();
        Integer port = Bukkit.getPort();

        return ServerManager.getServer(address, port);
    }

    public static Integer getDefaultStatus() {
        try {
            File file = new File(SpigotAPI.getInstance().getDataFolder() + File.separator + "plugins.json");

            if (!file.exists()) {
                file.createNewFile();

                ByteSource byteSource = new ByteSource() {
                    @Override
                    public InputStream openStream() {
                        return Common.getInstance().getResource("configuration.json");
                    }
                };

                String fileValues = byteSource.asCharSource(Charsets.UTF_8).read();

                Files.write(fileValues, file, Charsets.UTF_8);
            }

            FileReader fileReader = new FileReader(file);

            JSONObject jsonObject = (JSONObject) JSONValue.parse(fileReader);

            return ((Long) jsonObject.get("default_status")).intValue();
        } catch (IOException exception) {
            exception.printStackTrace();

            Bukkit.getServer().shutdown();
        }

        return null;
    }

    public static void unloadUser(User user) {
        UserManager.removeUser(user.getId());
    }
}
