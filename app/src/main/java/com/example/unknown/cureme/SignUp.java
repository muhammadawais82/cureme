package com.example.unknown.cureme;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class SignUp extends Activity {
    DatabaseHelper myDb = new DatabaseHelper(this);
    private RadioGroup radioGroup;
    private RadioButton patient, doctor;
    String Status = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        radioGroup = findViewById(R.id.user_type);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if (checkedId == R.id.type_rdoc) {
                    Status = "Doctor";
                    Toast.makeText(getApplicationContext(), "Doctor Selected", Toast.LENGTH_SHORT).show();
                } else if (checkedId == R.id.type_rpat) {
                    Status = "Patient";
                    Toast.makeText(getApplicationContext(), "Patiant Selected", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    public void onSignUpClick(View v) {
        if (v.getId() == R.id.btn_signin_reg) {
            Intent intent = new Intent(SignUp.this, SignIn.class);
            startActivity(intent);
        }
        if (v.getId() == R.id.submit_S) {
            EditText usernam = (EditText) findViewById(R.id.user_n);
            EditText pass = (EditText) findViewById(R.id.pass_S);
            EditText Cpass = (EditText) findViewById(R.id.Cpass_S);


            String userstr = usernam.getText().toString();
            String passstr = pass.getText().toString();
            String cpassstr = Cpass.getText().toString();


            if (userstr.length()<4){
                Toast.makeText(this, "Please Enter atleat 4 words for username", Toast.LENGTH_SHORT).show();
            }
            else{
                if (passstr.length()<4){
                    Toast.makeText(this, "Enter Atleat 4 words for Password", Toast.LENGTH_SHORT).show();
                }

            else {
                if (!passstr.equals(cpassstr)) {
                    Toast.makeText(SignUp.this, "Password Doesn't Match", Toast.LENGTH_LONG).show();
                } else {
                    if (TextUtils.isEmpty(Status)) {
                        Toast.makeText(this, "choose option", Toast.LENGTH_SHORT).show();
                    } else {
                        if (myDb.searchUser(userstr).equals("Welcome")) {
                            myDb.signUpData(userstr, passstr, Status);
                            Toast.makeText(this, "Resigtration Successfully", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(SignUp.this, "Username Already Exists", Toast.LENGTH_LONG).show();
                        }
                    }
                }
                }
            }
        }
    }
}
