package com.msx7.josn.tvServer;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.msx7.josn.tvconnection.action.CameraBody;
import com.msx7.josn.tvconnection.action.CameraGetBody;
import com.msx7.josn.tvconnection.action.TouchBody;
import com.msx7.josn.tvconnection.action.TouchGetBody;
import com.msx7.josn.tvconnection.action.VolBody;
import com.msx7.josn.tvconnection.mima.server.MainMinaServer;
import com.msx7.josn.tvconnection.mima.server.handler.ServerMinaHandler;
import com.msx7.josn.tvconnection.pack.Code;
import com.msx7.josn.tvconnection.pack.MessageHandlerLib;
import com.msx7.josn.tvconnection.pack.message.Message;
import com.msx7.josn.tvconnection.pack.message.MessageHandler;
import com.msx7.josn.tvconnection.pack.message.MessageHead;
import com.msx7.josn.tvconnection.pack.message.impl.MessageHeadImpl;
import com.msx7.josn.tvconnection.pack.message.impl.MessageImpl;

import org.apache.mina.core.session.IoSession;

/**
 * Created by Josn on 2016/1/3.
 */
public class ConnectionService extends Service {

    Thread thread;

    @Override
    public void onCreate() {
        super.onCreate();

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
        MessageHandlerLib.getInstance().addHandler(String.valueOf(Code.ACTION_MICROPHONE), new DefaultMessageHandler(openAppHandler, handler));
        MessageHandlerLib.getInstance().addHandler(String.valueOf(Code.ACTION_MONITORING), new DefaultMessageHandler(openAppHandler, handler));

        ServerMinaHandler.getInstances().setLogHandler(new ServerMinaHandler.ILogHandler() {
            @Override
            public void handLog(final String string) {
                sendBroadCast(string);
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


    }


    public void sendBroadCast(String msg) {
        Intent intent = new Intent(ServerActivity.ACTION_BROADCAST);
        intent.putExtra("MSG", msg);
        sendBroadcast(intent);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    MessageHandler homeHandler = new MessageHandler() {
        @Override
        public void handleMessage(IoSession ioSession, Message message) {
            StringBuffer buffer = new StringBuffer();

            buffer.append("\r\n");
            buffer.append("LocalAddress" + ioSession.getLocalAddress() + "\r\n");
            buffer.append("getRemoteAddress" + ioSession.getRemoteAddress() + "+\r\n");
            buffer.append("content:\r\n 收到客户端的按下Home键的消息\r\n");
            sendBroadCast(buffer.toString());

        }
    };
    MessageHandler backHandler = new MessageHandler() {
        @Override
        public void handleMessage(IoSession ioSession, Message msg) {
            StringBuffer buffer = new StringBuffer();

            buffer.append("\r\n");
            buffer.append("LocalAddress" + ioSession.getLocalAddress() + "\r\n");
            buffer.append("getRemoteAddress" + ioSession.getRemoteAddress() + "+\r\n");
            buffer.append("content:\r\n 收到客户端的按下Back键的消息\r\n");
            sendBroadCast(buffer.toString());

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

                buffer.append("\r\n");
                buffer.append("LocalAddress" + ioSession.getLocalAddress() + "\r\n");
                buffer.append("getRemoteAddress" + ioSession.getRemoteAddress() + "+\r\n");
                buffer.append("content:\r\n 收到客户端的需要音量的消息\r\n");
                sendBroadCast(buffer.toString());
                sendVolMessage(ioSession, Code.ACTION_VOL_GET);
            } else if (msg.getMessageHead().getActionCode() == Code.ACTION_VOL_SET) {
                StringBuffer buffer = new StringBuffer();

                buffer.append("\r\n");
                buffer.append("LocalAddress" + ioSession.getLocalAddress() + "\r\n");
                buffer.append("getRemoteAddress" + ioSession.getRemoteAddress() + "+\r\n");
                buffer.append("content:\r\n 收到客户端的设置音量的消息\r\n");

                VolBody body = new VolBody();
                body.decoder(msg.getMessageBody().encode());

                buffer.append(body.volL + "," + body.volR + "\r\n");
                sendBroadCast(buffer.toString());
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

                buffer.append("\r\n");
                buffer.append("LocalAddress" + var1.getLocalAddress() + "\r\n");
                buffer.append("getRemoteAddress" + var1.getRemoteAddress() + "+\r\n");
                buffer.append("content:\r\n 收到客户端的旋转摄像头的消息\r\n");
                buffer.append("水平:" + cameraBody.horizontal + ",垂直:" + cameraBody.vertical + "\r\n");
                horizonal = cameraBody.horizontal;
                vertical = cameraBody.vertical;
                sendBroadCast(buffer.toString());
            } else if (msg.getMessageHead().getActionCode() == Code.ACTION_CAMERA_ANGLE_GET) {
                StringBuffer buffer = new StringBuffer();

                buffer.append("\r\n");
                buffer.append("LocalAddress" + var1.getLocalAddress() + "\r\n");
                buffer.append("getRemoteAddress" + var1.getRemoteAddress() + "+\r\n");
                buffer.append("content:\r\n 收到客户端的需要获取摄像头角度的消息\r\n");
                buffer.append("水平:" + horizonal + ",垂直:" + vertical + "\r\n");
                sendBroadCast(buffer.toString());
                CameraGetBody body = new CameraGetBody(horizonal, vertical, -90, -90, 90, 90);
                MessageHead head = new MessageHeadImpl("".getBytes(), MessageHead.HEAD_LENGTH + CameraGetBody.LENGTH, Code.ACTION_CAMERA_ANGLE_GET, 1);
                Message message = new MessageImpl(head, body);
                var1.write(message);
            }

        }
    };


    MessageHandler touchHandler = new MessageHandler() {
        @Override
        public void handleMessage(IoSession ioSession, Message msg) {
            if (msg.getMessageHead().getActionCode() == Code.ACTION_MOTION_GET) {
                StringBuffer buffer = new StringBuffer();

                buffer.append("\r\n");
                buffer.append("LocalAddress" + ioSession.getLocalAddress() + "\r\n");
                buffer.append("getRemoteAddress" + ioSession.getRemoteAddress() + "+\r\n");
                buffer.append("content:\r\n 收到客户端的需要屏幕宽高的消息\r\n");

                TouchGetBody body = new TouchGetBody();
                body.width = getResources().getDisplayMetrics().widthPixels;
                body.height = getResources().getDisplayMetrics().heightPixels;
                buffer.append("宽度:" + body.width + ",高度:" + body.height + "\r\n");
                sendBroadCast(buffer.toString());
                MessageHead head = new MessageHeadImpl("".getBytes(), MessageHead.HEAD_LENGTH + body.LENGTH, Code.ACTION_MOTION_GET, 1);
                Message message = new MessageImpl(head, body);
                ioSession.write(message);

                return;
            }
            TouchBody body = new TouchBody();
            body.decoder(msg.getMessageBody().encode());
            StringBuffer buffer = new StringBuffer();

            buffer.append("\r\n");
            buffer.append("LocalAddress" + ioSession.getLocalAddress() + "\r\n");
            buffer.append("getRemoteAddress" + ioSession.getRemoteAddress() + "+\r\n");
            buffer.append("content:\r\n 收到客户端的屏幕触摸的消息\r\n");
            for (int i = 0; i < body.infos.length; i++) {
                TouchBody.TouchInfo info = body.infos[i];
                if (info.action == 1) {
                    buffer.append("DOWN  ");
                } else if (info.action == 2) {
                    buffer.append("MOVE  ");
                } else if (info.action == 3) {
                    buffer.append("UP  ");
                }
                buffer.append("x:" + info.x + ",y:" + info.y + "\r\n");
            }
            sendBroadCast(buffer.toString());

        }
    };

    MessageHandler openAppHandler = new MessageHandler() {
        @Override
        public void handleMessage(IoSession var1, Message msg) {
            if (msg.getMessageHead().getActionCode() == Code.ACTION_MONITORING) {

                StringBuffer buffer = new StringBuffer();

                buffer.append("\r\n");
                buffer.append("LocalAddress" + var1.getLocalAddress() + "\r\n");
                buffer.append("getRemoteAddress" + var1.getRemoteAddress() + "+\r\n");
                buffer.append("content:\r\n 收到客户端的旋转摄像头的消息\r\n");
                buffer.append(" 打开远程监控 \r\n");

                sendBroadCast(buffer.toString());
            } else if (msg.getMessageHead().getActionCode() == Code.ACTION_MICROPHONE) {
                StringBuffer buffer = new StringBuffer();

                buffer.append("\r\n");
                buffer.append("LocalAddress" + var1.getLocalAddress() + "\r\n");
                buffer.append("getRemoteAddress" + var1.getRemoteAddress() + "+\r\n");
                buffer.append("content:\r\n 收到客户端的需要获取摄像头角度的消息\r\n");
                buffer.append(" 打开话筒 \r\n");
                sendBroadCast(buffer.toString());
            }

        }
    };

}
