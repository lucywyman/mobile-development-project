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
import android.os.AsyncTask;


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

    public void hitServer(View v) {
        try {
            final String username = UiUtil.readText(this, R.id.txtUsername);
            if (username.length() == 0)
                throw new IllegalArgumentException("Please enter a username.");
            final String password = UiUtil.readText(this, R.id.txtPassword);
            if (password.length() == 0)
                throw new IllegalArgumentException("Please enter a password.");

            final boolean reg = (v.getId() == R.id.btnRegister);
            new AsyncTask<Void, Void, Long>() {
                protected void onPreExecute() {
                    UiUtil.writeText(MainActivity.this, R.id.txtStatus, "Hold on a sec...");
                    UiUtil.enableView(MainActivity.this, R.id.btnRegister, false);
                    UiUtil.enableView(MainActivity.this, R.id.btnLogin, false);
                }

                @Override
                protected Long doInBackground(Void... params) {
                    try {
                        return Server.init(username, password, reg);
                    } catch (Exception e) {
                        UiUtil.toastOnUiThread(MainActivity.this, "Error: " + e.getMessage());
                        return 0L;
                    }
                }

                protected void onPostExecute(Long sessionId) {
                    if (sessionId > 0) {
                        UiUtil.writeText(MainActivity.this, R.id.txtStatus, "");
                        startLimerickEditor(username, password, sessionId);
                    } else {
                        UiUtil.writeText(MainActivity.this, R.id.txtStatus, "Please try again...");
                    }
                    UiUtil.enableView(MainActivity.this, R.id.btnRegister, true);
                    UiUtil.enableView(MainActivity.this, R.id.btnLogin, true);
                }
            }.execute();
        } catch (IllegalArgumentException e) {
            UiUtil.toastOnUiThread(this, e.getMessage());
        }
    }
    private void startLimerickEditor(String username, String password, long sessionId) {
        Intent intent = new Intent(this, LimerickActivity.class);
//        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("username", username);
        intent.putExtra("password", password);
        intent.putExtra("session", sessionId);
        startActivity(intent);
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
                return true;
            case R.id.messages:
                return true;
        }
        return (super.onOptionsItemSelected(item));
    }

}
