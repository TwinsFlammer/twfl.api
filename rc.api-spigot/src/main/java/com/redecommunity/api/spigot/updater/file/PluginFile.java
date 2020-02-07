package com.redecommunity.api.spigot.updater.file;

import com.google.common.collect.Lists;
import com.redecommunity.api.spigot.SpigotAPI;
import lombok.Getter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
        File file = this.loadFile();

        FileReader fileReader = new FileReader(file);

        JSONObject jsonObject = (JSONObject) JSONValue.parse(fileReader);

        JSONArray jsonArray = (JSONArray) jsonObject.get("plugins");

        jsonArray.forEach(o -> {
            File file1 = new File("/plugins/" + o);

            this.files.add(file1);
        });
    }

    private File loadFile() throws IOException {
        return new File(SpigotAPI.getInstance().getDataFolder() + File.separator + "configuration.json");
    }
}
