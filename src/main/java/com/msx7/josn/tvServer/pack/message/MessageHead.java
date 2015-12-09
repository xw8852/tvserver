package com.msx7.josn.tvServer.pack.message;

import com.msx7.josn.tvServer.mima.MinaConstants;
import com.msx7.josn.tvServer.pack.IEncoder;

/**
 * Created by Josn on 2015/12/6.
 * 包头，包括四个部分：
 * 客户端的唯一标识码，由服务端给出，
 * 包长
 * 包序号
 * 动作编码
 */
public interface MessageHead extends IEncoder {
    /**
     * 包头长度
     */
    public static final int HEAD_LENGTH = 37;
    /**
     * 消息总长度字段长度
     */
    public static final int MSG_LEN_COUNT = 4;
    /**
     * 客户端唯一标识符字段长度
     */
    public static final int MSG_LEN_CODE = 16;
    /**
     * 消息ID字段长度
     */
    public static final int MSG_LEN_ID = 4;
    /**
     * 动作编码字段长度
     */
    public static final int MSG_LEN_ACTION = 4;
    /**
     * 消息开始标识符长度
     */
    public static final int MSG_START_FLAG_WIDTH = 1;
    /**
     * 总的包数字段的长度
     */
    public static final int PACK_COUNT_WIDTH = 4;
    /**
     * 包的索引字段的长度
     */
    public static final int PACK_INDEX_WIDTH = 4;
    /**
     * 消息开始标识
     */
    public static final byte messageStartFlag = MinaConstants.MESSASGE_START_FLAG;

    /**
     * 客户端唯一标示符 预留16位
     *
     * @return
     */
    public byte[] getDeviceCode();

    /***
     * 包长 预留4位
     *
     * @return
     */
    public int getLength();

    public  void setLength(int length);

    /**
     * 消息ID 预留4位
     *
     * @return
     */
    public int getId();

    /**
     * 动作编码 预留4位
     *
     * @return
     */
    public int getActionCode();

    public int getPackCount();
    public int getPackIndex();
}
