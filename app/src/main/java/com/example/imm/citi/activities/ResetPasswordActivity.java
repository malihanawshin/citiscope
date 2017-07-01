package com.example.imm.citi.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.imm.citi.R;
import com.example.imm.citi.technicalClasses.Authentication;
import com.example.imm.citi.technicalClasses.User;

public class ResetPasswordActivity extends SuperRegRes {
    Authentication auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        setTitle("Reset Password");

        if(getSupportActionBar()!=null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnSignup = (Button) findViewById(R.id.btnReset);
        btnSignup.setOnClickListener(clickListener);

        btnClear = (Button) findViewById(R.id.btnPasswordClear);
        btnClear.setOnClickListener(clickListener);

        edtEmail = (EditText) findViewById(R.id.textEmailResetPass);
        setEmail();
        edtPwd1 = (EditText) findViewById(R.id.eTxtPwd1);
        edtPwd2 = (EditText) findViewById(R.id.eTxtPwd2);
    }

    private void setEmail() {
        if(User.loggedIn){
            edtEmail.setText(User.Email);
            edtEmail.setEnabled(false);
        }
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {


        @Override
        public void onClick(View v) {

            switch(v.getId()){
                case R.id.btnReset:
                    if(credentialsCorrect()) {
                        auth = new Authentication(regRes);
                        auth.verifyInput(edtEmail.getText().toString(), edtPwd1.getText().toString(),regRes);
                    }
                    break;
                case R.id.btnPasswordClear:
                    if(!User.loggedIn)
                        edtEmail.setText("");
                    edtPwd1.setText("");
                    edtPwd2.setText("");
            }
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }

}
