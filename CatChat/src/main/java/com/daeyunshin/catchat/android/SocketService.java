package com.daeyunshin.catchat.android;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created by daeyun on 11/22/13.
 */
public class SocketService extends Service {
    SocketChannel socketChannel;
    private final IBinder myBinder = new LocalBinder();

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
        try {
            socketChannel = SocketChannel.open();

            Toast.makeText(this, "Service created ...", Toast.LENGTH_LONG).show();
            Runnable connect = new connectSocket();
            new Thread(connect).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class LocalBinder extends Binder {
        SocketService getService() {
            // Return this instance of LocalService so clients can call public methods
            return SocketService.this;
        }
    }

    class connectSocket implements Runnable {
        @Override
        public void run() {
            Log.v("CatLog", "Thread is running");
            try {
                socketChannel.connect(new InetSocketAddress("192.168.1.105", 10100));
            } catch (IOException e) {
                Log.v("CatLog", "Thread exception!");
                e.printStackTrace();
                // Connection error
            }

            ByteBuffer buf = ByteBuffer.allocateDirect(1024);

            try {
                while (true) {
                    buf.clear();
                    int numBytesRead = socketChannel.read(buf);

                    if (numBytesRead == -1) {
                        break;
                    } else {
                        buf.flip();
                        Log.v("LOG", toString(buf));
                    }
                }
            } catch (IOException e) {
                // Connection may have been closed
            }

            Log.v("LOG", "Thread reached end.");
        }

        public String toString(ByteBuffer bb) {
            final byte[] bytes = new byte[bb.remaining()];

            bb.duplicate().get(bytes);

            return new String(bytes);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            socketChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
