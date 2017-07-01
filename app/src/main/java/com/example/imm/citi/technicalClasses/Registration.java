package com.example.imm.citi.technicalClasses;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.example.imm.citi.activities.ProfileActivity;
import com.example.imm.citi.activities.SuperRegRes;

import java.util.ArrayList;

/**
 * Created by Sujoy on 4/10/2017.
 */

public class Registration {
    ArrayList<String> keysReg, keysConf;
    String regFile = "reg.php", confFile = "regConfirmation.php";
    public String email="", password="";
    Activity parent;
    Registration reg = this;

    public Registration(String sMail, String sPwd, Activity act) {
        initKeys();
        parent = act;
        email = sMail;
        password = sPwd;
    }

    private void initKeys() {
        keysReg = new ArrayList<>();
        keysReg.add("email");
        keysReg.add("password");

        keysConf = new ArrayList<>();
        keysConf.add("email");
    }










    public void verifyRegistrationInput(){
        ArrayList<String> vals = new ArrayList<>();
        vals.add(email);

        Database db = new Database();
        db.retrieve(new RetrievalData(keysConf, vals, confFile, parent), true, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
                if(result.equals("false"))
                    Toast.makeText(parent,"Email Already Exists",Toast.LENGTH_LONG).show();
                else{
                    ConfirmationCodeGenerator confGen = new ConfirmationCodeGenerator((SuperRegRes)parent);
                    confGen.sendConfCode(email);
                }
            }
        });
    }

    public void register(){
        ArrayList<String> vals = new ArrayList<>();
        vals.add(email);
        vals.add(password);

        Database db = new Database();
        db.update(new RetrievalData(keysReg, vals, regFile, parent), true, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
                if(result.equals("false"))
                    Toast.makeText(parent,"Sorry something went wrong. Try again.",Toast.LENGTH_LONG).show();
                else{
                    login();
                    Toast.makeText(parent, "You have successfully registered to CITISCOPE", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(parent, ProfileActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    parent.startActivity(intent);
                    parent.finish();
                }
            }
        });
    }

    public void login(){
        User.setAttributes(email,email,"","", parent);
    }





//    public void sendConfCode(){
//        new SendMailTask().execute(email);
//    }
//
//    private String getConfCode() {
//        final String alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
//        final int N = alphabet.length();
//
//        Random rand = new Random();
//        code="";
//        for(int i=0; i<6; i++){
//            code += alphabet.charAt(rand.nextInt(N));
//        }
//        parent.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                new CountDownTimer(valTime*60*1000, 12000) {
//                    @Override
//                    public void onTick(long l) {
//
//                    }
//
//                    public void onFinish() {
//                        code="";
//                        Toast.makeText(parent,"The Confirmation Code has expired",Toast.LENGTH_LONG).show();
//                    }
//                }.start();
//            }
//        });
//
//        return code;
//    }
//
//    class SendMailTask extends AsyncTask {
//
//        private ProgressDialog statusDialog;
//        private Activity sendMailActivity;
//
//        protected void onPreExecute() {
//            statusDialog = new ProgressDialog(parent);
//            statusDialog.setMessage("Getting ready...");
//            statusDialog.setIndeterminate(false);
//            statusDialog.setCancelable(false);
//            statusDialog.show();
//        }
//
//        @Override
//        protected Object doInBackground(Object... args) {
//            try {
//                Log.i("SendMailTask", "About to instantiate GMail...");
//
//                publishProgress("Processing input....");
//                CitiMail androidEmail = new CitiMail(args[0].toString(), getConfCode());
//                publishProgress("Preparing mail message....");
//                androidEmail.createEmailMessage();
//                publishProgress("Sending email....");
//                androidEmail.sendEmail();
//                publishProgress("Email Sent.");
//
//                Log.i("SendMailTask", "Mail Sent.");
//            } catch (Exception e) {
//                publishProgress(e.getMessage());
//                Log.e("SendMailTask", e.getMessage(), e);
//                parent.startActivity(new Intent(parent, HomeActivity.class));
//            }
//            return "done";
//        }
//
//        @Override
//        public void onProgressUpdate(Object... values) {
//            statusDialog.setMessage(values[0].toString());
//        }
//
//        @Override
//        public void onPostExecute(Object result) {
//            statusDialog.dismiss();
//            if(parent instanceof RegistrationActivity) {
//                RegistrationActivity regAct = (RegistrationActivity) parent;
//                regAct.startCodeConfirmation();
//            }
//        }
//
//    }
}
