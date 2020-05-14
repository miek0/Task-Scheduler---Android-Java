package com.example.taskscheduler;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    UserDatabase mUserDatabase;
    private Button btnAdd, btnViewData;
    private EditText username_text, password_text;
    private Button signup_button, login_button;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username_text = findViewById(R.id.username_text_box);
        password_text = findViewById(R.id.password_text_box);
        signup_button = findViewById(R.id.signup_button);
        login_button = findViewById(R.id.login_button);

        editText = (EditText) findViewById(R.id.editText);
        btnAdd = (Button) findViewById(R.id.btnAdd);
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

        //upon successful login, takes user to the welcome page
        login_button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(verify_login_credentials()) {
//                    open_welcome_activity();
//
//                }
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEntry = editText.getText().toString();
                if (editText.length() != 0) {
                    AddData(newEntry);
                    editText.setText("");
                } else {
                    toastMessage("You must put something in the text field!");
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
        return false;
    }

    public void AddData(String newEntry) {
        boolean insertData = mUserDatabase.addData(newEntry);

        if (insertData) {
            toastMessage("Data Successfully Inserted!");
        } else {
            toastMessage("Something went wrong");
        }
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
