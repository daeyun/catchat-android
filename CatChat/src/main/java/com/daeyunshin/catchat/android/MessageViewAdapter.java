package com.daeyunshin.catchat.android;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by daeyun on 11/21/13.
 */
public class MessageViewAdapter extends ArrayAdapter<SingleMessage> {
    private Activity context;

    public MessageViewAdapter(Activity context) {
        super(context, R.layout.message_single, new ArrayList<SingleMessage>());

        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View messageView = inflater.inflate(R.layout.message_single, null, true);

        TextView msgTime = (TextView) messageView.findViewById(R.id.msg_time);
        TextView msgText = (TextView) messageView.findViewById(R.id.msg_text);

        String time = getItem(position).getTimeString();
        msgTime.setText(time);

        String msg = getItem(position).getMessageText();
        msgText.setText(msg);

        return messageView;
    }
}