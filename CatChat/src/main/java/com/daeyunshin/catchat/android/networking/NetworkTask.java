package com.daeyunshin.catchat.android.networking;

/**
 * Created by daeyun on 11/28/13.
 */
public class NetworkTask {
    private NetworkTaskType type;
    private String data;

    public NetworkTask(NetworkTaskType type, String data) {
        this.type = type;
        this.data = data;
    }

    public NetworkTaskType getType() {
        return type;
    }

    public String getData() {
        return data;
    }
}
