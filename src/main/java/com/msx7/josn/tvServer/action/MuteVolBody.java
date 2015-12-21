package com.msx7.josn.tvServer.action;

import com.msx7.josn.tvServer.pack.IDecoder;
import com.msx7.josn.tvServer.pack.message.MessageBody;

/**
 * 音量静音
 * Created by Josn on 2015/12/21.
 */
public class MuteVolBody implements MessageBody, IDecoder {
    /**
     * 1 表示静音
     * 非1：表示不静音
     */
    boolean isMute;

    @Override
    public void decoder(byte[] bytes) {
        isMute = bytes[0] == 1;
    }

    @Override
    public int getBodyLength() {
        return 1;
    }

    @Override
    public byte[] encode() {
        return isMute ? new byte[]{1} : new byte[]{0};
    }
}
