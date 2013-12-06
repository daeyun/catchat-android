package com.daeyunshin.catchat.android.helpers;

import android.content.Intent;
import android.util.Log;

import org.json.JSONObject;

/**
 * Created by daeyun on 11/29/13.
 */
public class IncomingMessageParser {


    public static Intent messageToIntent(String message) {
        try {
            JSONObject jsonObject = new JSONObject(message);
            if (jsonObject.has("hash")) {
                Intent intent = new Intent(IntentAction.CONFIRMATION);
                intent.putExtra("hash", (String) jsonObject.get("hash"));
                return intent;
            } else {
                String type = (String) jsonObject.get("type");

                if (type.equals(IntentAction.NEW_MESSAGE)) {
                    Intent intent = new Intent(IntentAction.NEW_MESSAGE);
                    intent.putExtra("channel", (String) jsonObject.get("channel"));
                    intent.putExtra("sender", (String) jsonObject.get("sender_nick"));
                    intent.putExtra("senderHostmask", (String) jsonObject.get("sender_hostmask"));
                    intent.putExtra("message", (String) jsonObject.get("message"));
                    return intent;
                }
            }
        } catch (Exception e) {
            Log.e("LOG", Log.getStackTraceString(e));
        }
        return null;
    }
}
