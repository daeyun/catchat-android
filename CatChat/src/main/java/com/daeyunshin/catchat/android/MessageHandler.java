package com.daeyunshin.catchat.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.daeyunshin.catchat.android.helpers.IntentAction;

import java.util.Date;

/**
 * Created by daeyun on 11/29/13.
 */
public class MessageHandler extends BroadcastReceiver {
    MessageViewAdapter adapter;

    public MessageHandler(MessageViewAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.v("LOG", "onReive called");
        String action = intent.getAction();
        if (action.equalsIgnoreCase(IntentAction.NEW_MESSAGE)) {
            Bundle extra = intent.getExtras();

            String sender = extra.getString("sender");
            String message = extra.getString("message");

            adapter.add(new SingleMessage(sender, "+", new Date(), message));
            adapter.notifyDataSetChanged();
            Log.v("LOG", "onReive ending here");
        }
    }
}
