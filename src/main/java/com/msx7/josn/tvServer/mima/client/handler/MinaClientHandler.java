package com.msx7.josn.tvServer.mima.client.handler;

import android.util.Log;

import com.msx7.josn.tvServer.pack.MessageHandlerLib;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

/**
 * Created by xiaowei on 2015/12/8.
 */
public class MinaClientHandler implements IoHandler{
    private static MinaClientHandler minaClientHandler = null;

    public static MinaClientHandler getInstances() {
        if (null == minaClientHandler) {
            minaClientHandler = new MinaClientHandler();
        }
        return minaClientHandler;
    }

    private MinaClientHandler() {

    }
    @Override
    public void sessionCreated(IoSession ioSession) throws Exception {
        Log.d("way", "Created---LocalAddress:" + ioSession.getLocalAddress().toString());
        Log.d("way", "Created---RemoteAddress:" + ioSession.getRemoteAddress().toString());
        Log.d("way", "Created---ServiceAddress:" + ioSession.getServiceAddress().toString());
        Log.d("way", "服务器与客户端会话创建了" );
    }

    @Override
    public void sessionOpened(IoSession ioSession) throws Exception {
        Log.d("way", "服务器与客户端会话打开了" );
    }

    @Override
    public void sessionClosed(IoSession ioSession) throws Exception {
        Log.d("way", "Closed---LocalAddress:" + ioSession.getLocalAddress().toString());
        Log.d("way", "Closed---RemoteAddress:" + ioSession.getRemoteAddress().toString());
        Log.d("way", "Closed---ServiceAddress:" + ioSession.getServiceAddress().toString());
        Log.d("way", "服务器与客户端会话关闭了" );

    }

    @Override
    public void sessionIdle(IoSession ioSession, IdleStatus idleStatus) throws Exception {
        Log.d("way", "服务器与的客户端会话空闲了");
    }

    @Override
    public void exceptionCaught(IoSession ioSession, Throwable throwable) throws Exception {
        Log.d("way", "服务器与的客户端会话异常了");
    }

    @Override
    public void messageReceived(IoSession ioSession, Object o) throws Exception {
        Log.d("way", "服务器与的客户端会话接受了");
        MessageHandlerLib.getInstance().getHandler("").handleMessage(ioSession, o);
    }

    @Override
    public void messageSent(IoSession ioSession, Object o) throws Exception {
        Log.d("way", "服务器与的客户端会话发送了");
    }

    @Override
    public void inputClosed(IoSession ioSession) throws Exception {
        Log.d("way", "服务器与的客户端会话inputClosed了");
    }
}
