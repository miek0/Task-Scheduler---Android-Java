package com.example.todo_application;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class EditTaskFragment extends Fragment {

    private Button finish_btn;

    public View onCreateView(LayoutInflater inflater, ViewGroup
            container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.edit_task,container, false);

        finish_btn = rootView.findViewById(R.id.finish_btn);
        finish_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Pass Intents through here in order to save tasks to TaskRecyclerFragment

                getFragmentManager().popBackStack();
            }
        });

        return rootView;
    }
}

