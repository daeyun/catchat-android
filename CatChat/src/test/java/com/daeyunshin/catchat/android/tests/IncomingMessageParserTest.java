package com.daeyunshin.catchat.android.tests;

import android.content.Intent;
import android.os.Bundle;

import com.daeyunshin.catchat.android.helpers.IncomingMessageParser;
import com.daeyunshin.catchat.android.helpers.IntentAction;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.fest.assertions.api.Assertions.assertThat;

@RunWith(RobolectricGradleTestRunner.class)
public class IncomingMessageParserTest {

    @Test
    public void testMessageToIntentNewMessage() throws Exception {
        Intent intent = IncomingMessageParser.
                messageToIntent("NEW_MESSAGE #cats daeyun ~daeyun@unaffiliated/daeyun Hello World");

        Bundle extra = intent.getExtras();

        assertThat(intent.getAction()).isEqualTo(IntentAction.NEW_MESSAGE);
        assertThat(extra.getString("sender")).isEqualTo("daeyun");
        assertThat(extra.getString("senderHostmask")).isEqualTo("~daeyun@unaffiliated/daeyun");
        assertThat(extra.getString("message")).isEqualTo("Hello World");
        assertThat(extra.getString("channel")).isEqualTo("#cats");
    }

    @Test
    public void testMessageToIntentNewMessageSpace() throws Exception {
        Intent intent = IncomingMessageParser.
                messageToIntent("NEW_MESSAGE ##cats daeyun ~daeyun@unaffiliated/daeyun  ");

        Bundle extra = intent.getExtras();

        assertThat(intent.getAction()).isEqualTo(IntentAction.NEW_MESSAGE);
        assertThat(extra.getString("sender")).isEqualTo("daeyun");
        assertThat(extra.getString("senderHostmask")).isEqualTo("~daeyun@unaffiliated/daeyun");
        assertThat(extra.getString("message")).isEqualTo(" ");
        assertThat(extra.getString("channel")).isEqualTo("##cats");
    }

    @Test
    public void testMessageToIntentNewMessageInvalidAction() throws Exception {
        Intent intent = IncomingMessageParser.
                messageToIntent("NEW__MESSAGE #cats daeyun ~daeyun@unaffiliated/daeyun hi");

        assertThat(intent).isEqualTo(null);
    }

    @Test
    public void testMessageToIntentNewMessageInvalidChannel() throws Exception {
        /* channel names must start with # */
        Intent intent = IncomingMessageParser.
                messageToIntent("NEW_MESSAGE cats daeyun ~daeyun@unaffiliated/daeyun hi");

        assertThat(intent).isEqualTo(null);
    }

    @Test
    public void testMessageToIntentNewMessageMissingInfo() throws Exception {
        /* channel names must start with # */
        Intent intent = IncomingMessageParser.
                messageToIntent("NEW_MESSAGE #cats daeyun ~daeyun@unaffiliated/daeyun");

        assertThat(intent).isEqualTo(null);
    }
}