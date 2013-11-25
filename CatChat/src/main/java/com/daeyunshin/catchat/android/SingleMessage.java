package com.daeyunshin.catchat.android;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by daeyun on 11/21/13.
 */
public class SingleMessage {
    private String nickname;
    private String userStatus;
    private Date date;
    private String message;

    public SingleMessage(String nickname, String userStatus, Date date, String message) {
        this.nickname = nickname;
        this.userStatus = userStatus;
        this.date = date;
        this.message = message;
    }

    public String getNickname() {
        return nickname;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public Date getDate() {
        return date;
    }

    public String getMessage() {
        return message;
    }

    public String getTimeString() {
        Date date = getDate();
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        return String.format("%02d:%02d", hour, minute);
    }

    public String getMessageText() {
        return String.format("<%s%s> %s", getUserStatus(), getNickname(), getMessage());
    }
}
