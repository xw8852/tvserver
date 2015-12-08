package com.msx7.josn.tvServer.pack.message.impl.body;

import com.msx7.josn.tvServer.pack.message.MessageBody;

/**
 * Created by Josn on 2015/12/6.
 */
public class ConnectionMessageBody implements MessageBody {

    /**
     * 得到消息体转换为字节码后的长度
     *
     * @return 消息体字节码长度
     */
    @Override
    public int getBodyLength() {
        return encode().length;
    }

    /**
     * 编码成字节流.
     *
     * @return 字节流
     */
    @Override
    public byte[] encode() {
        int _len = "寻找机顶盒".getBytes().length;
        byte[] bytes = new byte[_len + MSG_END_FLAG_WIDTH];
        System.arraycopy("寻找机顶盒".getBytes(), 0, bytes, 0, _len);
        bytes[bytes.length - 1] = messageEndFlag;
        return bytes;
    }


}
