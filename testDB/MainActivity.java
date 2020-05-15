package com.example.taskscheduler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    UserDatabase mUserDatabase;
    private Button btnViewData;
    private EditText username_text, password_text;
    private Button signup_button, login_button;
    String username = "";
    CheckBox keepLoggedcheck;
    boolean isChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUserDatabase = new UserDatabase(this);
        username_text = findViewById(R.id.username_text_box);
        password_text = findViewById(R.id.password_text_box);
        signup_button = findViewById(R.id.signup_button);
        login_button = findViewById(R.id.login_button);
        keepLoggedcheck = findViewById(R.id.keepLoggedCheck);

        btnViewData = findViewById(R.id.btnView);

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final SharedPreferences.Editor editor = settings.edit();

        keepLoggedcheck = findViewById(R.id.keepLoggedCheck);
        keepLoggedcheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editor.putString("username", username);
                editor.putBoolean("isChecked", isChecked);
                editor.commit();
            }
        });

        //takes user to the sign up page
        signup_button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signup_intent = new Intent(MainActivity.this, Signup_Activity.class);
                startActivity(signup_intent);
            }
        });

        //upon successful login, takes user to the home page
        login_button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(verify_login_credentials()) {
                    isChecked = keepLoggedcheck.isChecked();
                    Intent login_intent = new Intent(MainActivity.this, HomeScreenSlidePagerActivity.class);
                    editor.putString("username", username);
                    editor.putBoolean("isChecked", isChecked);
                    editor.commit();
                    startActivity(login_intent);
                }
            }
        });

        btnViewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListDataActivity.class);
                startActivity(intent);
            }
        });
    }

    public boolean verify_login_credentials() {
        username = username_text.getText().toString();
        String password = password_text.getText().toString();
        int itemID = -1;
        String itemPW = "";

        Cursor data = mUserDatabase.getItemID(username); //get the id associated with that name
        while (data.moveToNext()) {
            itemID = data.getInt(0);
        }
        Cursor dataPW = mUserDatabase.getItemField(itemID, "password");
        while (dataPW.moveToNext()) {
            itemPW = dataPW.getString(0);
        }

        if(itemID > -1) {   //if username is correct
            if(itemPW.equals(password))     //if pw is correct
                return true;
            else {
                toastMessage("Incorrect password.");
                password_text.setText("");
            }
        }
        else{
            toastMessage("That user does not exist.");
            username_text.setText("");
            password_text.setText("");
        }
        return false;
    }

    /**
     * customizable toast
     *
     * @param message
     */
    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
