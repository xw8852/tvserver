package com.msx7.josn.tvServer;

import android.app.Activity;
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

        /**
         * 注册消息处理器
         */
        Handler handler = new Handler();
        MessageHandlerLib.getInstance().addHandler(String.valueOf(Code.ACTION_SYSTEM_HOME), new DefaultMessageHandler(homeHandler, handler));
        MessageHandlerLib.getInstance().addHandler(String.valueOf(Code.ACTION_SYSTEM_BACK), new DefaultMessageHandler(backHandler, handler));
        MessageHandlerLib.getInstance().addHandler(String.valueOf(Code.ACTION_VOL_GET), new DefaultMessageHandler(volHandler, handler));
        MessageHandlerLib.getInstance().addHandler(String.valueOf(Code.ACTION_VOL_SET), new DefaultMessageHandler(volHandler, handler));
        MessageHandlerLib.getInstance().addHandler(String.valueOf(Code.ACTION_CAMERA_ANGLE_ROTATE), new DefaultMessageHandler(cameraHandler, handler));
        MessageHandlerLib.getInstance().addHandler(String.valueOf(Code.ACTION_CAMERA_ANGLE_GET), new DefaultMessageHandler(cameraHandler, handler));
        MessageHandlerLib.getInstance().addHandler(String.valueOf(Code.ACTION_MOTION_GET), new DefaultMessageHandler(touchHandler, handler));
        MessageHandlerLib.getInstance().addHandler(String.valueOf(Code.ACTION_MOTION_SET), new DefaultMessageHandler(touchHandler, handler));


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
        thread = new Thread() {
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

                    }
                });
            }
        });
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
    protected void onDestroy() {
        super.onDestroy();
        MainMinaServer.getInstances().close();
        thread.interrupt();
    }

    MessageHandler homeHandler = new MessageHandler() {
        @Override
        public void handleMessage(IoSession ioSession, Message message) {
            StringBuffer buffer = new StringBuffer();
            buffer.append(textView.getText().toString());
            buffer.append("\r\n");
            buffer.append("LocalAddress" + ioSession.getLocalAddress() + "\r\n");
            buffer.append("getRemoteAddress" + ioSession.getRemoteAddress() + "+\r\n");
            buffer.append("content:\r\n 收到客户端的按下Home键的消息\r\n");
            textView.setText(buffer.toString());
            scrollDown();
        }
    };
    MessageHandler backHandler = new MessageHandler() {
        @Override
        public void handleMessage(IoSession ioSession, Message msg) {
            StringBuffer buffer = new StringBuffer();
            buffer.append(textView.getText().toString());
            buffer.append("\r\n");
            buffer.append("LocalAddress" + ioSession.getLocalAddress() + "\r\n");
            buffer.append("getRemoteAddress" + ioSession.getRemoteAddress() + "+\r\n");
            buffer.append("content:\r\n 收到客户端的按下Back键的消息\r\n");
            textView.setText(buffer.toString());
            scrollDown();
        }
    };
    boolean isMute;//是否静音
    boolean isSignle;//是否单声道
    int vol = 50;//双声道音量
    int volL = 50;//单声道左声道音量
    int volR = 50;//单声道右声道音量
    MessageHandler volHandler = new MessageHandler() {
        @Override
        public void handleMessage(IoSession ioSession, Message msg) {
            if (msg.getMessageHead().getActionCode() == Code.ACTION_VOL_GET) {
                StringBuffer buffer = new StringBuffer();
                buffer.append(textView.getText().toString());
                buffer.append("\r\n");
                buffer.append("LocalAddress" + ioSession.getLocalAddress() + "\r\n");
                buffer.append("getRemoteAddress" + ioSession.getRemoteAddress() + "+\r\n");
                buffer.append("content:\r\n 收到客户端的需要音量的消息\r\n");
                textView.setText(buffer.toString());
                sendVolMessage(ioSession, Code.ACTION_VOL_GET);
            } else if (msg.getMessageHead().getActionCode() == Code.ACTION_VOL_SET) {
                StringBuffer buffer = new StringBuffer();
                buffer.append(textView.getText().toString());
                buffer.append("\r\n");
                buffer.append("LocalAddress" + ioSession.getLocalAddress() + "\r\n");
                buffer.append("getRemoteAddress" + ioSession.getRemoteAddress() + "+\r\n");
                buffer.append("content:\r\n 收到客户端的设置音量的消息\r\n");

                VolBody body = new VolBody();
                body.decoder(msg.getMessageBody().encode());

                buffer.append(body.volL + "," + body.volR + "\r\n");
                textView.setText(buffer.toString());
                isMute = body.isMute;
                isSignle = body.isSingle;
                if (isSignle) {
                    if (body.volL > -1) volL = body.volL;
                    if (body.volR > -1) volR = body.volR;
                } else if (body.volL > -1) vol = body.volL;

                sendVolMessage(ioSession, Code.ACTION_VOL_GET);
            }
        }
    };

    void sendVolMessage(IoSession ioSession, int code) {
        VolBody body = new VolBody(isMute, isSignle);
        if (isSignle) {
            body.volL = volL;
            body.volR = volR;
        } else {
            body.volL = vol;
            body.volR = -1;
        }
        MessageHead head = new MessageHeadImpl("".getBytes(), MessageHead.HEAD_LENGTH + VolBody.LENGTH, code, 1);
        Message message = new MessageImpl(head, body);
        ioSession.write(message);
    }

    int horizonal;
    int vertical;
    MessageHandler cameraHandler = new MessageHandler() {
        @Override
        public void handleMessage(IoSession var1, Message msg) {
            if (msg.getMessageHead().getActionCode() == Code.ACTION_CAMERA_ANGLE_ROTATE) {
                CameraBody cameraBody = new CameraBody();
                cameraBody.decoder(msg.getMessageBody().encode());
                StringBuffer buffer = new StringBuffer();
                buffer.append(textView.getText().toString());
                buffer.append("\r\n");
                buffer.append("LocalAddress" + var1.getLocalAddress() + "\r\n");
                buffer.append("getRemoteAddress" + var1.getRemoteAddress() + "+\r\n");
                buffer.append("content:\r\n 收到客户端的旋转摄像头的消息\r\n");
                buffer.append("水平:" + cameraBody.horizontal + ",垂直:" + cameraBody.vertical + "\r\n");
                horizonal = cameraBody.horizontal;
                vertical = cameraBody.vertical;
                textView.setText(buffer.toString());
            } else if (msg.getMessageHead().getActionCode() == Code.ACTION_CAMERA_ANGLE_GET) {
                StringBuffer buffer = new StringBuffer();
                buffer.append(textView.getText().toString());
                buffer.append("\r\n");
                buffer.append("LocalAddress" + var1.getLocalAddress() + "\r\n");
                buffer.append("getRemoteAddress" + var1.getRemoteAddress() + "+\r\n");
                buffer.append("content:\r\n 收到客户端的需要获取摄像头角度的消息\r\n");
                buffer.append("水平:" + horizonal + ",垂直:" + vertical + "\r\n");
                textView.setText(buffer.toString());
                CameraGetBody body = new CameraGetBody(horizonal, vertical, -90, -90, 90, 90);
                MessageHead head = new MessageHeadImpl("".getBytes(), MessageHead.HEAD_LENGTH + CameraGetBody.LENGTH, Code.ACTION_CAMERA_ANGLE_GET, 1);
                Message message = new MessageImpl(head, body);
                var1.write(message);
            }
            scrollDown();
        }
    };


    MessageHandler touchHandler = new MessageHandler() {
        @Override
        public void handleMessage(IoSession ioSession, Message msg) {
            if (msg.getMessageHead().getActionCode() == Code.ACTION_MOTION_GET) {
                StringBuffer buffer = new StringBuffer();
                buffer.append(textView.getText().toString());
                buffer.append("\r\n");
                buffer.append("LocalAddress" + ioSession.getLocalAddress() + "\r\n");
                buffer.append("getRemoteAddress" + ioSession.getRemoteAddress() + "+\r\n");
                buffer.append("content:\r\n 收到客户端的需要屏幕宽高的消息\r\n");

                TouchGetBody body = new TouchGetBody();
                body.width = getResources().getDisplayMetrics().widthPixels;
                body.height = getResources().getDisplayMetrics().heightPixels;
                buffer.append("宽度:" + body.width + ",高度:" + body.height + "\r\n");
                textView.setText(buffer.toString());
                MessageHead head = new MessageHeadImpl("".getBytes(), MessageHead.HEAD_LENGTH + body.LENGTH, Code.ACTION_MOTION_GET, 1);
                Message message = new MessageImpl(head, body);
                ioSession.write(message);
                scrollDown();
                return;
            }
            TouchBody body = new TouchBody();
            body.decoder(msg.getMessageBody().encode());
            StringBuffer buffer = new StringBuffer();
            buffer.append(textView.getText().toString());
            buffer.append("\r\n");
            buffer.append("LocalAddress" + ioSession.getLocalAddress() + "\r\n");
            buffer.append("getRemoteAddress" + ioSession.getRemoteAddress() + "+\r\n");
            buffer.append("content:\r\n 收到客户端的屏幕触摸的消息\r\n");
            for (int i = 0; i < body.infos.length; i++) {
                TouchBody.TouchInfo info=body.infos[i];
                if(info.action==1){
                    buffer.append("DOWN  ");
                }else if(info.action==2){
                    buffer.append("MOVE  ");
                }else if(info.action==3){
                    buffer.append("UP  ");
                }
                buffer.append("x:"+info.x+",y:"+info.y+"\r\n");
            }
            textView.setText(buffer.toString());
            scrollDown();
        }
    };
}
