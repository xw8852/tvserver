package com.msx7.josn.tvServer.mima.common;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

import java.nio.charset.Charset;

/**
 * Created by xiaowei on 2015/12/8.
 */
public class TvProtocalCodecFactory implements ProtocolCodecFactory{
    private final TvProtocalEncoder encoder;
    private final TvProtocalDecoder decoder;

    public TvProtocalCodecFactory(Charset charset) {
        encoder = new TvProtocalEncoder(charset);
        decoder = new TvProtocalDecoder(charset);
    }
    @Override
    public ProtocolEncoder getEncoder(IoSession ioSession) throws Exception {
        return encoder;
    }

    @Override
    public ProtocolDecoder getDecoder(IoSession ioSession) throws Exception {
        return decoder;
    }
}
