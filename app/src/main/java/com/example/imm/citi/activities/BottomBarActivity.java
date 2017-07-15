package com.example.imm.citi.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imm.citi.R;
import com.example.imm.citi.technicalClasses.Authentication;
import com.example.imm.citi.technicalClasses.Database;
import com.example.imm.citi.technicalClasses.Notification;
import com.example.imm.citi.technicalClasses.RetrievalData;
import com.example.imm.citi.technicalClasses.User;
import com.example.imm.citi.technicalClasses.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

public class BottomBarActivity extends AppCompatActivity {
    Activity parent = this;
    ArrayList<Notification> notifications;

    private final String FETCHNOTIFFILE = "getNotifications.php";

    @Nullable
    @BindView(R.id.txt_notification_count)TextView notificationIcon;

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        updateNotificationIcon();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //notificationIcon = (TextView) bindView(R.id.txt_notification_count);
      //  ButterKnife.bind(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.overflow_menu, menu);

        if(User.loggedIn){
            MenuItem item = menu.findItem(R.id.action_sign_in);
            item.setVisible(false);
            MenuItem item2 = menu.findItem(R.id.action_sign_up);
            item2.setVisible(false);
            MenuItem item3 = menu.findItem(R.id.action_sign_out);
            item3.setVisible(true);
        }

        else {
            MenuItem item4 = menu.findItem(R.id.action_sign_out);
            item4.setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sign_in:
                showLoginPage();
                return true;
            case R.id.action_sign_out:
                Authentication auth = new Authentication(parent);
                auth.logout(parent);
                return true;
            case R.id.action_sign_up:
                showRegisterPage();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showLoginPage() {

        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);

    }


    private void showRegisterPage() {

        Intent intent = new Intent(this,RegistrationActivity.class);
        startActivity(intent);

    }


    @Optional
    @OnClick(R.id.img_notification)
    public void showNotification() {
        if(!(this instanceof NotificationActivity)) {
            if(User.loggedIn) {
                startActivity(new Intent(this, NotificationActivity.class));
                finish();
            }
            else{
                Toast.makeText(parent, "Log in first", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Optional
    @OnClick(R.id.img_home)
    public void showHome() {
        if(!(this instanceof HomeActivity)) {
            startActivity(new Intent(this,HomeActivity.class));
            finish();
        }
    }

    @Optional
    @OnClick(R.id.img_profile)
    public void showProfile() {
       // Toast.makeText(parent, User.loggedIn + "__" + User.Email,Toast.LENGTH_LONG).show();

        if(!(this instanceof ProfileActivity)) {
            if(User.loggedIn){
                startActivity(new Intent(this,ProfileActivity.class));
                finish();
            }
            else{
                Toast.makeText(parent, "Sign in first",Toast.LENGTH_LONG).show();
            }
        }
    }

    @Optional
    @OnClick(R.id.img_poll)
    public void showPoll() {
        if(!(this instanceof PollActivity)) {
            startActivity(new Intent(this,PollActivity.class));
            finish();
        }
    }

    protected void getOverflowMenu() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void updateNotificationIcon(){

        ArrayList<String> keys = new ArrayList<>(), vals = new ArrayList<>();

        keys.add("email");
        vals.add(User.Email);

        Database db = new Database();
        db.retrieve(new RetrievalData(keys, vals, FETCHNOTIFFILE, parent), false, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String response) {
                try {
                    ArrayList<Notification> notifs = new ArrayList<>();

                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray result = jsonObject.getJSONArray("result");

                    UpdateNotification(result.length());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    protected void UpdateNotification(int count){
        if (count != 0) {
            notificationIcon.setVisibility(View.VISIBLE);
            notificationIcon.setText( "" + count);
        }
        else notificationIcon.setVisibility(View.GONE);

    }
}
