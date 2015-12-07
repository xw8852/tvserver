package com.msx7.josn.tvServer.pack.message;

import com.msx7.josn.tvServer.pack.IEncoder;

/**
 * Created by xiaowei on 2015/12/7.
 * 包头，包括四个部分：
 * 客户端的唯一标识码，由服务端给出，16
 * 包长 4
 * 包序号 4
 * 动作编码 4
 */
public abstract class Message implements MessageBody, MessageHead, IEncoder {
    /**
     * 编码成字节流.
     *
     * @return 字节流
     */
    @Override
    public byte[] encode() {
        byte[] bytes = new byte[LENGTH];
        if (getDeviceCode() != null) {
            byte[] _code = getDeviceCode();
            int length = _code.length;
            int offset = 0;
            length = Math.min(16, length);
            offset = 16 - length;
            System.arraycopy(_code, offset, bytes, 0, length);
        }
        int length = getLength();
        System.arraycopy(intToByteArray1(length), 0, bytes, 16, 4);
        System.arraycopy(intToByteArray1(getId()), 0, bytes, 20, 4);
        System.arraycopy(intToByteArray1(getActionCode()), 0, bytes, 14, 4);

        byte[] _body = getBody();
        if (_body.length > 0) {
            byte[] _bytes = new byte[LENGTH + _body.length];
            System.arraycopy(bytes, 0, bytes, 0, LENGTH);
            System.arraycopy(_body, 0, bytes, LENGTH, _body.length);
            return _bytes;
        }
        return bytes;
    }


    /**
     * 将int类型的数据转换为byte数组
     * 原理：将int数据中的四个byte取出，分别存储
     *
     * @param i int数据
     * @return 生成的byte数组
     */
    public static byte[] intToByteArray1(int i) {
        byte[] result = new byte[4];
        result[0] = (byte) ((i >> 24) & 0xFF);
        result[1] = (byte) ((i >> 16) & 0xFF);
        result[2] = (byte) ((i >> 8) & 0xFF);
        result[3] = (byte) (i & 0xFF);
        return result;
    }
}
