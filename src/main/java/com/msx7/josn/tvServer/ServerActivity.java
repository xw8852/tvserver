package com.msx7.josn.tvServer;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.msx7.josn.tvServer.mima.server.MainMinaServer;
import com.msx7.josn.tvServer.pack.MessageHandlerLib;
import com.msx7.josn.tvServer.pack.message.Message;
import com.msx7.josn.tvServer.pack.message.impl.body.PackBody;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.handler.demux.MessageHandler;

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
        MessageHandlerLib.getInstance().addHandler("", new MessageHandler() {
            @Override
            public void handleMessage(final IoSession ioSession, final Object o) throws Exception {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        StringBuffer buffer = new StringBuffer();
                        buffer.append(textView.getText().toString());
                        buffer.append("\r\n");
                        buffer.append("LocalAddress" + ioSession.getLocalAddress() + "\r\n");
                        buffer.append("getRemoteAddress" + ioSession.getRemoteAddress() + "+\r\n");
                        Message message = (Message) o;
                        PackBody packBody = (PackBody) message.getMessageBody();
                        buffer.append("content:\r\n" + new String(packBody.body) + "\r\n");
                        textView.setText(buffer.toString());
                    }
                });

            }
        });
    }
}
