package com.daeyunshin.catchat.android.networking;

import org.json.JSONObject;

/**
 * Created by daeyun on 11/28/13.
 */
public class NetworkTask {
    private NetworkTaskType type;
    private JSONObject data;

    public NetworkTask(NetworkTaskType type, JSONObject data) {
        this.type = type;
        this.data = data;
    }

    public NetworkTaskType getType() {
        return type;
    }

    public String getJSONString() {
        return data.toString();
    }
}
