package com.example.imm.citi.technicalClasses;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Toast;

import com.example.imm.citi.activities.CodeConfirmationActivity;
import com.example.imm.citi.activities.HomeActivity;
import com.example.imm.citi.activities.SuperRegRes;

import java.util.Random;

/**
 * Created by Sujoy on 6/30/2017.
 */

public class ConfirmationCodeGenerator {
    public String code;
    Activity parent;
    final long valTime = 15;
    public long secondsPassed=0;

    public ConfirmationCodeGenerator(SuperRegRes regRes1){
        parent = regRes1;
    }

    public ConfirmationCodeGenerator(CodeConfirmationActivity confAct1, String s, long sec){
        parent = confAct1;
        code = s;
        secondsPassed = sec;
        startThread();
    }






    public void sendConfCode(String email){
        new SendMailTask().execute(email);
    }

    private String getConfCode() {
        final String alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final int N = alphabet.length();

        Random rand = new Random();
        code="";
        for(int i=0; i<6; i++){
            code += alphabet.charAt(rand.nextInt(N));
        }

        secondsPassed = 0;
        startThread();

        return code;
    }

    private void startThread() {
        parent.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new CountDownTimer(valTime*60*1000-secondsPassed*1000, 1000) {
                    @Override
                    public void onTick(long l) {
                        secondsPassed++;
                    }

                    public void onFinish() {
                        code="";
                        Toast.makeText(parent,"The Confirmation Code has expired",Toast.LENGTH_LONG).show();
                    }
                }.start();
            }
        });
    }



    class SendMailTask extends AsyncTask {
        private ProgressDialog statusDialog;

        protected void onPreExecute() {
            statusDialog = new ProgressDialog(parent);
            statusDialog.setMessage("Getting ready...");
            statusDialog.setIndeterminate(false);
            statusDialog.setCancelable(false);
            statusDialog.show();
        }

        @Override
        protected Object doInBackground(Object... args) {
            try {
                Log.i("SendMailTask", "About to instantiate GMail...");

                publishProgress("Processing input....");
                CitiMail androidEmail = new CitiMail(args[0].toString(), getConfCode());
                publishProgress("Preparing mail message....");
                androidEmail.createEmailMessage();
                publishProgress("Sending email....");
                androidEmail.sendEmail();
                publishProgress("Email Sent.");

                Log.i("SendMailTask", "Mail Sent.");
            } catch (Exception e) {
                publishProgress(e.getMessage());
                Log.e("SendMailTask", e.getMessage(), e);
                parent.startActivity(new Intent(parent, HomeActivity.class));
            }
            return "done";
        }

        @Override
        public void onProgressUpdate(Object... values) {
            statusDialog.setMessage(values[0].toString());
        }

        @Override
        public void onPostExecute(Object result) {
            statusDialog.dismiss();
            Toast.makeText(parent,"A Confirmation Code was sent to the email address you specified",Toast.LENGTH_LONG).show();
            Toast.makeText(parent,"The Confirmation Code is valid for " + valTime + " minutes",Toast.LENGTH_LONG).show();

            if(!(parent instanceof CodeConfirmationActivity)){
                System.out.println("NOT CONF ACTIVITY");
                SuperRegRes sup = (SuperRegRes) parent;
                sup.startCodeConfirmation(code, secondsPassed);
            }
        }

    }
}
