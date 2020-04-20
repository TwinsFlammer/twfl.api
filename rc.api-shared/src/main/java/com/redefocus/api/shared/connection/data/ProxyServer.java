package com.redefocus.api.shared.connection.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashSet;

/**
 * Created by @SrGutyerrez
 */
@AllArgsConstructor
public class ProxyServer {
    @Getter
    private final Integer id;
    @Getter
    private final String name;

    @Setter
    private Boolean status;
    @Getter
    @Setter
    private HashSet<Integer> usersId;

    public Boolean isOnline() {
        return this.status;
    }

    public Integer getPlayerCount() {
        return this.usersId.size();
    }

    public String toJSONString() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("proxy_id", this.id);

        JSONArray usersId = new JSONArray();

        usersId.addAll(this.usersId);

        jsonObject.put("users_id", usersId);
        jsonObject.put("name", this.name);
        jsonObject.put("status", this.status);

        return jsonObject.toString();
    }
}
