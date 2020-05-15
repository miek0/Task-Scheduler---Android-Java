package com.example.taskscheduler;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class AboutDialog extends AppCompatDialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("About This Program")
                .setMessage("This is a task scheduling application. Our goal is to provide the user with a " +
                        "user friendly experience that will assist in their time management skills. Our application" +
                        " offers the ability to add, edit, and delete tasks. Every task can have one set due date, and we" +
                        " plan to be able to notify the user for reminders and progress updates. We hope you, as the user, benefit" +
                        " from our work!")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        return builder.create();
    }
}