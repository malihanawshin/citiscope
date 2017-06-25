package com.example.imm.mypractice.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imm.mypractice.R;
import com.example.imm.mypractice.technicalClasses.Authentication;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    Toolbar t;
    private TextView forgotPassword;
    private TextView createAccount;

    private EditText emailText, pwdText;

    Activity parent = this;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Sign In");

        t = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(t);

        forgotPassword = (TextView) findViewById(R.id.text_forgotPassword);
        forgotPassword.setOnClickListener(clickListener);

        createAccount = (TextView) findViewById(R.id.text_createAccount);
        createAccount.setOnClickListener(clickListener);

        emailText = (EditText) findViewById(R.id.etxtEmail);
        pwdText = (EditText) findViewById(R.id.etxtPassword);
        setListeners();
    }

    private void setListeners() {
        Button btnLogIn = (Button) findViewById(R.id.btnLoginSubmit);
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtEmail = emailText.getText().toString();
                String txtPwd = pwdText.getText().toString();

                if (txtEmail.equals("") || txtPwd.equals("")) {
                    Toast.makeText(parent, "Please enter all fields", Toast.LENGTH_LONG).show();
                    return;
                }

                Authentication auth = new Authentication();
                auth.verifyLoginCredentials(txtEmail, txtPwd, parent);
            }
        });
    }


    private View.OnClickListener clickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            switch(v.getId()){
                case R.id.text_forgotPassword:
                    resetPassword();
                    break;
                case R.id.text_createAccount:
                    showRegisterPage();
                    break;
            }
        }
    };

    public void resetPassword(){
        Intent intent = new Intent(LoginActivity.this,ResetPasswordActivity.class);
        startActivity(intent);
    }


    private void showRegisterPage() {
        Intent intent = new Intent(this,RegistrationActivity.class);
        startActivity(intent);
    }

}

