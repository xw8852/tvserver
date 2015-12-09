package com.msx7.josn.tvServer;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.TextView;

import com.msx7.josn.tvServer.mima.MinaConstants;
import com.msx7.josn.tvServer.mima.common.util.IPUtil;
import com.msx7.josn.tvServer.mima.server.MainMinaServer;
import com.msx7.josn.tvServer.mima.server.handler.ServerMinaHandler;
import com.msx7.josn.tvServer.pack.MessageHandlerLib;
import com.msx7.josn.tvServer.pack.message.Message;
import com.msx7.josn.tvServer.pack.message.MessageHandler;
import com.msx7.josn.tvServer.pack.message.impl.body.PackBody;

import org.apache.mina.core.session.IoSession;

import java.util.Arrays;

/**
 * Created by xiaowei on 2015/12/8.
 */
public class ServerActivity extends Activity {
    TextView textView;
 Thread thread;
    ScrollView scrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_server);
        textView = (TextView) findViewById(R.id.content);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        textView.setText("本机局域网IP:" + IPUtil.getIP(this));
        MinaConstants.MINA_SERVER_IP = IPUtil.getIP(this);

        ServerMinaHandler.getInstances().setLogHandler(new ServerMinaHandler.ILogHandler() {
            @Override
            public void handLog(final String string) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        StringBuffer buffer = new StringBuffer();
                        buffer.append(textView.getText().toString());
                        buffer.append("\r\n");
                        buffer.append(string);
                        textView.setText(buffer);
                        scrollDown();
                    }
                });
            }
        });
     thread=   new Thread() {
            @Override
            public void run() {
                super.run();
                MainMinaServer.getInstances();
            }
        };
        thread.start();
        MessageHandlerLib.getInstance().addHandler("", new MessageHandler() {
            @Override
            public void handleMessage(final IoSession ioSession, final Message message) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        StringBuffer buffer = new StringBuffer();
                        buffer.append(textView.getText().toString());
                        buffer.append("\r\n");
                        buffer.append("LocalAddress" + ioSession.getLocalAddress() + "\r\n");
                        buffer.append("getRemoteAddress" + ioSession.getRemoteAddress() + "+\r\n");
                        PackBody packBody = (PackBody) message.getMessageBody();
                        byte[] bytes = new byte[packBody.getBodyLength() - 1];
                        Log.d("way", Arrays.toString(packBody.encode()));
                        System.arraycopy(packBody.encode(), 0, bytes, 0, bytes.length);
                        Log.d("way", Arrays.toString(bytes));
                        buffer.append("content:\r\n" + new String(bytes) + "\r\n");
                        textView.setText(buffer.toString());
                        scrollDown();
                    }
                });

            }
        });
    }
Handler handler=new Handler();
    public void scrollDown(){
        handler.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        MainMinaServer.getInstances().close();
        thread.interrupt();
    }


}
