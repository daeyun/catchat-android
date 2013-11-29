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
    ListView messageList;
    MessageViewAdapter adapter;

    private SocketService mService;
    private Boolean mBound;

    private MessageHandler messageHandler;

    /**
     * callbacks for service binding, passed to bindService()
     */
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            SocketService.LocalBinder binder = (SocketService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
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
        Intent intent = new Intent(this, SocketService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageHandler, new IntentFilter(SocketService.NEW_MESSAGE));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(messageHandler);
    }

    public SocketService getSocketService() {
        return mService;
    }
}