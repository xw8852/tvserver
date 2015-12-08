package com.msx7.josn.tvServer;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.msx7.josn.tvServer.mima.server.MainMinaServer;

/**
 * Created by xiaowei on 2015/12/8.
 */
public class ServerActivity extends Activity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_server);
        textView = (TextView) findViewById(R.id.content);
        new Thread() {
            @Override
            public void run() {
                super.run();
                MainMinaServer.getInstances();
            }
        }.start();
    }
}
