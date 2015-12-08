package com.msx7.josn.tvServer;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.msx7.josn.tvServer.mima.client.MainMinaClient;
import com.msx7.josn.tvServer.pack.MessageHandlerLib;
import com.msx7.josn.tvServer.pack.message.Message;
import com.msx7.josn.tvServer.pack.message.MessageBody;
import com.msx7.josn.tvServer.pack.message.MessageHead;
import com.msx7.josn.tvServer.pack.message.impl.MessageHeadImpl;
import com.msx7.josn.tvServer.pack.message.impl.MessageImpl;
import com.msx7.josn.tvServer.pack.message.impl.body.PackBody;
import com.msx7.josn.tvServer.pack.message.impl.body.StringMessageBody;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.handler.demux.MessageHandler;

/**
 * Created by xiaowei on 2015/12/8.
 */
public class ClientActivity extends Activity {

    Button connect;
    Button send;
    TextView ip;
    TextView content;
    TextView logView;
    MainMinaClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_client);
        connect = (Button) findViewById(R.id.connect);
        send = (Button) findViewById(R.id.button);
        ip = (TextView) findViewById(R.id.ip);
        logView = (TextView) findViewById(R.id.textView);
        content = (TextView) findViewById(R.id.editText);

        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        client = new MainMinaClient(ip.getText().toString());
                    }
                }.start();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageBody body = new StringMessageBody(content.getText().toString());
                client.session.write(new MessageImpl(new MessageHeadImpl("kehuduan".getBytes(), body.getBodyLength() + MessageHead.HEAD_LENGTH, 0X02, 0X01), body));
            }
        });

        MessageHandlerLib.getInstance().addHandler("", new MessageHandler() {
            @Override
            public void handleMessage(final IoSession ioSession, final Object o) throws Exception {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        StringBuffer buffer = new StringBuffer();
                        buffer.append(logView.getText().toString());
                        buffer.append("/r/n");
                        buffer.append("LocalAddress" + ioSession.getLocalAddress() + "/r/n");
                        buffer.append("getRemoteAddress" + ioSession.getRemoteAddress() + "/r/n");
                        Message message = (Message) o;
                        PackBody packBody = (PackBody) message.getMessageBody();
                        buffer.append("content:/r/n" + new String(packBody.body) + "/r/n");
                    }
                });
            }
        });
    }

}
