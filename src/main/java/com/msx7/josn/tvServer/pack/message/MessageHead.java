package com.msx7.josn.tvServer.pack.message;

/**
 * Created by Josn on 2015/12/6.
 * 包头，包括四个部分：
 * 客户端的唯一标识码，由服务端给出，
 * 包长
 * 包序号
 * 动作编码
 */
public interface MessageHead {
    /**
     * 包头长度
     */
    public static final int LENGTH = 33;

    /**
     * 客户端唯一标示符 预留16位
     *
     * @return
     */
    public String getDeviceCode();

    /***
     * 包长 预留4位
     *
     * @return
     */
    public int getLength();

    /**
     * 消息ID 预留9位
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

}
