package com.daeyunshin.catchat.android.tests;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.daeyunshin.catchat.android.MainActivity;
import com.daeyunshin.catchat.android.MessageViewAdapter;
import com.daeyunshin.catchat.android.R;
import com.daeyunshin.catchat.android.SingleMessage;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;

import java.util.Date;

import static org.fest.assertions.api.Assertions.assertThat;

@RunWith(RobolectricGradleTestRunner.class)
public class MainActivityTest {

    private MainActivity activity;

    @Before
    public void setup() {
        activity = Robolectric.buildActivity(MainActivity.class).create().resume().get();
    }

    @Test
    public void testLayoutNotNull() {
        assertThat(activity).isNotNull();

        ListView listView = (ListView) activity.findViewById(R.id.list);
        assertThat(listView).isNotNull();

        Button sendButton = (Button) activity.findViewById(R.id.button_send);
        assertThat(sendButton).isNotNull();

        EditText editMessage = (EditText) activity.findViewById(R.id.edit_message);
        assertThat(editMessage).isNotNull();
    }

    @Test
    public void testAddToMessageListView() {
        MessageViewAdapter messageViewAdapter = activity.getAdapter();
        int itemCount = messageViewAdapter.getCount();
        assertThat(itemCount).isEqualTo(0);

        messageViewAdapter.add(new SingleMessage("daeyun", "+", new Date(), "supgaiz"));
        messageViewAdapter.notifyDataSetChanged();
        itemCount = messageViewAdapter.getCount();
        assertThat(itemCount).isEqualTo(1);
    }

    @Test
    public void testSendButtonClearEditText() {
        EditText editText = (EditText) activity.findViewById(R.id.edit_message);
        editText.setText("supgaiz");

        assertThat(editText.getText().toString()).isEqualTo("supgaiz");

        Button sendButton = (Button) activity.findViewById(R.id.button_send);

        try {
            sendButton.performClick();
        } catch (Exception e) {
            // ignore all exceptions
        }

        assertThat(editText.getText().toString()).isEqualTo("");
    }
}