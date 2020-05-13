package com.example.todo_application;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class ProfileSliderFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup
                             container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.profile_slider,container, false);
        return rootView;
    }
}

