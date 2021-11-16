package com.example.collegeinformationsystem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.collegeinformationsystem.MainActivity;


public class RegistrationActivity extends AppCompatActivity {

    EditText username, mail, number, password, confirm;
    LinearLayout button;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String Name = "nameKey";
    public static final String Mail = "mailKey";
    public static final String Phone = "PhoneKey";
    public static final String Pass = "passKey";
    public static final String Confirm = "confirmKey";
    SharedPreferences.Editor editor= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        username = findViewById(R.id.username);
        mail = findViewById(R.id.mail);
        number = findViewById(R.id.number);
        password = findViewById(R.id.password);
        confirm = findViewById(R.id.confirm);
        button = findViewById(R.id.button);
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String myname = username.getText().toString();
                        String myemail = mail.getText().toString();
                        String mynumber = number.getText().toString();
                        String mypass = password.getText().toString();
                        String myconfirm = confirm.getText().toString();
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                        if (myname.isEmpty()) {
                            Toast.makeText(RegistrationActivity.this, "enter the name", Toast.LENGTH_SHORT).show();
                        } else if (myemail.isEmpty()) {
                            Toast.makeText(RegistrationActivity.this, "enter the mail", Toast.LENGTH_SHORT).show();
                        } else if (!myemail.matches(emailPattern)) {
                            Toast.makeText(RegistrationActivity.this, "Invalid Email Address", Toast.LENGTH_SHORT).show();
                        } else if (mynumber.isEmpty()) {
                            Toast.makeText(RegistrationActivity.this, "enter the number", Toast.LENGTH_SHORT).show();
                        } else if (mypass.isEmpty()) {
                            Toast.makeText(RegistrationActivity.this, "enter the password", Toast.LENGTH_SHORT).show();
                        } else if (mypass.length() < 6) {
                            Toast.makeText(RegistrationActivity.this, "password must 6 character", Toast.LENGTH_SHORT).show();
                        } else if (myconfirm.isEmpty()) {
                            Toast.makeText(RegistrationActivity.this, "enter the confirm password", Toast.LENGTH_SHORT).show();
                        } else if (mynumber.length() < 13) {
                            Toast.makeText(RegistrationActivity.this, "Invalid Mobile Number", Toast.LENGTH_SHORT).show();
                        } else {
                            if (mypass.equals(myconfirm)) {
                                editor.putString(Name, myname);
                                editor.putString(Mail, myemail);
                                editor.putString(Phone,mynumber);
                                editor.putString(Pass, mypass);
                                editor.putString(Confirm, myconfirm);
                                editor.commit();
                                startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                                finish();

                            }

                        }
                    }
                }
        );

    }

}

