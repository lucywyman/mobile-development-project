package com.example.lucy.buddysystem;

import java.text.ParseException;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.support.v7.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Date sundown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DateFormat df = new SimpleDateFormat("h:mm:ss a");
        Date sunsetdate = getSundown(this);
        String sunset = "Sunset today is at " + df.format(sunsetdate);
        TextView myTextView= (TextView) findViewById(R.id.myTextView);
        myTextView.setText(sunset);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Buddy System");
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        if (toolbar != null) {
            toolbar.inflateMenu(R.menu.menu_main);
        }

        sundown = getSundown(MainActivity.this);
        new SundownNotifier(this).execute(sundown);
    }

    private Date getSundown(Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url ="http://api.sunrise-sunset.org/json?lat=36.7201600&lng=-4.4203400&date=today";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject json = null;
                        try {
                            json = new JSONObject(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String sunset = null;
                        try {
                            sunset = json.getString("sunset");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        DateFormat df = new SimpleDateFormat("h:mm:ss a");
                        try {
                            sundown = df.parse(sunset);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
        return sundown;
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
