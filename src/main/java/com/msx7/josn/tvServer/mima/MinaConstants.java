package com.msx7.josn.tvServer.mima;

import com.msx7.josn.tvServer.pack.message.MessageHead;

import java.util.ArrayList;
import java.util.List;

public class MinaConstants {
    /**
     * mina服务端口号
     */
    public static final int MINA_PORT = 8745;

    /**
     * mina客户端口号
     */
    public static final int MINA_CLIENT_PORT = 8775;
    /**
     * mina服务器Ip
     */
    public static final String MINA_SERVER_IP = "127.0.0.1";

    /**
     * 消息开始标识符
     */
    public static final byte MESSASGE_START_FLAG = 0x5b;

    /**
     * I/O Porcessor线程数
     */
    public static final int PROCESSOR_COUNT = Runtime.getRuntime()
            .availableProcessors() + 1;

    /**
     * 消息结束标识符
     */
    public static final byte MESSASGE_END_FLAG = 0x5d;

    /**
     * 判断是否连接标识
     */
    public static final String HAD_CONNCETION = "hadConnection";

    /**
     * 消息的最大长度
     */
    public static final int MAX_MESSAFE_LENGTH = 100;

    /**
     * 消息体的最大长度
     */
    public static final int MAX_BOYD_LENGTH = MAX_MESSAFE_LENGTH
            - MessageHead.HEAD_LENGTH;

    /**
     * 消息String类型字段长度不够时，用于填充，代表空格
     */
    public static final byte COVER_SIGN = 0x20;

    public static void main(String[] args) {
        System.out.println(MESSASGE_END_FLAG);
        byte[] bytes = new byte[1];
        bytes[0] = 0x02;
        // bytes[1] = 0x02;
        System.out.println(new String(bytes));
        List<String> list = new ArrayList<String>();
        list.add(null);
        System.out.println(list == null);
    }
}
