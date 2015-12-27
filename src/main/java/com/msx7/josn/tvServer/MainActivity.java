package com.msx7.josn.tvServer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;


import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        long time = System.nanoTime();
        long time2 = System.currentTimeMillis();
        String code = "|";
        byte[] bytes = code.getBytes();

        Log.d("way", "time " + time + "," + time2);
        Log.d("way", "time " + String.valueOf(time).hashCode() + "," + String.valueOf(time2).hashCode());
        Log.d("way", "time  " + "," + Integer.parseInt("0001") + "," + Arrays.toString(bytes) + "," + bytes.length);
//        Log.d("way", "time  " + "," +Arrays.toString( new ConnectionMessageBody().encode()));

    }

    public void goClient(View v) {
        startActivity(new Intent(this, ClientActivity.class));
    }

    public void goServer(View v) {
        startActivity(new Intent(this, ServerActivity.class));
    }
}
