package com.example.todo_application;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class NewTaskFragment extends Fragment {

//    private Button help_btn;

    public View onCreateView(LayoutInflater inflater, ViewGroup
            container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.new_task_fragment,container, false);

//        help_btn = rootView.findViewById(R.id.help_btn);
//        help_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                help_btn.setText("You clicked me!");
//            }
//        });

        return rootView;
    }
}

