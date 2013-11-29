package com.daeyunshin.catchat.android.networking;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class SSLSocketHandler extends Thread {
    private String host;
    private int port;
    private Callback callback;
    private PrintWriter writer;
    private BufferedReader reader;
    private volatile boolean running;
    private SSLSocket socket;

    public SSLSocketHandler(String host, int port, Callback callback) {
        this.host = host;
        this.port = port;
        this.callback = callback;
        this.running = false;
    }

    public void sendMessage(String message) {
        if (writer != null) {
            writer.println(message);
            writer.flush();
        }
    }

    public void terminate() throws Exception {
        running = false;
        socket.close();
    }

    @Override
    public void run() {
        running = true;

        try {
            SSLSocketFactory socketFactory = SSLSocketContext.getContext().getSocketFactory();
            socket = (SSLSocket) socketFactory.createSocket(this.host, this.port);

            InputStream inputstream = socket.getInputStream();
            InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
            reader = new BufferedReader(inputstreamreader);

            OutputStream outputstream = socket.getOutputStream();
            OutputStreamWriter outputstreamwriter = new OutputStreamWriter(outputstream);
            BufferedWriter bufferedWriter = new BufferedWriter(outputstreamwriter);
            writer = new PrintWriter(bufferedWriter);

            try {
                while (running) {
                    String line = reader.readLine();
                    if (line != null) {
                        callback.onMessageReceived(line);
                    }
                }
            } catch (Exception e) {
                // terminate()
            } finally {
                socket.close();
            }
        } catch (Exception e) {
            Log.e("LOG", Log.getStackTraceString(e));
        }
    }

    public interface Callback {
        public void onMessageReceived(String message);
    }
}