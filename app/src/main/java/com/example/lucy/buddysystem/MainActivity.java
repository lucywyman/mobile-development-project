package com.example.lucy.buddysystem;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;


public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Buddy System");
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.inflateMenu(R.menu.menu_main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.messages);
        fab.setOnClickListener(new View.OnClickListener() {
            // TODO
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // TODO
            case R.id.settings:
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivityForResult(settingsIntent, 1);
                return true;
            case R.id.contacts:
                Intent contactIntent = new Intent(this, ContactActivity.class);
                startActivityForResult(contactIntent, 2);
            case R.id.messages:
                return true;
            case R.id.bluetooth:
                Intent bluetoothIntent = new Intent(this, BluetoothActivity.class);
                startActivityForResult(bluetoothIntent, 3);
        }
        return (super.onOptionsItemSelected(item));
    }

}
