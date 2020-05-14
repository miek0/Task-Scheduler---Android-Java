package com.example.taskscheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    UserDatabase mUserDatabase;
    private Button btnViewData;
    private EditText username_text, password_text;
    private Button signup_button, login_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username_text = findViewById(R.id.username_text_box);
        password_text = findViewById(R.id.password_text_box);
        signup_button = findViewById(R.id.signup_button);
        login_button = findViewById(R.id.login_button);

        btnViewData = (Button) findViewById(R.id.btnView);
        mUserDatabase = new UserDatabase(this);

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
                    Intent login_intent = new Intent(MainActivity.this, ScreenSliderPagerActivity.class);
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
        String username = username_text.getText().toString();
        String password = password_text.getText().toString();
        int itemID = -1;
        String itemPW = "";

        Cursor data = mUserDatabase.getItemID(username); //get the id associated with that name
        while (data.moveToNext()) {
            itemID = data.getInt(0);
        }
        Cursor dataPW = mUserDatabase.getItemPW(username);
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
