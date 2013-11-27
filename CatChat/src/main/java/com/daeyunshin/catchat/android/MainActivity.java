package com.daeyunshin.catchat.android;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.ListView;

import java.util.Date;

public class MainActivity extends FragmentActivity {
    ListView messageList;
    MessageListAdapter adapter;

    private SocketService mService;
    private Boolean mBound;

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
        Log.v("CatLog", "Activity created");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);

        adapter = new MessageListAdapter(MainActivity.this);

        messageList = (ListView) findViewById(R.id.list);
        messageList.setAdapter(adapter);
    }

    public MessageListAdapter getAdapter() {
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
}