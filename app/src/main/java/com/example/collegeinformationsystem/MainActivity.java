package com.example.collegeinformationsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText mail,pass_word,name;
   LinearLayout button;
    TextView forget,register,myOutputBox;
    String E_mail,Password;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String Mail = "mailKey";
    public static final String Pass = "passKey";
    private static final String TAG =MainActivity.class.getSimpleName();
    SharedPreferences.Editor editor = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mail=findViewById(R.id.mail);
        pass_word=findViewById(R.id.pass_word);
        button=findViewById(R.id.button);
        myOutputBox=findViewById(R.id.myOutputBox);
        SharedPreferences sharedPreferences=getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        E_mail=sharedPreferences.getString(Mail,"myemail");
        Password=sharedPreferences.getString(Pass,"mypass");
      mail.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                if (mail.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+") && s.length() > 0)
                {
                    myOutputBox.setText("valid mail");
                }
                else
                {
                    myOutputBox.setText("in correct");
                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }
        });
      button.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) { if (mail.getText().toString().equals(E_mail)&& pass_word.getText().toString().equals(Password)){
              editor.putBoolean("isLogin",true);
              editor.commit();
              Toast.makeText(getApplicationContext(),"Login Success",Toast.LENGTH_SHORT).show();
              startActivity(new Intent(MainActivity.this, HomeActivity.class));
              finish();
          }
          else{
              editor.putBoolean("isLogin",false);
              editor.commit();
              Toast.makeText(getApplicationContext(),"Invalid user and password",Toast.LENGTH_SHORT).show();
          }
          }
      }
      );
    }
    public void register(View view) {
        startActivity(new Intent(MainActivity.this,RegistrationActivity.class));
    }
}