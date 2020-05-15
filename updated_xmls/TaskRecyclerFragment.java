package com.example.taskscheduler;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class TaskRecyclerFragment extends Fragment {

    private RecyclerView task_list_rv;
    private TaskListAdapter task_list_adapter;
    private LinkedList<String> task_list = new LinkedList<>();

    class TaskListAdapter extends
            RecyclerView.Adapter<TaskListAdapter.TaskViewHolder> {

        private final LinkedList<String> mTaskList;
        private final LayoutInflater mInflater;

        class TaskViewHolder extends RecyclerView.ViewHolder
                implements View.OnClickListener {

            final TextView taskTitleView;
            final TextView taskDueDateView;
            final TaskListAdapter mAdapter;

            /**
             * Creates a new custom view holder to hold the view to display in the RecyclerView.
             *
             * @param itemView The view in which to display the data.
             * @param adapter The adapter that manages the the data and views for the RecyclerView.
             */
            public TaskViewHolder(View itemView, TaskListAdapter adapter) {
                super(itemView);
                taskTitleView = itemView.findViewById(R.id.task_title);
                taskDueDateView = itemView.findViewById(R.id.task_due_date);
                this.mAdapter = adapter;
                itemView.setOnClickListener(this);
            }

            //            Whenever an item is clicked in the recycler view, do this
            @Override
            public void onClick(View v) {

//                Get the item position of what was clicked.
                int itemPosition = task_list_rv.getChildLayoutPosition(v);

//                The item is a string combination with the delimiter ***, so in regex that is "\\*\\*\\*"

//                Toasts For Debugging
                Toast test = Toast.makeText(v.getContext(), "Test Toast: Text = " + task_list.get(itemPosition), Toast.LENGTH_LONG);
                test.show();

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.home_page_layout, new EditTaskFragment()).addToBackStack(null);
                transaction.commit();
                notifyDataSetChanged();
            }
        }

        public TaskListAdapter(Context context, LinkedList<String> wordList) {
            mInflater = LayoutInflater.from(context);
            this.mTaskList = wordList;
        }

        /**
         * Inflates an item view and returns a new view holder that contains it.
         * Called when the RecyclerView needs a new view holder to represent an item.
         *
         * @param parent The view group that holds the item views.
         * @param viewType Used to distinguish views, if more than one
         *                 type of item view is used.
         * @return a view holder.
         */
        @Override
        public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // Inflate an item view.
            View itemView = mInflater.inflate(R.layout.task_list_item, parent, false);
            return new TaskViewHolder(itemView, this);
        }

        /**
         * Sets the contents of an item at a given position in the RecyclerView.
         *
         * @param holder The view holder for that position in the RecyclerView.
         * @param position The position of the item in the RecycerView.
         */
        @Override
        public void onBindViewHolder(TaskViewHolder holder, int position) {
            // Retrieve the data for that position.
            String current = mTaskList.get(position);
            // Add the data to the view holder.
            holder.taskTitleView.setText(current.split("\\*\\*\\*")[0]);
            holder.taskDueDateView.setText(current.split("\\*\\*\\*")[1]);
        }



        /**
         * Returns the size of the container that holds the data.
         *
         * @return Size of the list of data.
         */
        @Override
        public int getItemCount() {
            return mTaskList.size();
        }

        @Override
        public long getItemId(int position) {
            return super.getItemId(position);
        }

        @Override
        public int getItemViewType(int position) {
            return super.getItemViewType(position);
        }
    }

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
//                FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                transaction.replace(R.id.home_page_layout, new EditTaskFragment()).addToBackStack(null);
//                transaction.commit();
            }
        });

        task_list_rv = rootView.findViewById(R.id.task_list_rv);
        task_list_rv.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        task_list_adapter = new TaskListAdapter(rootView.getContext(), task_list);
        task_list_rv.setAdapter(task_list_adapter);

        task_list.add("Task 1***Due Date: 10/7/2019***Justin Mabutas");
        task_list.add("Task 2***Due Date: 10/7/2019***Justin Mabutas");
        task_list.add("Task 3***Due Date: 10/7/2019***Justin Mabutas");


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

