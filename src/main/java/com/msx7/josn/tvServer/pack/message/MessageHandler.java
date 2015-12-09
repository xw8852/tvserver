package com.msx7.josn.tvServer.pack.message;

import org.apache.mina.core.session.IoSession;

/**
 * Created by xiaowei on 2015/12/9.
 */
public interface MessageHandler {
    void handleMessage(IoSession var1, Message msg);
}
