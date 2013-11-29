package com.daeyunshin.catchat.android;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.widget.ListView;

public class MainActivity extends FragmentActivity {
    private ListView messageList;
    private MessageViewAdapter adapter;
    private NetworkService networkService;
    private Boolean serviceRunning;
    private MessageHandler messageHandler;

    /**
     * callbacks for service binding, passed to bindService()
     */
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            NetworkService.LocalBinder binder = (NetworkService.LocalBinder) service;
            networkService = binder.getService();
            serviceRunning = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            serviceRunning = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);

        adapter = new MessageViewAdapter(MainActivity.this);

        messageList = (ListView) findViewById(R.id.list);
        messageList.setAdapter(adapter);

        messageHandler = new MessageHandler(adapter);
    }

    public MessageViewAdapter getAdapter() {
        return adapter;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, NetworkService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (serviceRunning) {
            unbindService(serviceConnection);
            serviceRunning = false;
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageHandler, new IntentFilter(NetworkService.NEW_MESSAGE));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(messageHandler);
    }

    public NetworkService getNetworkService() {
        return networkService;
    }
}