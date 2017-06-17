package com.example.imm.mypractice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class RegisterActivity extends AppCompatActivity {

    Toolbar t;
    private Button signup;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("Sign Up");

        t = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(t);

        signup = (Button) findViewById(R.id.btnRegSubmit);
        signup.setOnClickListener(clickListener);

    }


    private View.OnClickListener clickListener = new View.OnClickListener() {


        @Override
        public void onClick(View v) {

            switch(v.getId()){
                case R.id.btnRegSubmit:
                    startCodeConfirmation();
            }
        }
    };



    private void startCodeConfirmation(){
        Intent intent = new Intent(this,CodeConfirmationActivity.class);
        startActivity(intent);
    }

}
