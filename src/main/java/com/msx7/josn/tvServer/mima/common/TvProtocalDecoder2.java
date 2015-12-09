package com.msx7.josn.tvServer.mima.common;

import android.util.Log;

import com.msx7.josn.tvServer.mima.MinaConstants;
import com.msx7.josn.tvServer.pack.message.Message;
import com.msx7.josn.tvServer.pack.message.MessageHead;
import com.msx7.josn.tvServer.pack.message.impl.MessageHeadImpl;
import com.msx7.josn.tvServer.pack.message.impl.MessageImpl;
import com.msx7.josn.tvServer.pack.message.impl.body.PackBody;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

/**
 * Created by xiaowei on 2015/12/9.
 */
public class TvProtocalDecoder2 extends CumulativeProtocolDecoder {
    @Override
    protected boolean doDecode(IoSession ioSession, IoBuffer ioBuffer, ProtocolDecoderOutput protocolDecoderOutput) throws Exception {
        final int packHeadLength = MessageHead.HEAD_LENGTH;
//        if (MinaConstants.MESSASGE_START_FLAG != ioBuffer.get(0)) {
////            Log.d("way", "bytes:" + Arrays.toString(ioBuffer.array()));
////            ioBuffer.clear();
//            throw new Exception("消息头错误");
//        }
        ioBuffer.mark();
//        printHexString(ioBuffer.array());
        ioBuffer.reset();
        while (ioBuffer.remaining() >= MessageHead.HEAD_LENGTH) {
            ioBuffer.mark();
            if (MinaConstants.MESSASGE_START_FLAG == ioBuffer.get())
                break;
            Log.d("way", "消息头错误");
        }
        if (ioBuffer.markValue() >= 0)
            ioBuffer.reset();
        if (ioBuffer.remaining() >= packHeadLength) {
            MessageHead head = null;
            ioBuffer.mark();
            byte[] bytes = new byte[MessageHead.HEAD_LENGTH];
            ioBuffer.get(bytes, 0, MessageHead.HEAD_LENGTH);
//            printHexString(bytes);
            head = new MessageHeadImpl(bytes);
            int packLength = head.getLength();
            if (packHeadLength - MessageHead.HEAD_LENGTH > ioBuffer.remaining()) {
                ioBuffer.reset();
            } else {
                bytes = new byte[packLength - MessageHead.HEAD_LENGTH];
                ioBuffer.get(bytes, 0, bytes.length);
//                printHexString(bytes);
                PackBody packBody = new PackBody(bytes);
                Message message = new MessageImpl(head, packBody);
                protocolDecoderOutput.write(message);
                if (ioBuffer.remaining() > 0)
                    return true;
            }
        }

        return false;
    }

    public static void printHexString(byte[] b) {

        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            buffer.append(hex.toUpperCase() + " ");
            if (buffer.length() >= 50) {
                Log.d("BBBBB", buffer.toString());
                buffer = new StringBuffer();
            }
        }
        Log.d("BBBBB", buffer.toString());
    }
}
