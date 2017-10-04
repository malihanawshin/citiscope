package com.example.imm.citi.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.imm.citi.R;
import com.example.imm.citi.technicalClasses.Database;
import com.example.imm.citi.technicalClasses.RetrievalData;
import com.example.imm.citi.technicalClasses.User;
import com.example.imm.citi.technicalClasses.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.ButterKnife;

public class HomeActivity extends BottomBarActivity implements ServiceListAdapter.ClickCallback, View.OnClickListener{

    ServiceListAdapter adapter;
    ArrayList<ServiceFeature> items = new ArrayList<>();

    private Spinner spnCity;
    private RecyclerView serviceview;
    private SwipeRefreshLayout homeRefreshLayout;
    int size;

    private final String DISTRICTFILE = "districts.php";

    Activity parent = this;
    ArrayList<String> serv;
    private String district;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle("Home");
        ButterKnife.bind(this);

        getSession();

        spnCity = (Spinner) findViewById(R.id.spn_city);
        serviceview = (RecyclerView) findViewById(R.id.services);
        homeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.homeRefreshLayout);

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

        fillSpinner();
        getOverflowMenu();
    }

    private void setRadioGroup() {
        RadioGroup sorters = (RadioGroup)findViewById(R.id.rdGrpSorter);
        sorters.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                RadioButton checkedRb = (RadioButton)group.findViewById(checkedId);
                boolean isChecked = checkedRb.isChecked();
                if (isChecked)
                {
                    if(checkedRb.getText().toString().equals("Sort By Name"))
                        sortServicesByName(items);
                    else
                        sortServicesByPopularity(items);
                }
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
        intent.putExtra("chosenService", district);
        startActivity(intent);
    }


    private void fillSpinner() {
        ArrayList<String> keys = new ArrayList<>(), vals = new ArrayList<>();

        Database db = new Database();
        db.retrieve(new RetrievalData(keys, vals, DISTRICTFILE, parent), true, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String response) {
                try {
                    ArrayAdapter<String> spnAdapter;
                    ArrayList<String> districts = new ArrayList<String>();

                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray result = jsonObject.getJSONArray("result");

                    if(result.length()!=0){
                        System.out.println(result + " X " + result.length());
                        for(int i=0; i<result.length(); i++) {
                            try {
                                JSONObject res = result.getJSONObject(i);
                                districts.add(res.getString("District"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        spnAdapter = new ArrayAdapter<String>(parent, android.R.layout.simple_spinner_item, districts);
                        spnCity.setAdapter(spnAdapter);
                    }

                    setSpinnerListener();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setSpinnerListener() {
        spnCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long l) {
                String selected = parent.getItemAtPosition(pos).toString();
                district = selected;
                getServices(selected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    private void getServices(String city) {
        serv = new ArrayList<>();

        ArrayList<String> vals = new ArrayList<>();
        vals.add(city);

        ArrayList<String> keys = new ArrayList<>();
        keys.add("district");

        String file = "serviceNames.php";

        Database db = new Database();
        db.retrieve(new RetrievalData(keys, vals, file, parent), true, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray result = jsonObject.getJSONArray("result");
                    items.clear();

                    if(result.length()!=0){
                        System.out.println(result + " X " + result.length());
                        for(int i=0; i<result.length(); i++) {
                            try {
                                JSONObject serviceInfo = result.getJSONObject(i);
                                serv.add(serviceInfo.getString("ServiceName"));
                                items.add(new ServiceFeature(serviceInfo.getString("ServiceName"), serviceInfo.getString("Logo"), serviceInfo.getInt("Popularity")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    System.out.println(items);
                    adapter.notifyDataSetChanged();

                    setRadioGroup();
                    setRefreshLayout();


                    //createButtons();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setRefreshLayout() {
        homeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getServices(district);
                onItemsLoadComplete();
            }
        });
    }

    void onItemsLoadComplete() {
        RadioButton rdName = (RadioButton) findViewById(R.id.radio1);
        rdName.setChecked(true);
        homeRefreshLayout.setRefreshing(false);
    }


    private void getSession() {
        SharedPreferences shpr=this.getSharedPreferences("Authentication", Activity.MODE_PRIVATE);
        Boolean loggedIn = shpr.getBoolean("loggedIn", false);

        if(loggedIn){
            Toast.makeText(parent,"Logged In",Toast.LENGTH_LONG).show();

            String email = shpr.getString("email", "");
            String name = shpr.getString("name", "");
            String phone = shpr.getString("phone", "");
            String bio = shpr.getString("bio", "");
            Boolean admin = shpr.getBoolean("admin", false);

            User.setAttributes(email, name, phone, bio, this);
        }
    }



    private void sortServicesByPopularity(ArrayList<ServiceFeature> sfArr){
        Collections.sort(sfArr, ServiceFeature.servicePopularityComparator);
        adapter.notifyDataSetChanged();
    }

    private void sortServicesByName(ArrayList<ServiceFeature> sfArr){
        Collections.sort(sfArr, ServiceFeature.serviceNameComparator);
        adapter.notifyDataSetChanged();
    }



}