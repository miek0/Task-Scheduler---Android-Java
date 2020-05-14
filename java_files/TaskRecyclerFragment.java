package com.example.todo_application;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class TaskRecyclerFragment extends Fragment {

    private NotificationManagerCompat notification_manager;
    private Button test_notification_btn;

    public View onCreateView(LayoutInflater inflater, ViewGroup
            container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.task_recycler_fragment,container, false);

        notification_manager = NotificationManagerCompat.from(rootView.getContext());

        test_notification_btn = rootView.findViewById(R.id.test_notification_btn);
        test_notification_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                sendNotificationThroughAIChannel(v, "Final Project Development", "How's the progress going for this task?");
//                sendNotificationThroughUserChannel(v, "User", "These messages are for user designated times");
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.home_page_layout, new EditTaskFragment()).addToBackStack(null);
                transaction.commit();
            }
        });


        return rootView;
    }

    public void sendNotificationThroughAIChannel(View v, String title, String message) {

        Intent intent = new Intent(v.getContext(), TaskRecyclerFragment.class);
        PendingIntent content_intent = PendingIntent.getActivity(v.getContext(),
                0, intent, 0);

        Intent broadcast_intent = new Intent(v.getContext(), NotificationReceiver.class);
        broadcast_intent.putExtra("toast_message", message);
        PendingIntent action_intent = PendingIntent.getBroadcast(v.getContext(),
                0, broadcast_intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(v.getContext(), NotificationHandler.AI_REMINDER_CHANNEL)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentIntent(content_intent)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .setColor(Color.BLUE)
                .addAction(R.mipmap.ic_launcher, "Haven't Started", action_intent)
                .addAction(R.mipmap.ic_launcher, "Started", action_intent)
                .addAction(R.mipmap.ic_launcher, "Done", action_intent)
                .build();

        notification_manager.notify(1,notification);
    }
    public void sendNotificationThroughUserChannel(View v, String title, String message) {

        Notification notification = new NotificationCompat.Builder(v.getContext(), NotificationHandler.USER_REMINDER_CHANNEL)
                .setSmallIcon(R.drawable.ic_user_notification)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notification_manager.notify(2,notification);
    }
}

