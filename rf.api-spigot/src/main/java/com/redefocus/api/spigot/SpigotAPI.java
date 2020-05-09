package com.redefocus.api.spigot;

import com.comphenix.protocol.ProtocolLibrary;
import com.google.common.collect.Lists;
import com.google.common.io.ByteSource;
import com.google.common.io.Files;
import com.redefocus.api.spigot.hologram.protocol.HologramProtocol;
import com.redefocus.api.spigot.manager.StartManager;
import com.redefocus.api.spigot.nametag.manager.NametagManager;
import com.redefocus.api.spigot.reflection.Reflection;
import com.redefocus.api.spigot.restart.data.Restart;
import com.redefocus.api.spigot.updater.manager.UpdaterManager;
import com.redefocus.api.shared.API;
import com.redefocus.api.shared.connection.manager.ProxyServerManager;
import com.redefocus.api.spigot.user.data.SpigotUser;
import com.redefocus.api.spigot.user.factory.SpigotUserFactory;
import com.redefocus.common.shared.Common;
import com.redefocus.common.shared.databases.manager.DatabaseManager;
import com.redefocus.common.shared.databases.mysql.data.MySQL;
import com.redefocus.common.shared.databases.mysql.manager.MySQLManager;
import com.redefocus.common.shared.permissions.user.data.User;
import com.redefocus.common.shared.permissions.user.manager.UserManager;
import com.redefocus.common.shared.server.data.Server;
import com.redefocus.common.shared.server.manager.ServerManager;
import com.redefocus.common.shared.util.Printer;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.Charsets;
import org.bukkit.Bukkit;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by @SrGutyerrez
 */
public class SpigotAPI extends FocusPlugin {
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

    @Getter
    private NametagManager nametagManager;

    @Getter
    private static MySQL mySQL;

    @Getter
    private static SpigotUserFactory<? extends SpigotUser> spigotUserFactory;

    @Override
    public void onEnablePlugin() {
        this.reflection = new Reflection(this);

        this.setupServerSQL();

        new StartManager();

        this.api = new API();

        this.nametagManager = new NametagManager();

        HologramProtocol hologramProtocol = new HologramProtocol(this);

        ProtocolLibrary.getProtocolManager().addPacketListener(hologramProtocol);

        Server server = SpigotAPI.getCurrentServer();

        if (server != null) server.setStatus(SpigotAPI.getDefaultStatus());

        this.spigotUserFactory = new SpigotUserFactory<>();
    }

    @Override
    public void onDisablePlugin() {
        new UpdaterManager();
    }

    private void setupServerSQL() {
        DatabaseManager databaseManager = Common.getInstance().getDatabaseManager();

        MySQLManager mySQLManager = databaseManager.getMySQLManager();

        String mysqlName = "server", databaseName = this.getDefaultDatabaseName("server");

        SpigotAPI.mySQL = mySQLManager.createConnection(
                mysqlName,
                databaseName
        );
    }

    public static List<User> getUsers() {
        List<Integer> serversId = SpigotAPI.getSubServersId();

        return ProxyServerManager.getUsers()
                .stream()
                .filter(User::isOnline)
                .filter(user -> user.getServer() != null)
                .filter(user -> serversId.contains(user.getServer().getId()))
                .collect(Collectors.toList());
    }

    public static List<Integer> getSubServersId() {
        JSONArray jsonArray = (JSONArray) SpigotAPI.getConfiguration().get("servers");

        List<Integer> servers = Lists.newArrayList();

        Server server = SpigotAPI.getCurrentServer();

        servers.add(server.getId());

        jsonArray.forEach(o -> servers.add((Integer) o));

        return servers;
    }

    public static Server getCurrentServer() {
        final String[] defaultAddresses = new String[]{
                "0.0.0.0",
                "127.0.0.1",
                ""
        };

        String address = Bukkit.getIp();

        if (Arrays.asList(defaultAddresses).contains(address)) {
            Printer.INFO.coloredPrint(
                    "&cNão é possível localizar o servidor com o ip 0.0.0.0, altere na server.properties"
            );

            Bukkit.getServer().shutdown();

            return null;
        }

        Integer port = Bukkit.getPort();

        return ServerManager.getServer(address, port);
    }

    public static Integer getDefaultStatus() {
        JSONObject jsonObject = SpigotAPI.getConfiguration();

        return ((Long) jsonObject.get("default_status")).intValue();
    }

    public static Integer getRootServerId() {
        JSONObject jsonObject = SpigotAPI.getConfiguration();

        return ((Long) jsonObject.get("root_server_id")).intValue();
    }

    public static JSONObject getConfiguration() {
        try {

            File folder = SpigotAPI.getInstance().getDataFolder();

            if (!folder.exists()) folder.mkdirs();

            File file = new File(SpigotAPI.getInstance().getDataFolder() + File.separator + "configuration.json");

            if (!file.exists()) {
                file.createNewFile();

                ByteSource byteSource = new ByteSource() {
                    @Override
                    public InputStream openStream() {
                        return SpigotAPI.getInstance().getResource("configuration.json");
                    }
                };

                String fileValues = byteSource.asCharSource(Charsets.UTF_8).read();

                Files.write(fileValues, file, Charsets.UTF_8);
            }

            FileReader fileReader = new FileReader(file);

            return (JSONObject) JSONValue.parse(fileReader);
        } catch (IOException exception) {
            exception.printStackTrace();

            Bukkit.getServer().shutdown();
        }

        return null;
    }

    public String getDefaultDatabaseName(String database) {
        return String.format(
                "%s_%d",
                database,
                SpigotAPI.getCurrentServer().getId()
        );
    }

    public static void unloadUser(User user) {
        UserManager.removeUser(user.getId());
    }
}
