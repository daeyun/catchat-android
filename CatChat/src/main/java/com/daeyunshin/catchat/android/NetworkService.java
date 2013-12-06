package com.daeyunshin.catchat.android;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.daeyunshin.catchat.android.helpers.IncomingMessageParser;
import com.daeyunshin.catchat.android.networking.NetworkHandler;
import com.daeyunshin.catchat.android.networking.NetworkTask;
import com.daeyunshin.catchat.android.networking.NetworkTaskType;
import com.daeyunshin.catchat.android.networking.SSLSocketHandler;

import org.json.JSONObject;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by daeyun on 11/22/13.
 */
public class NetworkService extends Service {
    private final IBinder binder = new LocalBinder();
    private LinkedBlockingQueue<NetworkTask> taskQueue;
    private NetworkHandler networkHandler;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        taskQueue = new LinkedBlockingQueue<NetworkTask>();

        networkHandler = new NetworkHandler("192.168.1.103", 10100, taskQueue, new SSLSocketHandler.Callback() {
            @Override
            public void onMessageReceived(String message) {
                Log.v("LOG", message);
                sendBroadcast(IncomingMessageParser.messageToIntent(message));
            }
        });
        networkHandler.start();
    }

    public class LocalBinder extends Binder {
        NetworkService getService() {
            // Return this instance of LocalService so clients can call public methods
            return NetworkService.this;
        }
    }

    public void sendMessage(String message, String target) {
        try {
            JSONObject data = new JSONObject();
            data.put("type", "send_message");
            data.put("message", message);
            data.put("target", target);

            NetworkTask task = new NetworkTask(NetworkTaskType.SEND_MESSAGE, data);
            taskQueue.add(task);
        } catch (Exception e) {
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        NetworkTask task = new NetworkTask(NetworkTaskType.CLOSE_CONNECTION, null);
        taskQueue.add(task);
    }
}
