package com.msx7.josn.tvServer.mima.common.util;

import com.msx7.josn.tvServer.mima.MinaConstants;


import java.util.Arrays;


/**
 * ByteUtil.
 */
public abstract class ByteUtil {
    public static final int ZERO = 0;

    public static final int ONE = 1;

    public static final int TWO = 2;

    public static final int THREE = 3;

    public static final int FOUR = 4;

    public static final int FIVE = 5;

    public static final int SIX = 6;

    public static final int SEVEN = 7;

    public static final int EIGHT = 8;

    public static final int NINE = 9;

    public static final int THREE_BYTES_SIZE = 24;

    public static final int FIVE_BYTES_SIZE = 40;

    public static final int SIX_BYTES_SIZE = 48;

    public static final int SEVEN_BYTES_SIZE = 56;

    public static final short MAX_BYTE = 0xff;

    public static final int HALF_BYTE = 0xf;

    public static final int LONG_WIDTH = 8;

    public static final int INT_WIDTH = 4;

    public static final int SHORT_WIDTH = 2;

    public static final int DEFAULT_WIDTH = 16;

    /**
     * Bytes转换为int.
     *
     * @param bytes 码流
     * @return int
     */
    public static int bytesToInt(byte[] bytes) {
        int tmp = bytes[THREE] & MAX_BYTE;
        tmp |= (bytes[TWO] & MAX_BYTE) << Byte.SIZE;
        tmp |= (bytes[ONE] & MAX_BYTE) << Short.SIZE;
        tmp |= (bytes[ZERO] & MAX_BYTE) << THREE_BYTES_SIZE;
        return tmp;
    }

    /**
     * Bytes转换为int.
     *
     * @param bytes  码流
     * @param offset 开始索引
     * @return int
     */
    public static int bytesToInt(byte[] bytes, int offset) {
        int tmp = bytes[offset + THREE] & MAX_BYTE;
        tmp |= (bytes[offset + TWO] & MAX_BYTE) << Byte.SIZE;
        tmp |= (bytes[offset + ONE] & MAX_BYTE) << Short.SIZE;
        tmp |= (bytes[offset] & MAX_BYTE) << THREE_BYTES_SIZE;
        return tmp;
    }

    /**
     * Bytes转换为long.
     *
     * @param bytes 码流
     * @return long
     */
    public static long bytesToLong(byte[] bytes) {
        long tmp = bytes[SEVEN] & MAX_BYTE;
        tmp |= (bytes[SIX] & MAX_BYTE) << Byte.SIZE;
        tmp |= (bytes[FIVE] & MAX_BYTE) << Short.SIZE;
        tmp |= (bytes[FOUR] & MAX_BYTE) << THREE_BYTES_SIZE;
        tmp |= (long) (bytes[THREE] & MAX_BYTE) << Integer.SIZE;
        tmp |= (long) (bytes[TWO] & MAX_BYTE) << FIVE_BYTES_SIZE;
        tmp |= (long) (bytes[ONE] & MAX_BYTE) << SIX_BYTES_SIZE;
        tmp |= (long) (bytes[ZERO] & MAX_BYTE) << SEVEN_BYTES_SIZE;
        return tmp;
    }

    /**
     * Bytes转换为long.
     *
     * @param bytes  码流
     * @param offset 开始索引
     * @return long
     */
    public static long bytesToLong(byte[] bytes, int offset) {
        long tmp = bytes[offset + SEVEN] & MAX_BYTE;
        tmp |= (bytes[offset + SIX] & MAX_BYTE) << Byte.SIZE;
        tmp |= (bytes[offset + FIVE] & MAX_BYTE) << Short.SIZE;
        tmp |= (bytes[offset + FOUR] & MAX_BYTE) << THREE_BYTES_SIZE;
        tmp |= (long) (bytes[offset + THREE] & MAX_BYTE) << Integer.SIZE;
        tmp |= (long) (bytes[offset + TWO] & MAX_BYTE) << FIVE_BYTES_SIZE;
        tmp |= (long) (bytes[offset + ONE] & MAX_BYTE) << SIX_BYTES_SIZE;
        tmp |= (long) (bytes[offset + ZERO] & MAX_BYTE) << SEVEN_BYTES_SIZE;
        return tmp;
    }

    /**
     * Bytes转换为short.
     *
     * @param bytes 码流
     * @return short
     */
    public static short bytesToShort(byte[] bytes) {
        return (short) ((bytes[ONE] & MAX_BYTE) | (bytes[ZERO] & MAX_BYTE) << Byte.SIZE);
    }

    /**
     * Bytes转换为short.
     *
     * @param bytes  码流
     * @param offset 开始索引
     * @return short
     */
    public static short bytesToShort(byte[] bytes, int offset) {
        return (short) ((bytes[offset + ONE] & MAX_BYTE) | (bytes[offset] & MAX_BYTE) << Byte.SIZE);
    }

    /**
     * 比较码流
     *
     * @param bytes1  bytes1
     * @param offset1 码流的开始索引
     * @param bytes2  bytes2
     * @param offset2 码流的开始索引
     * @return
     */
    public static boolean compare(byte[] bytes1, int offset1, byte[] bytes2,
                                  int offset2) {
        if (offset1 + bytes2.length > offset2 + bytes1.length) {
            return false;
        }
        for (int i = 0; i < bytes2.length; ++i) {
            if (bytes1[i + offset1] != bytes2[i + offset2]) {
                return false;
            }
        }
        return true;
    }

    /**
     * 比较码流
     *
     * @param bytes1 bytes1
     * @param bytes2 bytes2
     * @return
     */
    public static boolean compare(byte[] bytes1, byte[] bytes2) {
        if (bytes1.length != bytes2.length) {
            return false;
        }
        return compare(bytes1, 0, bytes2, 0);
    }

    /**
     * 拼接两个字节数组，将第二个数组拼接到第一个后面.
     *
     * @param source1 第一个字节数组
     * @param source2 第二个字节数组
     * @return 拼接后的字节数组
     */
    public static byte[] concat(byte[] source1, byte[] source2) {
        if (source1 == null) {
            throw new NullPointerException("source1 byte array is null");
        }
        if (source2 == null) {
            throw new NullPointerException("source2 byte array is null");
        }
        byte[] buffer = new byte[source1.length + source2.length];
        System.arraycopy(source1, 0, buffer, 0, source1.length);
        System.arraycopy(source2, 0, buffer, source1.length, source2.length);
        return buffer;
    }

    /**
     * Int转换为bytes.
     *
     * @param source int
     * @return 码流
     */
    public static byte[] intToBytes(int source) {
        byte[] bytes = new byte[INT_WIDTH];
        bytes[ZERO] = (byte) (source >> THREE_BYTES_SIZE);
        bytes[ONE] = (byte) (source >> Short.SIZE);
        bytes[TWO] = (byte) (source >> Byte.SIZE);
        bytes[THREE] = (byte) source;
        return bytes;
    }

    /**
     * Int转换为bytes.
     *
     * @param source int
     * @param target
     * @param offset
     */
    public static void intToBytes(int source, byte[] target, int offset) {
        target[offset] = (byte) (source >> THREE_BYTES_SIZE);
        target[offset + ONE] = (byte) (source >> Short.SIZE);
        target[offset + TWO] = (byte) (source >> Byte.SIZE);
        target[offset + THREE] = (byte) source;
    }

    /**
     * Long转换为bytes.
     *
     * @param source long
     * @return 码流
     */
    public static byte[] longToBytes(long source) {
        byte[] bytes = new byte[LONG_WIDTH];
        bytes[ZERO] = (byte) (source >> SEVEN_BYTES_SIZE);
        bytes[ONE] = (byte) (source >> SIX_BYTES_SIZE);
        bytes[TWO] = (byte) (source >> FIVE_BYTES_SIZE);
        bytes[THREE] = (byte) (source >> Integer.SIZE);
        bytes[FOUR] = (byte) (source >> THREE_BYTES_SIZE);
        bytes[FIVE] = (byte) (source >> Short.SIZE);
        bytes[SIX] = (byte) (source >> Byte.SIZE);
        bytes[SEVEN] = (byte) source;
        return bytes;
    }

    /**
     * Long转换为bytes.
     *
     * @param source long
     * @param target
     * @param offset
     */
    public static void longToBytes(long source, byte[] target, int offset) {
        target[offset] = (byte) (source >> SEVEN_BYTES_SIZE);
        target[offset + ONE] = (byte) (source >> SIX_BYTES_SIZE);
        target[offset + TWO] = (byte) (source >> FIVE_BYTES_SIZE);
        target[offset + THREE] = (byte) (source >> Integer.SIZE);
        target[offset + FOUR] = (byte) (source >> THREE_BYTES_SIZE);
        target[offset + FIVE] = (byte) (source >> Short.SIZE);
        target[offset + SIX] = (byte) (source >> Byte.SIZE);
        target[offset + SEVEN] = (byte) source;
    }

    /**
     * Short转换为bytes.
     *
     * @param source short
     * @return 码流
     */
    public static byte[] shortToBytes(short source) {
        byte[] bytes = new byte[SHORT_WIDTH];
        bytes[ZERO] = (byte) (source >> Byte.SIZE);
        bytes[ONE] = (byte) source;
        return bytes;
    }

    /**
     * Short转换为bytes.
     *
     * @param source short
     * @param bytes  码流
     * @param offset 开始索引
     */
    public static void shortToBytes(short source, byte[] bytes, int offset) {
        bytes[offset] = (byte) (source >> Byte.SIZE);
        bytes[offset + ONE] = (byte) source;
    }

    /**
     * Byte转换为string.
     *
     * @param bytes     源码流
     * @param offset    开始位置
     * @param strLength 复制的长度
     * @return
     */
    public static String bytesToString(byte[] bytes, int offset, int strLength) {
        byte[] stringBytes = new byte[strLength];
        System.arraycopy(bytes, offset, stringBytes, 0, strLength);
//		return new String(Arrays.copyOfRange(bytes, offset, offset + strLength)).trim();
        return new String(stringBytes).trim();

    }

    /**
     * String转换为byte
     *
     * @param source String
     * @param bytes  码流
     * @param offset 开始索引
     */
    public static void StringToBytes(String source, byte[] bytes, int offset,
                                     int strLength) {
        byte[] stringBytes = new byte[strLength];
        byte[] originalStringBytes = source.getBytes();
        System.arraycopy(originalStringBytes, 0, stringBytes, 0,
                originalStringBytes.length);
        if (originalStringBytes.length < strLength) {
            int lackLength = strLength - originalStringBytes.length;
            byte[] lackBytes = new byte[lackLength];
            Arrays.fill(lackBytes, MinaConstants.COVER_SIGN);
            System.arraycopy(lackBytes, 0, stringBytes,
                    originalStringBytes.length, lackLength);
        }
        System.arraycopy(stringBytes, 0, bytes, offset, stringBytes.length);
    }
}
