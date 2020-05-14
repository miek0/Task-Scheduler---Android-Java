package com.example.todo_application;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class NotificationHandler extends Application {

    public static final String AI_REMINDER_CHANNEL = "ai_channel";
    public static final String USER_REMINDER_CHANNEL = "user_channel";

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannels();
    }

    private void createNotificationChannels() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel ai_channel = new NotificationChannel(
                    AI_REMINDER_CHANNEL,
                    "AI Channel",
                    NotificationManager.IMPORTANCE_HIGH
            );
            ai_channel.setDescription("This is the notification channel for ai scheduled reminders.");

            NotificationChannel user_channel = new NotificationChannel(
                    USER_REMINDER_CHANNEL,
                    "User Channel",
                    NotificationManager.IMPORTANCE_HIGH
            );
            ai_channel.setDescription("This is the notification channel for user scheduled reminders.");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(ai_channel);
            manager.createNotificationChannel(user_channel);
        }
    }
}
