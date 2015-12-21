package com.msx7.josn.tvServer.action;

import com.msx7.josn.tvServer.pack.IDecoder;
import com.msx7.josn.tvServer.pack.message.MessageBody;

/**
 * Created by Josn on 2015/12/21.
 */
public class TouchBody implements MessageBody,IDecoder {
    @Override
    public void decoder(byte[] bytes) {
        
    }

    @Override
    public int getBodyLength() {
        return 0;
    }

    @Override
    public byte[] encode() {
        return new byte[0];
    }
}
