package com.example.imm.citi.activities;

import android.os.PersistableBundle;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import com.example.imm.citi.R;
import com.example.imm.citi.technicalClasses.Notification;
import java.util.ArrayList;
import butterknife.ButterKnife;

public class NotificationActivity extends BottomBarActivity implements NotificationListAdapter.NotificationClickCallback{

    private RecyclerView recyclerView;
    ArrayList <Notification> list;
    NotificationListAdapter adapter;
    ImageButton toRemove;

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

        setRecycler();
    }

    private void setRecycler() {
        list = new ArrayList<>();
        list.add(new Notification("Your nomination Car Rent has been removed from the poll."));
        list.add(new Notification("Your nomination Hotel Booking has been added as a service."));
        adapter = new NotificationListAdapter(this,list,this);
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
    public void onCrossClick(Notification n) {
        //RelativeLayout layout = (RelativeLayout) findViewById(R.id.notification_recycler);
        //layout.setVisibility(View.GONE);
    }
}
