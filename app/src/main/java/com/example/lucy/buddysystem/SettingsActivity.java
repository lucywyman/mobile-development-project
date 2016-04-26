package com.example.lucy.buddysystem;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import java.util.List;

/**
 * Created by lucy on 4/25/16.
 */
public class SettingsActivity extends PreferenceActivity {
    @Override
    public void onBuildHeaders(List<Header> target)
    {
        loadHeadersFromResource(R.xml.settings_headers, target);
    }
    @Override
    protected boolean isValidFragment(String fragmentName) {
        return SettingsFragment.class.getName().equals(fragmentName);
    }
}

