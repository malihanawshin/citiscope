package com.example.imm.mypractice;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.imm.mypractice.R;

import java.util.ArrayList;

import butterknife.ButterKnife;

public class HomeActivity extends BottomBarActivity implements ServiceListAdapter.ClickCallback, View.OnClickListener{

    ServiceListAdapter adapter;
    ArrayList<ServiceFeature> items = new ArrayList<>();

    private Spinner cityname;
    private RecyclerView serviceview;
    private LinearLayout serviceView;
    int size;

    public void addServices(){
        ServiceFeature s1 = new ServiceFeature("Tuition",R.drawable.desk);
        ServiceFeature s2 = new ServiceFeature("Apartment",R.drawable.flats);
        ServiceFeature s3 = new ServiceFeature("Blood Donation",R.drawable.blood);
        ServiceFeature s4 = new ServiceFeature("Doctor",R.drawable.greendoctor);

        items.add(s1);
        items.add(s2);
        items.add(s3);
        items.add(s4);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            switch(v.getId()){
                case R.id.spn_city:
                    // TODO dynamic changes of city available
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle("Home");
        ButterKnife.bind(this);
        addServices();

        cityname = (Spinner) findViewById(R.id.spn_city);
        serviceview = (RecyclerView) findViewById(R.id.services);
        serviceView = (LinearLayout) findViewById(R.id.serviceGridView);

        GridLayoutManager manager = new GridLayoutManager(this,2);
        serviceview.setLayoutManager(manager);
        ViewTreeObserver vto = serviceview.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                serviceview.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                size = serviceview.getMeasuredWidth();
                setRecycler();
            }
        });
    }

    private void setRecycler() {
        adapter = new ServiceListAdapter(this, items, this, size / 2);
        serviceview.setAdapter(adapter);
        serviceview.addItemDecoration(new GridSpacingItemDecoration(2, 3, true));
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
    public void onClick(View v) {
    }


    @Override
    public void onItemClick(ServiceFeature s) {
    Intent intent = new Intent(this, FilterActivity.class);
    intent.putExtra("servicename",s.getServiceName());
    startActivity(intent);
    }

}