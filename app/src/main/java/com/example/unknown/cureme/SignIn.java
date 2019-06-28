package com.example.unknown.cureme;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;


public class SignIn extends AppCompatActivity {
    DatabaseHelper helper = new DatabaseHelper(this);
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        sessionManager = new SessionManager(this);
        if (sessionManager.isLoggedIn()) {
            HashMap<String, String> user = sessionManager.getUserDetails();
            String status = user.get(sessionManager.KEY_STATUS);

            if (status.equals("Doctor")) {
                // User is already logged in. Take him to main activity
                Intent intent = new Intent(SignIn.this, DoctorActivity.class);
                startActivity(intent);
                finish();
            } else {
                // User is already logged in. Take him to main activity
                Intent intent = new Intent(SignIn.this, TabActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }


    public void onButtonClick(View v) {
        if (v.getId() == R.id.btn_signup) {
            Intent intent = new Intent(SignIn.this, SignUp.class);
            startActivity(intent);
        }
        if (v.getId() == R.id.btn_signin) {
            EditText usrname = (EditText) findViewById(R.id.u_name_L);
            String strUsername = usrname.getText().toString();
            EditText passw = (EditText) findViewById(R.id.pass_L);
            String pas = passw.getText().toString();
            String passwords = helper.searchPass(strUsername, pas);
            if (passwords.equals("Error")) {
                Toast.makeText(SignIn.this, "Enter wrong password or username", Toast.LENGTH_LONG).show();
            } else {
                String[] LOGIN_DATA = passwords.split("_");
                String id = LOGIN_DATA[0];
                String name = LOGIN_DATA[1];
                String status_ = LOGIN_DATA[2];

                if (LOGIN_DATA[2].equals("Doctor")) {
                    sessionManager.setLogin(true, id, name, status_);
                    Intent intent = new Intent(SignIn.this, DoctorActivity.class);
                    intent.putExtra("Username", strUsername);
                    startActivity(intent);
                } else {
                    sessionManager.setLogin(true, id, name, status_);
                    Intent intent = new Intent(SignIn.this, TabActivity.class);
                    intent.putExtra("Username", strUsername);
                    startActivity(intent);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    finishAffinity();
                }
                //Toast.makeText(this, passwords, Toast.LENGTH_SHORT).show();
            }

        }

    }
}