package com.daeyunshin.catchat.android;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.daeyunshin.catchat.android.networking.NetworkHandler;
import com.daeyunshin.catchat.android.networking.NetworkTask;
import com.daeyunshin.catchat.android.networking.NetworkTaskType;
import com.daeyunshin.catchat.android.networking.SSLSocketHandler;

import java.util.concurrent.LinkedBlockingQueue;

import static java.lang.Thread.sleep;

/**
 * Created by daeyun on 11/22/13.
 */
public class NetworkService extends Service {
    // Broadcast filters
    public static final String NEW_MESSAGE = "NEW_MESSAGE";

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

        networkHandler = new NetworkHandler("192.168.1.105", 10100, taskQueue, new SSLSocketHandler.Callback() {
            @Override
            public void onMessageReceived(String message) {
                Log.v("LOG", message);
            }
        });
        networkHandler.start();

        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public class LocalBinder extends Binder {
        NetworkService getService() {
            // Return this instance of LocalService so clients can call public methods
            return NetworkService.this;
        }
    }

    public void sendMessage(String message) {
        NetworkTask task = new NetworkTask(NetworkTaskType.SEND_MESSAGE, message);
        taskQueue.add(task);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        NetworkTask task = new NetworkTask(NetworkTaskType.CLOSE_CONNECTION, null);
        taskQueue.add(task);
    }
}
