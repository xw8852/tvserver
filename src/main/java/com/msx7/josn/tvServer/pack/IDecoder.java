package com.msx7.josn.tvServer.pack;

/**
 * Created by Josn on 2015/12/6.
 * 包解码
 */
public interface IDecoder {

    /**
     * 解码字节流
     *
     * @return 字节流
     */
    public void decoder(byte[] bytes);
}
