package com.msx7.josn.tvServer.mima.common;

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
        if (MinaConstants.MESSASGE_START_FLAG != ioBuffer.get(0)) {
//            Log.d("way", "bytes:" + Arrays.toString(ioBuffer.array()));
//            ioBuffer.clear();
            throw new Exception("消息头错误");
        }
        if (ioBuffer.remaining() >= packHeadLength) {
            MessageHead head = null;
            ioBuffer.mark();
            byte[] bytes = new byte[MessageHead.HEAD_LENGTH];
            ioBuffer.get(bytes, 0, MessageHead.HEAD_LENGTH);
            head = new MessageHeadImpl(bytes);
            int packLength = head.getLength();
            if (packHeadLength - MessageHead.HEAD_LENGTH > ioBuffer.remaining()) {
                ioBuffer.reset();
            } else {
                bytes = new byte[packLength - MessageHead.HEAD_LENGTH];
                ioBuffer.get(bytes, 0, bytes.length);
                PackBody packBody = new PackBody(bytes);
                Message message = new MessageImpl(head, packBody);
                protocolDecoderOutput.write(message);
                if (ioBuffer.remaining() > 0)
                    return true;
            }
        }

        return false;
    }
}
