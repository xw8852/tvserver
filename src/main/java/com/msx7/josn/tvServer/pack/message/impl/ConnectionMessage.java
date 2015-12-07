package com.msx7.josn.tvServer.pack.message.impl;

import com.msx7.josn.tvServer.pack.message.Message;

/**
 * Created by Josn on 2015/12/6.
 */
public class ConnectionMessage extends Message {

    @Override
    public byte[] getBody() {
        return "寻找机顶盒".getBytes();
    }

    @Override
    public byte[] getDeviceCode() {
        return "0001".getBytes();
    }

    @Override
    public int getLength() {
        return LENGTH + getBody().length;
    }

    @Override
    public int getId() {
        return 1;
    }

    @Override
    public int getActionCode() {
        return 1;
    }


}
