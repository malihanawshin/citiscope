package com.example.imm.citi.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.imm.citi.R;
import com.example.imm.citi.technicalClasses.ConfirmationCodeGenerator;
import com.example.imm.citi.technicalClasses.Registration;

public class CodeConfirmationActivity extends AppCompatActivity {
    Button submit, resend, cancel;
    EditText eCode;
    Activity confAct = this;
    Registration reg;
    String code, email, password;
    long secPassed;
    ConfirmationCodeGenerator confGen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_confirmation);
        setTitle("Code Confirmation");

        submit = (Button) findViewById(R.id.btnCodeSubmit);
        resend = (Button) findViewById(R.id.btnCodeReset);
        cancel = (Button) findViewById(R.id.btnCodeCancel);

        eCode = (EditText) findViewById(R.id.etxtCode);


        code = getIntent().getStringExtra("code");
        email = getIntent().getStringExtra("email");
        password = getIntent().getStringExtra("password");
        secPassed = getIntent().getLongExtra("secPassed", 0);

        reg = new Registration(email, password, confAct);
        confGen = new ConfirmationCodeGenerator((CodeConfirmationActivity)confAct, code, secPassed);

        if(getSupportActionBar()!=null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setListeners();
    }

    private void setListeners(){
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelConfCode();
            }
        });

        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resendConfCode();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strCode = eCode.getText().toString();

                if(strCode.equals("")){
                    Toast.makeText(confAct,"Please fill in the required field",Toast.LENGTH_LONG).show();
                }
                else{
                    validateConfCode(strCode.toUpperCase());
                }
            }
        });
    }


    public void cancelConfCode() {
//        Intent intent = new Intent(this, RegistrationActivity.class);
//        startActivity(intent);

        onBackPressed();
    }

    public void resendConfCode() {
        confGen.sendConfCode(email);
    }

    public void validateConfCode(String str) {
        if(verifyConfirmationCode(str)) {
            reg.register(confAct);
        }
        else
            Toast.makeText(confAct,"Invalid Confirmation Code " + confGen.code,Toast.LENGTH_LONG).show();
    }


    public Boolean verifyConfirmationCode(String str){
        if(str.equals(confGen.code))
            return true;
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
