package com.redecommunity.api.spigot.updater.file;

import com.google.common.collect.Lists;
import com.google.common.io.ByteSource;
import com.google.common.io.Files;
import com.redecommunity.api.spigot.SpigotAPI;
import com.redecommunity.common.shared.Common;
import lombok.Getter;
import org.apache.commons.io.Charsets;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by @SrGutyerrez
 */
public class PluginFile {
    @Getter
    private List<File> files = Lists.newArrayList();

    public PluginFile() {
        try {
            this.load();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void load() throws IOException {
        File file = this.createFile();

        FileReader fileReader = new FileReader(file);

        JSONArray jsonArray = (JSONArray) JSONValue.parse(fileReader);

        jsonArray.forEach(o -> {
            File file1 = new File("/plugins/" + o);

            this.files.add(file1);
        });
    }

    private File createFile() throws IOException {
        this.createFolder();

        File file = new File(SpigotAPI.getInstance().getDataFolder() + File.separator + "plugins.json");

        if (!file.exists()) {
            file.createNewFile();

            ByteSource byteSource = new ByteSource() {
                @Override
                public InputStream openStream() {
                    return Common.getInstance().getResource("plugins.json");
                }
            };

            String fileValues = byteSource.asCharSource(Charsets.UTF_8).read();

            Files.write(fileValues, file, Charsets.UTF_8);
        }

        return file;
    }

    private void createFolder() throws IOException {
        File folder = SpigotAPI.getInstance().getDataFolder();

        if (!folder.exists()) folder.mkdir();
    }
}
