package com.msx7.josn.tvServer.pack.message.impl;

import com.msx7.josn.tvServer.pack.IEncoder;
import com.msx7.josn.tvServer.pack.message.MessageBody;
import com.msx7.josn.tvServer.pack.message.MessageHead;

/**
 * Created by Josn on 2015/12/6.
 */
public class ConnectionMessage implements MessageBody, MessageHead, IEncoder {

    @Override
    public String getBody() {
        return "寻找机顶盒";
    }

    @Override
    public String getDeviceCode() {
        return "0001";
    }

    @Override
    public int getLength() {
        return 0;
    }

    @Override
    public int getId() {
        return 1;
    }

    @Override
    public int getActionCode() {
        return 1;
    }

    @Override
    public byte[] encode() {
        return new byte[0];
    }
}
