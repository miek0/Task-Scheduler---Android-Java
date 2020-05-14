package com.example.taskscheduler;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//Purpose: This activity is responsible for signing up new users.
public class Signup_Activity extends AppCompatActivity {

    UserDatabase mUserDatabase;
    private EditText username_text;
    private EditText password_text;
    private EditText confirm_password_text;
    private EditText fname_text;
    private EditText lname_text;
    private EditText email_address_text;
    private EditText phone_text;
    private Button signup_button;
    private String[] user_fields;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_layout);

        Intent signup_intent = this.getIntent();

        username_text = findViewById(R.id.username_text_box);
        password_text = findViewById(R.id.password_text_box);
        confirm_password_text = findViewById(R.id.confirm_password_text_box);
        fname_text = findViewById(R.id.fname_text_box);
        lname_text = findViewById(R.id.lname_text_box);
        email_address_text = findViewById(R.id.email_text_box);
        phone_text = findViewById(R.id.phone_text_box);
        signup_button = findViewById(R.id.signup_button);
        mUserDatabase = new UserDatabase(this);

        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(create_new_user()) {
                    finish();
                }
            }
        });
    }


    //    Boolean statement. If all requirement are met and verified, return true, otherwise, clear and return false.
//    Requirements: Username not taken, password is confirmed, email is "valid", phone number is "valid"
    public boolean create_new_user() {
        if(verify_username(username_text.getText().toString())) {
            if(verify_user_setup() && verify_email_address(email_address_text.getText().toString()) && verify_phone_number(phone_text.getText().toString()) && confirm_password()) {
                user_fields = new String[]{username_text.getText().toString(), password_text.getText().toString(), fname_text.getText().toString(),
                                           lname_text.getText().toString(), email_address_text.getText().toString(), phone_text.getText().toString()};
                toastMessage("Your account has been created.");
                mUserDatabase.addData(user_fields);
                return true;
            }
        }
        password_text.setText("");
        confirm_password_text.setText("");
        return false;
    }

    //    Check login_dictionary for existing usernames. If it's free, return true, else false.
    public boolean verify_username(String username) {
        Cursor data = mUserDatabase.getItemID(username); //get the id associated with that name
        int itemID = -1;
        while (data.moveToNext()) {
            itemID = data.getInt(0);
        }
        if(itemID > -1) {
            toastMessage("Username already exists.");
            return false;
        }
        return true;
    }

//    Check to see if all fields are at least filled in. If so, true, else, false.
    public boolean verify_user_setup() {
        if( !username_text.getText().toString().isEmpty() &&
            !password_text.getText().toString().isEmpty() &&
            !confirm_password_text.getText().toString().isEmpty() &&
            !email_address_text.getText().toString().isEmpty() &&
            !phone_text.getText().toString().isEmpty()
            ) {
            return true;
        }
        toastMessage("All fields must be filled in.");
        return false;
    }

//    Verify email string based on [all letters and numbers and ._]@[only letters].[only letters]
//    No spaces allowed. Numbers and symbols only allowed before the @.
    public boolean verify_email_address(String email) {
        if(!email.contains(" ")) {
            Pattern pattern = Pattern.compile("[a-zA-Z0-9._]+(\\@)[a-zA-Z]+(\\.)([a-zA-Z])+");
            Matcher matcher = pattern.matcher(email);
            if(matcher.matches()) {
                return true;
            }
        }
        toastMessage("Email must be properly formatted.");
        return false;
    }

//    Format of phone number verification is (XXX)XXX-XXXX. Many ways to write phone numbers.
    public boolean verify_phone_number(String text) {
        Pattern pattern = Pattern.compile("[(]\\d{3}[)]\\d{3}[-]\\d{4}");
        Matcher matcher = pattern.matcher(text);
        if(matcher.matches()) {
            return true;
        }
        toastMessage("Phone number must be in the format of (XXX)XXX-XXXX");
        return false;
    }

//    Check to see if password and confirm_password matched up.
    public boolean confirm_password() {
        if(password_text.getText().toString().equals(confirm_password_text.getText().toString()))
            return true;
        toastMessage("Passwords must match.");
        return false;
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
