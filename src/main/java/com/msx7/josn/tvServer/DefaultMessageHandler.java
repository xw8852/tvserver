package com.msx7.josn.tvServer;

import android.os.Handler;

import com.msx7.josn.tvconnection.pack.message.Message;
import com.msx7.josn.tvconnection.pack.message.MessageHandler;

import org.apache.mina.core.session.IoSession;

/**
 * Created by Josn on 2015/12/26.
 */
public class DefaultMessageHandler implements MessageHandler {
    MessageHandler handler;
    Handler handler2;
    public DefaultMessageHandler(MessageHandler handler, Handler handler2) {
        this.handler = handler;
        this.handler2=handler2;
    }

    @Override
    public void handleMessage(final IoSession var1, final Message msg) {
        handler2.post(new Runnable() {
            @Override
            public void run() {
                if (handler != null) {
                    handler.handleMessage(var1, msg);
                }
            }
        });
    }

}
