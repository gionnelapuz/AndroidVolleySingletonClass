package com.example.gin.androidvolleysingletonclass;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private Timer timer;
    private TimerTask timerTask;
    private Handler handler = new Handler();

    public static final String URL_GETDATA = "YOUR_URL";
    private StringRequest stringRequest;

    private Button btnGetData, btnStopData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGetData = (Button) findViewById(R.id.btnGetData);
        btnStopData = (Button) findViewById(R.id.btnStopData);

        btnGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer();
            }
        });
        btnStopData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stoptimertask();
            }
        });
    }

    public void startTimer() {
        timer = new Timer();
        initializeTimerTask();
        timer.schedule(timerTask, 0, 10000);
    }
    public void stoptimertask() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        getData();
                    }
                });
            }
        };
    }


    private void getData() {

        stringRequest = new StringRequest(Request.Method.GET, URL_GETDATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(MainActivity.this, "SUCCESS", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof NetworkError) {
                            Toast.makeText(MainActivity.this, "NETWORK ERROR", Toast.LENGTH_SHORT).show();
                            stoptimertask();
                        } else if (error instanceof TimeoutError) {
                            Toast.makeText(MainActivity.this, "NETWORK TIMEOUT", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof NoConnectionError) {
                            Toast.makeText(MainActivity.this, "NO CONNECTION", Toast.LENGTH_SHORT).show();
                            stoptimertask();
                        }
                    }
                });
        VolleySingletonClass.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        VolleySingletonClass.getInstance(getApplicationContext()).cancelPendingRequests(stringRequest);
    }

}
