package com.example.imm.citi.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.imm.citi.R;
import com.example.imm.citi.technicalClasses.Registration;

public class CodeConfirmationActivity extends AppCompatActivity {
    Button submit, resend, cancel;
    EditText eCode;
    CodeConfirmationActivity confAct = this;
    Registration reg;
    String code, email, password;


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
        reg = new Registration(email, password, code, confAct);

        if(getSupportActionBar()!=null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setListeners();
    }

    private void setListeners(){
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confAct.cancelConfCode();
            }
        });

        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confAct.resendConfCode();
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
                    confAct.validateConfCode(strCode.toUpperCase());
                }
            }
        });
    }


    public void cancelConfCode() {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }

    public void resendConfCode() {
        reg.sendConfCode();
    }

    public void validateConfCode(String str) {
        if(reg.verifyConfirmationCode(str)) {
            Toast.makeText(confAct, "You have successfully registered to CITISCOPE", Toast.LENGTH_LONG).show();
            reg.register();
            cancelConfCode();
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }
        else
            Toast.makeText(confAct,"Invalid Confirmation Code",Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
