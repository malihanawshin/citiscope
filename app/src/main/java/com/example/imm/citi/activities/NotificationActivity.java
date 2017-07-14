package com.example.imm.citi.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.imm.citi.R;
import com.example.imm.citi.technicalClasses.Database;
import com.example.imm.citi.technicalClasses.Notification;
import com.example.imm.citi.technicalClasses.RetrievalData;
import com.example.imm.citi.technicalClasses.User;
import com.example.imm.citi.technicalClasses.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;

public class NotificationActivity extends BottomBarActivity implements NotificationListAdapter.NotificationClickCallback{

    private RecyclerView recyclerView;
    ArrayList <Notification> notifList;
    NotificationListAdapter adapter;
    ImageButton toRemove;

    private Activity parent = this;

    private final String FETCHNOTIFFILE = "getNotifications.php", REMOVENOTIFFILE = "removeNotification.php";

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        setTitle("Notification");
        ButterKnife.bind(this);

        recyclerView = (RecyclerView) findViewById(R.id.notification_recycler);
        toRemove = (ImageButton) findViewById(R.id.btnToRemoveNotification);

        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);

        fetchNotifications();

        setRecycler();
    }

    private void fetchNotifications() {
        ArrayList<String> keys = new ArrayList<>(), vals = new ArrayList<>();

        keys.add("email");
        vals.add(User.Email);

        Database db = new Database();
        db.retrieve(new RetrievalData(keys, vals, FETCHNOTIFFILE, parent), true, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String response) {
                try {
                    ArrayList<Notification> notifs = new ArrayList<>();

                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray result = jsonObject.getJSONArray("result");

                    if (result.length() != 0) {
                        //System.out.println(result + " X " + result.length());
                        for (int i = 0; i < result.length(); i++) {
                            try {
                                JSONObject obj = result.getJSONObject(i);
                                Notification notif = new Notification(obj.getString("ID"), obj.getString("Type"), obj.getString("Name"));

                                notifs.add(notif);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    changeNotifs(notifs);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void changeNotifs(ArrayList<Notification> notifs) {
        notifList.clear();
        notifList.addAll(notifs);
        if(notifList.size()==0){
            Toast.makeText(parent, "You have no new Notifications", Toast.LENGTH_SHORT).show();
        }
        adapter.notifyDataSetChanged();
    }

    private void setRecycler() {
        notifList = new ArrayList<>();
        adapter = new NotificationListAdapter(this,notifList,this);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCrossClick(final Notification notif) {
        ArrayList<String> keys = new ArrayList<>(), vals = new ArrayList<>();

        keys.add("id");
        vals.add(notif.id);

        Database db = new Database();
        db.update(new RetrievalData(keys, vals, REMOVENOTIFFILE, this), true, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
                if(result.equals("true")){
                    Toast.makeText(parent, "Notification has been deleted", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(parent, NotificationActivity.class);
                    parent.startActivity(intent);
                    parent.finish();
                }
                else{
                    Toast.makeText(parent, "Sorry, something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
