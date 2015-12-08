package com.msx7.josn.tvServer.pack.message.impl.body;

import android.text.TextUtils;

import com.msx7.josn.tvServer.pack.message.MessageBody;

/**
 * Created by xiaowei on 2015/12/8.
 */
public class StringMessageBody implements MessageBody {
    String content;

    public StringMessageBody(String content) {
        this.content = content;
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
        if (!TextUtils.isEmpty(content)) {
            return content.getBytes();
        }
        return new byte[0];
    }
}
