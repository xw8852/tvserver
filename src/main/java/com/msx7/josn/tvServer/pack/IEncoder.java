package com.msx7.josn.tvServer.pack;

/**
 * Created by Josn on 2015/12/6.
 * 一个消息包，包括 包头，动作内容 两个部分
 * 包头，包括四个部分：
 * 客户端的唯一标识码，由服务端给出，
 * 包长
 * 包序号
 * 动作编码
 */
public interface IEncoder {
//    public  Byte[] getTitle();
//    public  byte[] getAction();
//    public  Byte[] getContent();

    /**
     * 编码成字节流.
     *
     * @return 字节流
     */
    public byte[] encode();
}
