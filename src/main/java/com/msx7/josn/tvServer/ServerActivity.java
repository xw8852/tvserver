package com.msx7.josn.tvServer;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.TextView;

import com.msx7.josn.tvconnection.action.CameraBody;
import com.msx7.josn.tvconnection.action.CameraGetBody;
import com.msx7.josn.tvconnection.action.EmptyBody;
import com.msx7.josn.tvconnection.action.TouchBody;
import com.msx7.josn.tvconnection.action.TouchGetBody;
import com.msx7.josn.tvconnection.action.VolBody;
import com.msx7.josn.tvconnection.mima.MinaConstants;
import com.msx7.josn.tvconnection.mima.common.util.IPUtil;
import com.msx7.josn.tvconnection.mima.common.util.MinaUtil;
import com.msx7.josn.tvconnection.mima.server.MainMinaServer;
import com.msx7.josn.tvconnection.mima.server.handler.ServerMinaHandler;
import com.msx7.josn.tvconnection.pack.Code;
import com.msx7.josn.tvconnection.pack.MessageHandlerLib;
import com.msx7.josn.tvconnection.pack.message.Message;
import com.msx7.josn.tvconnection.pack.message.MessageHandler;
import com.msx7.josn.tvconnection.pack.message.MessageHead;
import com.msx7.josn.tvconnection.pack.message.impl.MessageHeadImpl;
import com.msx7.josn.tvconnection.pack.message.impl.MessageImpl;
import com.msx7.josn.tvconnection.pack.message.impl.body.PackBody;

import org.apache.mina.core.session.IoSession;

import java.util.Arrays;

/**
 * Created by xiaowei on 2015/12/8.
 */
public class ServerActivity extends Activity {

    public static final String ACTION_BROADCAST = "com.msx7.json.tvServer.RECIVER_TCP_MSG";
    TextView textView;
    ScrollView scrollView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_server);
        textView = (TextView) findViewById(R.id.content);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        textView.setText("本机局域网IP:" + IPUtil.getIP(this));
        MinaConstants.MINA_SERVER_IP = IPUtil.getIP(this);
        registerReceiver(connectService, new IntentFilter(ACTION_BROADCAST));
        startService(new Intent(this, ConnectionService.class));
    }

    Handler handler = new Handler();

    public void scrollDown() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    BroadcastReceiver connectService = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.hasExtra("MSG")) {
                String str = intent.getStringExtra("MSG");
                StringBuffer buffer = new StringBuffer(textView.getText().toString());
                buffer.append(str);
                textView.setText(buffer);
                scrollDown();
            }
        }
    };


}
