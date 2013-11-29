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
    private final IBinder myBinder = new LocalBinder();
    public static final String NEW_MESSAGE = "NEW_MESSAGE";
    LinkedBlockingQueue<NetworkTask> blockingQueue;
    NetworkHandler networkHandler;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        blockingQueue = new LinkedBlockingQueue<NetworkTask>();

        networkHandler = new NetworkHandler("192.168.1.105", 10100, blockingQueue, new SSLSocketHandler.Callback() {
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

        NetworkTask task = new NetworkTask(NetworkTaskType.SEND_MESSAGE, "How's everyone doing this fine evening.");
        blockingQueue.add(task);

    }

    public class LocalBinder extends Binder {
        NetworkService getService() {
            // Return this instance of LocalService so clients can call public methods
            return NetworkService.this;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        NetworkTask task = new NetworkTask(NetworkTaskType.CLOSE_CONNECTION, null);
        blockingQueue.add(task);
    }
}
