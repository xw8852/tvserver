package com.msx7.josn.tvServer.action;

import com.msx7.josn.tvServer.mima.common.util.ByteUtil;
import com.msx7.josn.tvServer.pack.IDecoder;
import com.msx7.josn.tvServer.pack.message.MessageBody;

/**
 * 双声道设置音量
 * Created by Josn on 2015/12/21.
 */
public class DoubleVolBody implements MessageBody, IDecoder {
    int vol;

    @Override
    public void decoder(byte[] bytes) {
        vol = ByteUtil.bytesToInt(bytes);
    }

    @Override
    public int getBodyLength() {
        return 4;
    }

    @Override
    public byte[] encode() {
        return ByteUtil.intToBytes(vol);
    }
}
