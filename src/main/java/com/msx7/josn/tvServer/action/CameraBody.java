package com.msx7.josn.tvServer.action;

import com.msx7.josn.tvServer.mima.common.util.ByteUtil;
import com.msx7.josn.tvServer.pack.IDecoder;
import com.msx7.josn.tvServer.pack.message.MessageBody;

/**
 * Created by Josn on 2015/12/21.
 */
public class CameraBody implements MessageBody,IDecoder{
    /**
     * 负值表示 偏左，正表示偏右
     */
    int horizontal ;
    /**
     * 负值表示 偏下，正，表示偏上
     */
    int vertical;
    @Override
    public void decoder(byte[] bytes) {
        byte[] _byte = new byte[4];
        System.arraycopy(bytes, 0, _byte, 0, 4);
        horizontal = ByteUtil.bytesToInt(bytes);
        System.arraycopy(bytes, 4, _byte, 0, 4);
        vertical = ByteUtil.bytesToInt(bytes);
    }

    @Override
    public int getBodyLength() {
        return 8;
    }

    @Override
    public byte[] encode() {
        byte[] _byte = new byte[8];
        System.arraycopy(ByteUtil.intToBytes(horizontal), 0, _byte, 0, 4);
        System.arraycopy(ByteUtil.intToBytes(vertical), 0, _byte, 4, 4);
        return _byte;
    }
}
