package com.msx7.josn.tvServer;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.msx7.josn.tvServer.mima.client.MainMinaClient;
import com.msx7.josn.tvServer.mima.client.handler.MinaClientHandler;
import com.msx7.josn.tvServer.mima.common.util.IPUtil;
import com.msx7.josn.tvServer.mima.common.util.MinaUtil;
import com.msx7.josn.tvServer.pack.MessageHandlerLib;
import com.msx7.josn.tvServer.pack.message.Message;
import com.msx7.josn.tvServer.pack.message.MessageBody;
import com.msx7.josn.tvServer.pack.message.MessageHandler;
import com.msx7.josn.tvServer.pack.message.MessageHead;
import com.msx7.josn.tvServer.pack.message.impl.MessageHeadImpl;
import com.msx7.josn.tvServer.pack.message.impl.MessageImpl;
import com.msx7.josn.tvServer.pack.message.impl.body.PackBody;
import com.msx7.josn.tvServer.pack.message.impl.body.StringMessageBody;

import org.apache.mina.core.session.IoSession;

import java.util.Arrays;
import java.util.List;

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
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_client);
        connect = (Button) findViewById(R.id.connect);
        send = (Button) findViewById(R.id.button);
        ip = (TextView) findViewById(R.id.ip);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        logView = (TextView) findViewById(R.id.textView);
        content = (TextView) findViewById(R.id.editText);
        logView.setText("本机局域网IP:" + IPUtil.getIP(this));

        MinaClientHandler.getInstances().setLogHandler(new MinaClientHandler.ILogHandler() {
            @Override
            public void handLog(final String string) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        StringBuffer buffer = new StringBuffer();
                        buffer.append(logView.getText().toString());
                        buffer.append("\r\n");
                        buffer.append(string);
                        logView.setText(buffer);
                        scrollDown();
                    }
                });
            }
        });
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        if (client != null) client.session.close(true);
                        try {
                            client = new MainMinaClient(ip.getText().toString());
                        } catch (final Exception e) {
                            e.printStackTrace();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    StringBuffer buffer = new StringBuffer();
                                    buffer.append(logView.getText().toString());
                                    buffer.append("\r\n");
                                    buffer.append(e.getClass().getName());
                                    logView.setText(buffer);
                                }
                            });
                        } finally {
                        }
                    }
                }.start();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageBody body = new StringMessageBody(content.getText().toString());
                Log.d("way", Arrays.toString(content.getText().toString().getBytes()));
                Log.d("way", Arrays.toString(body.encode()));
                MessageImpl impl = new MessageImpl(new MessageHeadImpl("kehuduan".getBytes(), body.getBodyLength() + MessageHead.HEAD_LENGTH, 0X02, 0X01), body);
                sendMsg(MinaUtil.messageClip(impl));
            }
        });

        MessageHandlerLib.getInstance().addHandler("", new MessageHandler() {
            @Override
            public void handleMessage(final IoSession ioSession, final Message message) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        StringBuffer buffer = new StringBuffer();
                        buffer.append(logView.getText().toString());
                        buffer.append("\r\n");
                        buffer.append("LocalAddress" + ioSession.getLocalAddress() + "\r\n");
                        buffer.append("getRemoteAddress" + ioSession.getRemoteAddress() + "\r\n");

                        PackBody packBody = (PackBody) message.getMessageBody();
                        buffer.append("content:\r\n" + new String(packBody.body) + "\r\n");
                        logView.setText(buffer.toString());
                        scrollDown();
                    }
                });
            }
        });
    }

    public void sendMsg(List<Message> msg) {
        if (msg == null || msg.isEmpty()) return;
        for (Message message : msg) {
            client.session.write(message);
        }
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (client != null) client.session.close(true);
    }
}
