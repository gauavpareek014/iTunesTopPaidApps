package com.uncc.inclass07test;
/*
* InClass07
* MainActivity.java
* Gaurav Pareek
* Srujan Pothina
* Josiah Laivins
* */
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.stetho.Stetho;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    DatabaseDataManager dm;
    ListView listView;
    ArrayList<AppData> sortedAppList = new ArrayList<AppData>();;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    Switch aSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Stetho.initializeWithDefaults(this);

        final TextView noFilteredApp = (TextView)findViewById(R.id.noFiltered);
        listView = (ListView) findViewById(R.id.favListview);
        dm = new DatabaseDataManager(MainActivity.this);
        String url = "https://itunes.apple.com/us/rss/toppaidapplications/limit=25/json";
        aSwitch = (Switch) findViewById(R.id.switch1);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        ImageView refresh = (ImageView)findViewById(R.id.refresh);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        List<Filter> filters = dm.getAllFilters();

        mRecyclerView = (RecyclerView) findViewById(R.id.myrecyclerview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);

        if(filters.size()>0) {
            mAdapter = new BottomAppAdapter((ArrayList<Filter>) filters, MainActivity.this);
            mRecyclerView.setAdapter(mAdapter);
            noFilteredApp.setVisibility(View.INVISIBLE);
        }else {
            noFilteredApp.setVisibility(View.VISIBLE);
        }

        if(isConnected()) {
            new GetAppAsynTask(new GetAppAsynTask.AsyncResponse() {
                @Override
                public void processFinish(ArrayList<AppData> appList) {
                    if(appList!=null && appList.size()>0) {
                        List<Filter> filtersList = dm.getAllFilters();
                        sortedAppList = (ArrayList<AppData>) appList.clone();
                        if (filtersList.size() > 0) {
                            for (Filter filter : filtersList) {
                                for (AppData appData : appList) {
                                    if (filter.getName().equalsIgnoreCase(appData.getAppName())) {
                                        sortedAppList.remove(appData);
                                    }
                                }
                            }
                        }
                        Collections.sort(sortedAppList, new Comparator<AppData>() {
                            @Override
                            public int compare(AppData o1, AppData o2) {
                                return o1.getAppPrice().compareTo(o2.getAppPrice());
                            }

                        });
                        progressDialog.hide();
                        TopAppAdapter arrayAdapter = new TopAppAdapter(MainActivity.this, R.layout.email_layout, sortedAppList);
                        listView.setAdapter(arrayAdapter);
                    }else{
                        Toast.makeText(MainActivity.this, "No data received from API", Toast.LENGTH_SHORT).show();
                    }
                }
            }).execute(url, "search");
        }else {
            progressDialog.hide();
            Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton cb, boolean on) {
                if (on) {
                    Collections.sort(sortedAppList, new Comparator<AppData>() {
                        @Override
                        public int compare(AppData o1, AppData o2) {
                            return o1.getAppPrice().compareTo(o2.getAppPrice());
                        }
                    });

                    TopAppAdapter arrayAdapter = new TopAppAdapter(MainActivity.this, R.layout.email_layout, sortedAppList);
                    listView.setAdapter(arrayAdapter);
                    aSwitch.setText("Ascending");
                } else {
                    Collections.sort(sortedAppList, new Comparator<AppData>() {
                        @Override
                        public int compare(AppData o1, AppData o2) {
                            return (-1) * o1.getAppPrice().compareTo(o2.getAppPrice());
                        }
                    });
                    aSwitch.setText("Descending");
                    TopAppAdapter arrayAdapter = new TopAppAdapter(MainActivity.this, R.layout.email_layout, sortedAppList);
                    listView.setAdapter(arrayAdapter);
                }
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AppData appData = sortedAppList.get(position);
                sortedAppList.remove(position);
                dm.saveFilter(new Filter(appData.getAppName(), Double.parseDouble(appData.getAppPrice()), appData.getAppThumb(), appData.getThumb_url()));
                TopAppAdapter arrayAdapter = new TopAppAdapter(MainActivity.this, R.layout.email_layout, sortedAppList);
                listView.setAdapter(arrayAdapter);
                List<Filter> filtersList = dm.getAllFilters();
                if (filtersList.size() > 0) {
                    mAdapter = new BottomAppAdapter((ArrayList<Filter>) filtersList, MainActivity.this);
                    mRecyclerView.setAdapter(mAdapter);
                    noFilteredApp.setVisibility(View.INVISIBLE);
                } else {
                    noFilteredApp.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = MainActivity.this.getIntent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                MainActivity.this.finish();
                MainActivity.this.overridePendingTransition(0, 0);
                MainActivity.this.startActivity(intent);
                MainActivity.this.overridePendingTransition(0, 0);
            }
        });
    }
    @Override
    protected void onDestroy() {
        dm.close();
        super.onDestroy();
    }
    public void deleteBottomData(View view) {
        int position = Integer.parseInt(view.getTag() + "");
        dm = new DatabaseDataManager(MainActivity.this);
        List<Filter> filters = dm.getAllFilters();
        Filter filter = filters.get(position);
        dm.deleteFilter(filter);
        List<Filter> filtersList = dm.getAllFilters();
        TextView noFilteredApp = (TextView)findViewById(R.id.noFiltered);
        mAdapter = new BottomAppAdapter((ArrayList<Filter>) filtersList, MainActivity.this);
        mRecyclerView.setAdapter(mAdapter);
        if(filtersList.size()>0) {
            noFilteredApp.setVisibility(View.GONE);
        }else{
            noFilteredApp.setVisibility(View.VISIBLE);
        }

        String dollarImage = "";
        if (filter.price >= 0 && filter.price <= 1.99) {
            dollarImage = R.drawable.price_low + "";
        } else if (filter.price >= 2.00 && filter.price <= 5.99) {
            dollarImage = R.drawable.price_medium + "";
        } else if (filter.price >= 6.00) {
            dollarImage = R.drawable.price_high + "";
        }
        AppData appData = new AppData(0, filter.getName(), filter.getThumb_url(), filter.getPrice().toString(), filter.getThumb_url_large(), dollarImage);
        sortedAppList.add(appData);
        if (aSwitch.isChecked()) {
            Collections.sort(sortedAppList, new Comparator<AppData>() {
                @Override
                public int compare(AppData o1, AppData o2) {
                    return o1.getAppPrice().compareTo(o2.getAppPrice());
                }
            });
        } else {
            Collections.sort(sortedAppList, new Comparator<AppData>() {
                @Override
                public int compare(AppData o1, AppData o2) {
                    return (-1) * o1.getAppPrice().compareTo(o2.getAppPrice());
                }
            });
        }
        TopAppAdapter arrayAdapter = new TopAppAdapter(MainActivity.this, R.layout.email_layout, sortedAppList);
        listView = (ListView) findViewById(R.id.favListview);
        listView.setAdapter(arrayAdapter);
    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }
}
