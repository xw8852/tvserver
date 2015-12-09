package com.msx7.josn.tvServer.mima.common.util;


import android.util.Log;

import com.msx7.josn.tvServer.mima.MinaConstants;
import com.msx7.josn.tvServer.pack.MessageHandlerLib;
import com.msx7.josn.tvServer.pack.message.Message;
import com.msx7.josn.tvServer.pack.message.MessageBody;
import com.msx7.josn.tvServer.pack.message.MessageHandler;
import com.msx7.josn.tvServer.pack.message.MessageHead;
import com.msx7.josn.tvServer.pack.message.impl.MessageHeadImpl;
import com.msx7.josn.tvServer.pack.message.impl.MessageImpl;
import com.msx7.josn.tvServer.pack.message.impl.body.PackBody;
import com.msx7.josn.tvServer.pack.message.impl.body.StringMessageBody;

import org.apache.mina.core.session.IoSession;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

    public static synchronized List<Message> messageClip(Message message) {
        List<Message> msgs = new ArrayList<Message>();
        if (message.getMessageHead().getLength() > MinaConstants.MAX_MESSAFE_LENGTH) {

            MessageHead msgHead = message.getMessageHead();
            byte[] body = message.getMessageBody().encode();
            int bodyLength = MinaConstants.MAX_BOYD_LENGTH - MessageBody.MSG_END_FLAG_WIDTH;
            int packCount = (message.getMessageBody().getBodyLength() - 1) / bodyLength;
            if (packCount * MinaConstants.MAX_BOYD_LENGTH < message.getMessageBody().getBodyLength())
                packCount++;
            int i = 0;
            while (i < packCount) {
                MessageHeadImpl head = new MessageHeadImpl(msgHead.getDeviceCode(), msgHead.getLength(), msgHead.getActionCode(), msgHead.getId(), packCount, i + 1);
                int start = i * bodyLength;
                int end = (i + 1) * bodyLength;
                if (end >= message.getMessageBody().getBodyLength() - 1) {
                    end = message.getMessageBody().getBodyLength() - 1;
                }
                byte[] bytes = new byte[end - start];
                System.arraycopy(body, start, bytes, 0, end - start);
                StringMessageBody msgBody = new StringMessageBody(bytes);
                head.setLength(MessageHead.HEAD_LENGTH + msgBody.getBodyLength());
                msgs.add(new MessageImpl(head, msgBody));
                i++;
            }
        } else
            msgs.add(message);
        return msgs;
    }

    /**
     * 分包消息处理器
     *
     * @param message
     * @param session
     */
    public static synchronized void messageRecevie(Message message,
                                                   IoSession session) {
        MessageHead head = message.getMessageHead();
//		int messageLength = MessageHeadImpl.HEAD_LENGTH
//				+ MessageType.bodyLengthMap.get(head.getMessageInfo());
//		byte[] bodyBytes = new byte[messageLength - MessageHead.HEAD_LENGTH];
        byte[] bodyBytes = new byte[head.getLength()];
        if (head.getPackCount() == 1) {
            bodyBytes = message.getMessageBody().encode();
        } else {
            /*
             * // 初始化messageList if (null == messageList) { messageList = new
			 * ArrayList<Message>(); for (int i = 0; i < head.getPackCount();
			 * i++) { messageList.add(i, null); } }
			 */
            messageList.add(message);
            packCount++;
            byte[] _body = new byte[0];
            if (packCount == head.getPackCount()) {
                Collections.sort(messageList, new Comparator<Message>() {
                    @Override
                    public int compare(Message lhs, Message rhs) {
                        return lhs.getMessageHead().getPackIndex() - rhs.getMessageHead().getPackIndex();
                    }
                });
                for (int i = 0; i < packCount; i++) {
                    Log.d("way", "index---" + messageList.get(i).getMessageHead().getPackIndex());
                    byte[] packMessageBody = messageList.get(i).getMessageBody().encode();
                    // 消息体除去结束标志位的长度
                    int length = MinaConstants.MAX_BOYD_LENGTH - 1;
                    //需要把包合并，才得知总包的长度
                    int totalLength = MessageHeadImpl.HEAD_LENGTH + messageList.get(i).getMessageBody().getBodyLength();
                    byte[] body = null;
                    // 如果是最后一个分包
                    if (packCount == i + 1) {
                        length = packMessageBody.length;
                        int _len = _body.length + length;
                        byte[] _body_1 = new byte[_len];
                        System.arraycopy(_body, 0, _body_1, 0, _body.length);
                        System.arraycopy(packMessageBody, 0, _body_1, _body.length, length);
                        bodyBytes = _body_1;
                        head.setLength(MessageHead.HEAD_LENGTH + bodyBytes.length);
                    } else {
                        /**
                         * 1、计算将当前包合并后，body长度的大小，并生成新的body
                         * 2、老的body copy 到新的body
                         * 3、新的包 copy到新的body
                         */
                        int _len = _body.length + length;
                        byte[] _body_1 = new byte[_len];
                        System.arraycopy(_body, 0, _body_1, 0, _body.length);
                        System.arraycopy(packMessageBody, 0, _body_1, _body.length, length);
                        _body = _body_1;

                    }
                }
            } else {
                return;
            }
        }
        messageList.clear();
        packCount = 0;
        //TODO:解包，并把message发送出去
//		MessageBody body = DecoderLib.getInstance()
//				.getDecoder(head.getDecoderInfo()).decode(bodyBytes, 0);
//		head.setPackIndex(1);
        Message msg = new MessageImpl(head, new PackBody(bodyBytes));
        MessageHandler handler = MessageHandlerLib.getInstance().getHandler("");
        handler.handleMessage(session, msg);
    }

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
