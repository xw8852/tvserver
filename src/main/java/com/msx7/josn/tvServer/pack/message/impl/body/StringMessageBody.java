package com.msx7.josn.tvServer.pack.message.impl.body;

import com.msx7.josn.tvServer.pack.message.MessageBody;

/**
 * Created by xiaowei on 2015/12/8.
 */
public class StringMessageBody implements MessageBody {

    byte[] bytes;

    public StringMessageBody(String content) {
        this(content.getBytes());
    }

    public StringMessageBody(byte[] bytes) {
        this.bytes = bytes;
    }

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
        int _len = bytes.length;
        byte[] _bytes = new byte[_len + MSG_END_FLAG_WIDTH];
        System.arraycopy(bytes, 0, _bytes, 0, _len);
        _bytes[_len] = messageEndFlag;
        return _bytes;
    }
}
