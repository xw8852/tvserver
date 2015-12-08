package com.msx7.josn.tvServer.pack.message.impl;

import com.msx7.josn.tvServer.mima.common.util.ByteUtil;
import com.msx7.josn.tvServer.pack.message.MessageHead;

/**
 * 包头，包括四个部分：
 * 客户端的唯一标识码，由服务端给出，16
 * 包长 4
 * 包序号 4
 * 动作编码 4
 * Created by xiaowei on 2015/12/8.
 */
public class MessageHeadImpl implements MessageHead {
    byte[] code;
    int msgLength;
    int actionId;
    int msgId;

    public MessageHeadImpl(byte[] bytes) {
        if (bytes[0] != messageStartFlag) {
            return;
        }
        code = new byte[MSG_LEN_CODE];
        System.arraycopy(bytes, MSG_START_FLAG_WIDTH, code, 0, MSG_LEN_CODE);
        byte[] tmp = new byte[4];
        System.arraycopy(bytes, MSG_LEN_CODE + MSG_START_FLAG_WIDTH, tmp, 0, MSG_LEN_COUNT);
        msgLength = ByteUtil.bytesToInt(tmp);
        System.arraycopy(bytes, MSG_LEN_CODE + MSG_START_FLAG_WIDTH + MSG_LEN_COUNT, tmp, 0, MSG_LEN_ID);
        msgId = ByteUtil.bytesToInt(tmp);
        System.arraycopy(bytes, MSG_LEN_CODE + MSG_START_FLAG_WIDTH + MSG_LEN_COUNT+MSG_LEN_ID, tmp, 0, MSG_LEN_ACTION);
        actionId = ByteUtil.bytesToInt(tmp);
    }

    /**
     * @param code      客户端的唯一标示符
     * @param msgLength 消息长度
     * @param actionId  动作编码ID
     * @param msgId     消息ID
     */
    public MessageHeadImpl(byte[] code, int msgLength, int actionId, int msgId) {
        this.code = code;
        this.msgLength = msgLength;
        this.actionId = actionId;
        this.msgId = msgId;
    }

    /**
     * 客户端唯一标示符 预留16位
     *
     * @return
     */
    @Override
    public byte[] getDeviceCode() {
        return code;
    }

    /***
     * 包长 预留4位
     *
     * @return
     */
    @Override
    public int getLength() {
        return msgLength;
    }

    /**
     * 消息ID 预留4位
     *
     * @return
     */
    @Override
    public int getId() {
        return msgId;
    }

    /**
     * 动作编码 预留4位
     *
     * @return
     */
    @Override
    public int getActionCode() {
        return actionId;
    }

    /**
     * 包头，包括四个部分：
     * 客户端的唯一标识码，由服务端给出，16
     * 包长 4
     * 包序号 4
     * 动作编码 4
     * <p>
     * 编码成字节流.
     *
     * @return 字节流
     */
    @Override
    public byte[] encode() {
        byte[] bytes = new byte[HEAD_LENGTH];
        bytes[0] = messageStartFlag;
        if (getDeviceCode() != null) {
            byte[] _code = getDeviceCode();
            int length = _code.length;
            length = Math.min(16, length);
            int offset = 16 - length;
            System.arraycopy(_code, 0, bytes, offset + MSG_START_FLAG_WIDTH, length);
        }
        int length = getLength();
        System.arraycopy(ByteUtil.intToBytes(length), 0, bytes, 16 + MSG_START_FLAG_WIDTH, 4);
        System.arraycopy(ByteUtil.intToBytes(getId()), 0, bytes, 20 + MSG_START_FLAG_WIDTH, 4);
        System.arraycopy(ByteUtil.intToBytes(getActionCode()), 0, bytes, 24 + MSG_START_FLAG_WIDTH, 4);
        return bytes;
    }
}
