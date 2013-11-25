package com.daeyunshin.catchat.android;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by daeyun on 11/20/13.
 */
public class InputBarFragment extends Fragment {
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_input_bar, container, false);
        this.view = view;

        Button sendButton = (Button) view.findViewById(R.id.button_send);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });

        return view;
    }

    public void sendMessage() {
        EditText editText = (EditText) view.findViewById(R.id.edit_message);
        String msg = editText.getText().toString();

        if (!msg.matches("")) {
            Log.v("CatLog", msg);
        }
    }
}