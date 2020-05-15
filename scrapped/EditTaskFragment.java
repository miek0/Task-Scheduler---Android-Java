package com.example.taskscheduler;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class EditTaskFragment extends Fragment {
    private UserDatabase mUserDatabase;
    private TaskDatabase mTaskDatabase;
    private Button finish_btn;
    private RadioButton progress1_btn, progress2_btn, progress3_btn;
    private EditText task_name_ET, task_desc_ET, task_duedate_ET;
    String username = "";
    String taskname = "";
    int userID = -1;

    public View onCreateView(LayoutInflater inflater, ViewGroup
            container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.edit_task,container, false);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(rootView.getContext());
        username = prefs.getString("username",username);
        taskname=prefs.getString("taskname",taskname);
        mUserDatabase = new UserDatabase(this.getActivity());
        mTaskDatabase = new TaskDatabase(this.getActivity());
        task_name_ET = rootView.findViewById(R.id.task_title_edit);
        task_desc_ET = rootView.findViewById(R.id.task_description_field);
        task_duedate_ET = rootView.findViewById(R.id.due_date_edit);
        progress1_btn = rootView.findViewById(R.id.progress1_btn);
        progress2_btn = rootView.findViewById(R.id.progress2_btn);
        progress3_btn = rootView.findViewById(R.id.progress3_btn);
        finish_btn = rootView.findViewById(R.id.finish_btn);
        finish_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update_task();
                getFragmentManager().popBackStack();
                Intent intent = new Intent(v.getContext(), HomeScreenSlidePagerActivity.class);
                startActivity(intent);
                EditTaskFragment.this.getActivity().finish();
                Toast.makeText(getActivity(), "Your task has been updated.", Toast.LENGTH_SHORT).show();
            }
        });
        return rootView;
    }

    public void update_task(){
        int taskID = -1;
        Cursor cursor = mUserDatabase.getItemID(username);
        while (cursor.moveToNext()) {
            userID = cursor.getInt(0);
        }
        cursor = mTaskDatabase.getItemName(taskID, userID);
        while (cursor.moveToNext()) {
            taskname = cursor.getString(0);
        }
        cursor = mTaskDatabase.getItemID(taskname, userID);
        while (cursor.moveToNext()) {
            taskID = cursor.getInt(0);
        }

        String oldtaskname = "";
        String oldtaskdesc = "";
        String oldtaskduedate = "";
        String oldtaskprog = "";
        cursor = mTaskDatabase.getDataOfUser(userID);
        Log.d("TEST","userID: "+userID);
        while(cursor.moveToNext()){
            oldtaskname = cursor.getString(2);
            oldtaskdesc = cursor.getString(3);
            oldtaskduedate = cursor.getString(4);
            oldtaskprog = cursor.getString(5);
        }
        String new_taskname = task_name_ET.getText().toString();
        String new_taskdesc = task_desc_ET.getText().toString();
        String new_taskduedate = task_duedate_ET.getText().toString();
        String prog = "";
        if(progress1_btn.isChecked()){
            prog = "Not Started";
        }else if(progress2_btn.isChecked()){
            prog = "Started";
        }else if(progress3_btn.isChecked()){
            prog = "Done";
        }else{
            prog = oldtaskprog;
        }

        if((!new_taskname.isEmpty()))
            mTaskDatabase.updateTask(new_taskname, oldtaskname, taskID, "taskname");
        if(!new_taskdesc.isEmpty())
            mTaskDatabase.updateTask(new_taskdesc, oldtaskdesc, taskID, "taskdesc");
        if(!new_taskduedate.isEmpty())
            mTaskDatabase.updateTask(new_taskduedate, oldtaskduedate,  taskID, "duedate");
        mTaskDatabase.updateTaskProgress(taskID, prog,"progress");
    }
}

