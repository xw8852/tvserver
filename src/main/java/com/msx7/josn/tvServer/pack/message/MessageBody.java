package com.msx7.josn.tvServer.pack.message;

import com.msx7.josn.tvServer.mima.MinaConstants;
import com.msx7.josn.tvServer.pack.IEncoder;

/**
 * Created by Josn on 2015/12/6.
 */
public interface MessageBody extends IEncoder {
    /**
     * 消息结束标识符长度
     */
    public static final int MSG_END_FLAG_WIDTH = 1;

    /**
     * 消息结束标识
     */
    public static final byte messageEndFlag = MinaConstants.MESSASGE_END_FLAG;

    /**
     * 得到消息体转换为字节码后的长度
     *
     * @return 消息体字节码长度
     */
    public int getBodyLength();
}
