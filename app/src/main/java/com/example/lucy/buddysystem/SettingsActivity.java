package com.example.lucy.buddysystem;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.widget.Toolbar;


/**
 * Created by lucy on 4/25/16.
 */
public class SettingsActivity extends PreferenceActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            //setSupportActionBar(toolbar);
            //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}

