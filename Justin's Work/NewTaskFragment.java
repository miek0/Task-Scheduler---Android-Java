package com.example.taskscheduler;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import java.sql.Time;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class NewTaskFragment extends Fragment {

    private EditText task_title_edit;
    private EditText task_description_field;
    private Button pick_due_date;
    private TextView due_date_text;
    private RadioButton progress1_btn;
    private RadioButton progress2_btn;
    private Button finish_btn;

    private String username;

    private Calendar calendar;
    private int day,month,year;

    public View onCreateView(LayoutInflater inflater, ViewGroup
            container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.new_task_fragment,container, false);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(rootView.getContext());
        username=prefs.getString("username",username);

        task_title_edit = rootView.findViewById(R.id.task_title_edit);
        task_description_field = rootView.findViewById(R.id.task_description_field);
        pick_due_date = rootView.findViewById(R.id.pick_due_date);
        due_date_text = rootView.findViewById(R.id.due_date_text);
        progress1_btn = rootView.findViewById(R.id.progress1_btn);
        progress2_btn = rootView.findViewById(R.id.progress2_btn);
        finish_btn = rootView.findViewById(R.id.finish_btn);

        calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
//        January starts at 0
        month+=1;

        String format_date = DateFormat.getDateInstance().format(calendar.getTime());
        due_date_text.setText(format_date);

        pick_due_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(NewTaskFragment.this.getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int temp_year, int temp_month, int temp_day) {
                        month = temp_month+1;
                        day=temp_day;
                        year=temp_year;
                        due_date_text.setText(temp_month+1 + "/" + temp_day + "/" + temp_year);
                    }
                }, year, month-1, day);
                datePickerDialog.show();
            }
        });
        finish_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), task_title_edit.getText(), Toast.LENGTH_SHORT).show();
            }
        });
        return rootView;
    }
}

