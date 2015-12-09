package com.msx7.josn.tvServer.mima.common;

import android.util.Log;

import com.msx7.josn.tvServer.mima.MinaConstants;
import com.msx7.josn.tvServer.pack.message.Message;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import java.nio.charset.Charset;

/**
 * Created by xiaowei on 2015/12/8.
 */
public class TvProtocalEncoder extends ProtocolEncoderAdapter {

    @SuppressWarnings("unused")
    private final Charset charset;

    public TvProtocalEncoder(Charset charset) {
        this.charset = charset;
    }

    // 在此处实现对Message的编码工作，并把它写入输出流中
    public void encode(IoSession session, Object object,
                       ProtocolEncoderOutput out) throws Exception {
        Message message = (Message) object;
        // MessageHead head = message.getMessageHead();
        byte[] headBytes = message.getMessageHead().encode();
        byte[] bodyBytes = message.getMessageBody().encode();
        Log.d("way", "count  " + message.getMessageHead().getLength() + "," + message.getMessageBody().getBodyLength());
        IoBuffer buf = IoBuffer.allocate(MinaConstants.MAX_MESSAFE_LENGTH).setAutoExpand(false);
        buf.put(headBytes);
        buf.put(bodyBytes);
        buf.flip();
        out.write(buf);
        out.flush();
        // }

    }

    public void dispose() throws Exception {
    }
}
