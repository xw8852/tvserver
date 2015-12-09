package com.msx7.josn.tvServer.mima.server.handler;

import android.util.Log;

import com.msx7.josn.tvServer.mima.common.util.MinaUtil;
import com.msx7.josn.tvServer.pack.message.Message;

import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

/**
 * Created by xiaowei on 2015/12/8.
 */
public class ServerMinaHandler extends IoFilterAdapter implements IoHandler {
    private static ServerMinaHandler minaServerHandler = null;

    public static ServerMinaHandler getInstances() {
        if (null == minaServerHandler) {
            minaServerHandler = new ServerMinaHandler();
        }
        return minaServerHandler;
    }

    private ServerMinaHandler() {

    }

    @Override
    public void sessionCreated(IoSession ioSession) throws Exception {
        Log.d("way", "Created---LocalAddress:" + ioSession.getLocalAddress().toString());
        Log.d("way", "Created---RemoteAddress:" + ioSession.getRemoteAddress().toString());
        Log.d("way", "Created---ServiceAddress:" + ioSession.getServiceAddress().toString());
        Log.d("way", "服务器与客户端会话创建了");
        handMsg("服务器与客户端会话创建了\r\n"+ioSession.getLocalAddress().toString() +"\r\n"+ ioSession.getServiceAddress().toString());

    }

    @Override
    public void sessionOpened(IoSession ioSession) throws Exception {
        Log.d("way", "服务器与客户端会话打开了");
        handMsg("服务器与客户端会话打开了\r\n"+ioSession.getLocalAddress().toString() +"\r\n"+ ioSession.getServiceAddress().toString());
    }

    @Override
    public void sessionClosed(IoSession ioSession) throws Exception {
        Log.d("way", "Closed---LocalAddress:" + ioSession.getLocalAddress().toString());
        Log.d("way", "服务器与客户端会话关闭了");
        handMsg("服务器与客户端会话关闭了");

    }

    @Override
    public void sessionIdle(IoSession ioSession, IdleStatus idleStatus) throws Exception {
        Log.d("way", "服务器与的客户端会话空闲了");
        handMsg("服务器与的客户端会话空闲了");

    }

    @Override
    public void exceptionCaught(IoSession ioSession, Throwable throwable) throws Exception {
        Log.d("way", "服务器与的客户端会话异常了");
        handMsg("服务器与的客户端会话异常了");
        throwable.printStackTrace();
    }

    @Override
    public void messageReceived(IoSession ioSession, Object o) throws Exception {
        Log.d("way", "服务器与的客户端会话接受了");
        MinaUtil.messageRecevie((Message)o,ioSession);
//        MessageHandler handler = MessageHandlerLib.getInstance().getHandler("");
//        if (handler != null)
//            handler.handleMessage(ioSession, o);
    }

    @Override
    public void messageSent(IoSession ioSession, Object o) throws Exception {
        Log.d("way", "服务器与的客户端会话发送了");
        handMsg("服务器与的客户端会话发送了");
    }

    @Override
    public void inputClosed(IoSession ioSession) throws Exception {
        Log.d("way", "服务器与的客户端会话inputClosed了");
        ioSession.close(true);
        handMsg("服务器与的客户端会话inputClosed了");
    }

    public void handMsg(String str){
        if(handler!=null){
            handler.handLog(str);
        }
    }
    ILogHandler handler;
    public  void setLogHandler(ILogHandler handler){
        this.handler=handler;
    }
    public interface  ILogHandler{
        public  void handLog(String string);
    }
}
