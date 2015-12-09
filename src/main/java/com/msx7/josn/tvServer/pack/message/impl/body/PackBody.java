package com.msx7.josn.tvServer.pack.message.impl.body;

/**
 * Created by xiaowei on 2015/12/8.
 */

import com.msx7.josn.tvServer.mima.MinaConstants;
import com.msx7.josn.tvServer.pack.message.MessageBody;
import com.msx7.josn.tvServer.pack.message.MessageHead;

/**
 * 分包消息体
 */
public class PackBody implements MessageBody {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 消息体长度
     */
    public static final int BODY_LENGTH = MinaConstants.MAX_MESSAFE_LENGTH
            - MessageHead.HEAD_LENGTH;

    /**
     * 字节流
     */
    public byte[] body = null;

    public PackBody() {
        super();
    }

    /**
     * 创建一个登录消息体
     *
     * @param bytes 字节流
     */
    public PackBody(byte[] bytes) {
        body = bytes;
    }

    public byte[] encode() {
        return body;
    }

    public int getBodyLength() {
        return body.length;
    }
}
