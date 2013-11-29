package com.daeyunshin.catchat.android.helpers;

import android.content.Intent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by daeyun on 11/29/13.
 */
public class IncomingMessageParser {


    public static Intent messageToIntent(String message) {
        Matcher lineMatcher = Pattern.compile("([^ ]+) ([^\r\n]+)").matcher(message);
        if (lineMatcher.matches()) {
            String action = lineMatcher.group(1);
            String data = lineMatcher.group(2);

            if (action.equals(IntentAction.NEW_MESSAGE)) {
                Intent intent = new Intent(IntentAction.NEW_MESSAGE);

                System.out.println("DATA: " + data);
                Matcher contentMatcher = Pattern.compile("(#[^ ]+) ([^ ]+) ([^ ]+) ([^\r\n]+)").matcher(data);

                if (contentMatcher.matches()) {
                    intent.putExtra("channel", contentMatcher.group(1));
                    intent.putExtra("sender", contentMatcher.group(2));
                    intent.putExtra("senderHostmask", contentMatcher.group(3));
                    intent.putExtra("message", contentMatcher.group(4));
                    return intent;
                }
            }
        }

        return null;
    }
}
