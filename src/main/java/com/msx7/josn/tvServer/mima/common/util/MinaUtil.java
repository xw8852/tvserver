package com.msx7.josn.tvServer.mima.common.util;


import com.msx7.josn.tvServer.pack.message.Message;

import java.util.ArrayList;
import java.util.List;


public class MinaUtil {

	/**
	 * 分包消息集合
	 */
	private static List<Message> messageList = new ArrayList<Message>();

	/**
	 * 包的数量
	 */
	private static int packCount = 0;

	/**
	 * 分包消息处理器
	 *
	 * @param message
	 * @param session
	 */
//	public static synchronized void messageRecevie(Message message,
//												   IoSession session) {
//		MessageHead head = message.getMessageHead();
//		int messageLength = MessageHeadImpl.HEAD_LENGTH
//				+ MessageType.bodyLengthMap.get(head.getMessageInfo());
//		byte[] bodyBytes = new byte[messageLength - MessageHead.HEAD_LENGTH];
//		if (head.getPackCount() == 1) {
//			bodyBytes = message.getMessageBody().encode();
//		} else {
//			/*
//			 * // 初始化messageList if (null == messageList) { messageList = new
//			 * ArrayList<Message>(); for (int i = 0; i < head.getPackCount();
//			 * i++) { messageList.add(i, null); } }
//			 */
//			messageList.add(head.getPackIndex() - 1, message);
//			packCount++;
//			if (packCount == head.getPackCount()) {
//				for (int i = 0; i < packCount; i++) {
//					byte[] packMessageBody = messageList.get(i)
//							.getMessageBody().encode();
//					// 消息体除去结束标志位的长度
//					int length = MinaConstants.MAX_BOYD_LENGTH - 1;
//					int totalLength = MessageHeadImpl.HEAD_LENGTH
//							+ MessageType.bodyLengthMap.get(head
//							.getMessageInfo());
//					byte[] body = null;
//					// 如果是最后一个分包,则把填充的空格字符去除掉
//					if (packCount == i + 1) {
//						int lastPackBodyLength = totalLength
//								% MinaConstants.MAX_MESSAFE_LENGTH - 1;
//						byte[] packBodyBytes = new byte[lastPackBodyLength];
//						System.arraycopy(packMessageBody, 0, packBodyBytes, 0, lastPackBodyLength);
//						body = packBodyBytes;
////						body = Arrays.copyOfRange(packMessageBody, 0,lastPackBodyLength);
//						head.setMessageLength(totalLength);
//						System.arraycopy(body, 0, bodyBytes, i * length,
//								lastPackBodyLength);
//
//					} else {
//						body = packMessageBody;
//						byte[] packBodyBytes = new byte[length];
//						System.arraycopy(packMessageBody, 0, packBodyBytes, 0, length);
//						System.arraycopy(packBodyBytes, 0, bodyBytes, i * length, length);
//						//Arrays.copyOfRange(packMessageBody, 0, length)
//					}
//				}
//			} else {
//				return;
//			}
//		}
//		messageList.clear();
//		packCount = 0;
//		MessageBody body = DecoderLib.getInstance()
//				.getDecoder(head.getDecoderInfo()).decode(bodyBytes, 0);
//		head.setPackIndex(1);
//		Message msg = new MessageImpl(head, body);
//		MessageHandler handler = MessageHandlerLib.getInstance().getHandler(
//				msg.getMessageHead().getMessageInfo());
//		handler.execute(msg, session);
//	}

	/**
	 * 根据用户名得到clientId，由username加三位随机数mod5加密而成
	 *
	 * @param userName
	 * @return
	 */
	public static String getClientId(String userName) {
		// 100到999的三位随机数 ，(数据类型)(最小值+Math.random()*(最大值-最小值+1))
		int random = (int) (100 + Math.random() * (999 - 100 + 1));
		MD5 md5 = new MD5();
		return md5.getMD5ofStr(userName + random);
	}
}
