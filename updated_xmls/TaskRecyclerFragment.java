package com.example.taskscheduler;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class NewTaskFragment extends Fragment {

    private EditText task_title_edit;
    private EditText task_description_field;
    private EditText due_date_edit;
    private RadioButton progress1_btn;
    private RadioButton progress2_btn;
    private Button finish_btn;

    private String username;

    public View onCreateView(LayoutInflater inflater, ViewGroup
            container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.new_task_fragment,container, false);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(rootView.getContext());
        username=prefs.getString("username",username);

        task_title_edit = rootView.findViewById(R.id.task_title_edit);
        task_description_field = rootView.findViewById(R.id.task_description_field);
        due_date_edit = rootView.findViewById(R.id.due_date_edit);
        progress1_btn = rootView.findViewById(R.id.progress1_btn);
        progress2_btn = rootView.findViewById(R.id.progress2_btn);
        finish_btn = rootView.findViewById(R.id.finish_btn);

        finish_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), task_title_edit.getText(), Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }
}

