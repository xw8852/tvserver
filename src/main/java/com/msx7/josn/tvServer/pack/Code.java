package com.msx7.josn.tvServer.pack;

/**
 * Created by Josn on 2015/12/6.
 * 所有功能的编码
 * 在包里面预留长度为4位即0-9999
 */
public class Code {
    /**
     * 心跳包
     */
    public static final int BASE_CODE = 1;
    /**
     * 触摸动作 -按下
     */
    public static final int ACTION_EVENT_DOWN=2;
    /**
     * 触摸动作 -移动
     */
    public static final int ACTION_EVENT_MOVE=3;
    /**
     * 触摸动作 -弹起
     */
    public static final int ACTION_EVENT_UP=5;

}
