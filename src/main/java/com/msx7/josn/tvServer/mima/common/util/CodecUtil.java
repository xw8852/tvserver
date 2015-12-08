package com.msx7.josn.tvServer.mima.common.util;

import java.util.concurrent.atomic.AtomicInteger;


		import java.util.concurrent.atomic.AtomicInteger;

/**
 * 编码工具类
 */
public abstract class CodecUtil {

	private static final int MAX_BYTE = 0xff;

	private static final int FULL_ONE_SHORT = 0xffff;

	private static AtomicInteger seqIdCreator = new AtomicInteger(0);

	/**
	 * 得到消息信息
	 *
	 * @param messageType
	 *            消息类型
	 * @param seqType
	 *            是否是请求消息
	 *
	 * @return 消息信息
	 */
	public static int getMessageInfo(int messageType, byte seqType) {
		int info = messageType << Byte.SIZE;
		info |= seqType & MAX_BYTE;
		return info;
	}

	/**
	 * 得到消息序列号
	 *
	 * @return 序列号
	 */
	public static int getSequenceId() {
		return seqIdCreator.getAndIncrement() & Integer.MAX_VALUE;
	}

	/**
	 * 得到线程名
	 *
	 * @param threadType
	 *            线程类型
	 * @param seq
	 *            请求
	 *
	 * @return 线程名
	 */
	public static String getThreadName(String threadType, int seq) {
		StringBuilder buffer = new StringBuilder();
		buffer.append(threadType);
		buffer.append(seq);
		return buffer.toString();
	}

	/**
	 * Merge short
	 *
	 * @param short1
	 *            int1
	 * @param short2
	 *            int2
	 *
	 * @return int
	 */
	public static int mergeShort(int short1, int short2) {
		int tmp = 0;
		tmp |= short1 << Short.SIZE;
		tmp |= short2 & FULL_ONE_SHORT;
		return tmp;

	}

	/**
	 * Check bytes length. 比较byte当前的长度是否能够添加指定的新数组
	 *
	 * @param bytes
	 *            the bytes 添加前的byte
	 * @param oldLength
	 *            the oldLength 原byte长度+要添加的byte长度
	 * @param initLength
	 *            the initLength 增加byte的初始长度
	 *
	 * @return the byte[] 返回新的byte数组
	 */
	public static byte[] checkBytesLength(byte[] bytes, int oldLength,
										  int initLength) {
		int tempLength = oldLength;
		int bytesLength = bytes.length;
		if (tempLength >= bytesLength) {
			int newInit = tempLength + initLength;
			byte[] newBytes = new byte[newInit];
			System.arraycopy(bytes, 0, newBytes, 0, bytesLength);
			return newBytes;
		} else {
			return bytes;
		}
	}

	/**
	 * 输入异常信息
	 *
	 * @param e
	 *
	 * @return string
	 */
	public static String printException(Exception e) {
		StackTraceElement[] sta = e.getStackTrace();
		StringBuilder buff = new StringBuilder();
		for (StackTraceElement stac : sta) {
			buff.append(stac.toString());
			buff.append("\n");
		}
		return buff.toString();
	}

}
