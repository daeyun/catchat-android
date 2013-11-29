package com.daeyunshin.catchat.android.networking;

import android.util.Log;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by daeyun on 11/28/13.
 */
public class NetworkHandler extends Thread {
    private SSLSocketHandler socketHandler;
    private LinkedBlockingQueue<NetworkTask> blockingQueue;
    private String host;
    private int port;
    private SSLSocketHandler.Callback callback;


    public NetworkHandler(String host, int port, LinkedBlockingQueue<NetworkTask> blockingQueue,
                          SSLSocketHandler.Callback callback) {
        this.host = host;
        this.port = port;
        this.blockingQueue = blockingQueue;
        this.callback = callback;
    }

    @Override
    public void run() {
        socketHandler = new SSLSocketHandler(host, port, callback);
        socketHandler.start();

        try {
            while (true) {
                NetworkTask task = blockingQueue.take();
                switch (task.getType()) {
                    case SEND_MESSAGE:
                        socketHandler.sendMessage(task.getData());
                        break;
                    case CLOSE_CONNECTION:
                        socketHandler.terminate();
                    default:
                }
            }
        } catch (Exception e) {
            Log.e("LOG", Log.getStackTraceString(e));
        }
    }
}
