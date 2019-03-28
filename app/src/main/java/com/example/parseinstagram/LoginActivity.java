package com.example.parseinstagram;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private EditText etUsername;
    private EditText etPassWord;
    private Button btnLogin;
    private Button btnSignups;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        etUsername = findViewById(R.id.etUsername);
        etPassWord = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignups = findViewById(R.id.btnSignUps);
        btnSignups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(i);
            }
        });
        ParseUser currentUser = ParseUser.getCurrentUser();
        if(currentUser!= null){
            goMainActivity();
        }
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = etUsername.getText().toString();
                String pass = etPassWord.getText().toString();
                login(user, pass);
            }
        });

    }

    private void login(String user, String pass){
        ParseUser.logInInBackground(user, pass, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
               if(e != null){
                   Log.e(TAG, "Issue with logging in");
                   e.printStackTrace();
                   return;
               }
                ParseUser currentUser = ParseUser.getCurrentUser();
               goMainActivity();
            }
        });
    }

    private void goMainActivity(){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
