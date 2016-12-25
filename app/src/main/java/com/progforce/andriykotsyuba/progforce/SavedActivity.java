package com.progforce.andriykotsyuba.progforce;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import java.util.Map;

import models.SavedAdapter;

public class SavedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.dispatchMenuVisibilityChanged(false);
        actionBar.setTitle(R.string.app_shar);

        SharedPreferences sp = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        Map<String,String> allData = (Map<String, String>) sp.getAll();
        String[] savedList = allData.values().toArray(new String[0]);

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new SavedAdapter(this, savedList));
    }
}
