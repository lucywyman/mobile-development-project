package com.example.lucy.buddysystem;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by lucy on 4/26/16.
 */

public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.settings);
    }
}
