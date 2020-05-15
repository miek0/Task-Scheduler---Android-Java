package com.example.taskscheduler;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class ProfileSliderFragment extends Fragment {
    UserDatabase mUserDatabase;
    private Button about_btn, help_btn, logout_btn,edit_profile_btn;
    private TextView username_text, fullname_text, email_text, phone_text;
    String username = "";

    public View onCreateView(LayoutInflater inflater, ViewGroup
            container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.profile_info_fragment,container, false);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(rootView.getContext());
        username=prefs.getString("username",username);

        mUserDatabase = new UserDatabase(this.getContext());
        username_text = rootView.findViewById(R.id.username_text);
        fullname_text = rootView.findViewById(R.id.full_name_text);
        email_text = rootView.findViewById(R.id.email_text);
        phone_text = rootView.findViewById(R.id.phone_number_text);
        logout_btn = rootView.findViewById(R.id.logout_btn);
        about_btn = rootView.findViewById(R.id.about_btn);
        help_btn = rootView.findViewById(R.id.help_btn);
        edit_profile_btn = rootView.findViewById(R.id.edit_btn);

        int itemID = -1;
        String item_fullname = "";
        String item_email = "";
        String item_phone = "";
        String temp = "";
        Cursor cursor = mUserDatabase.getItemID(username);
        while (cursor.moveToNext()) {
            itemID = cursor.getInt(0);
        }

        String fname = "";
        String lname = "";
        cursor = mUserDatabase.getItemField(itemID, "firstname");
        while (cursor.moveToNext()) {
            fname = cursor.getString(0);
        }
        cursor = mUserDatabase.getItemField(itemID, "lastname");
        while (cursor.moveToNext()) {
            lname = cursor.getString(0);
        }
        item_fullname = fname + " " + lname;

        cursor = mUserDatabase.getItemField(itemID, "email");
        while (cursor.moveToNext()) {
            temp = cursor.getString(0);
        }
        item_email = temp;

        cursor = mUserDatabase.getItemField(itemID, "phone");
        while (cursor.moveToNext()) {
            temp = cursor.getString(0);
        }
        item_phone = temp;

        username_text.setText(username);
        fullname_text.setText(item_fullname);
        email_text.setText(item_email);
        phone_text.setText(item_phone);

        edit_profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileSliderFragment.this.getContext(), ProfileEditActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
                ProfileSliderFragment.this.getActivity().finish();
            }
        });

        help_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelpDialog help = new HelpDialog();
                help.show(ProfileSliderFragment.this.getFragmentManager(), "Help Dialog");
            }
        });

        about_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AboutDialog about = new AboutDialog();
                about.show(ProfileSliderFragment.this.getFragmentManager(), "About Dialog");
            }
        });

        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("isChecked", false);
                editor.commit();

                Intent backtomain = new Intent(ProfileSliderFragment.this.getActivity(), MainActivity.class);
                startActivity(backtomain);
                ProfileSliderFragment.this.getActivity().finish();
            }
        });
        return rootView;
    }
}

