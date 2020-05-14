package com.example.todo_application;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String message = intent.getStringExtra("toast_message");
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }

}
