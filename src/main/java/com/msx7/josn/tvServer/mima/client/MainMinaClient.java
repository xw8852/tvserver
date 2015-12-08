package com.msx7.josn.tvServer.mima.client;

import com.msx7.josn.tvServer.mima.MinaConstants;
import com.msx7.josn.tvServer.mima.client.handler.MinaClientHandler;
import com.msx7.josn.tvServer.mima.common.TvProtocalCodecFactory;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * Created by xiaowei on 2015/12/8.
 */
public class MainMinaClient {
    //    private static MainMinaClient mainServer = null;
//
//    public static MainMinaClient getInstances() {
//        if (null == mainServer) {
//            mainServer = new MainMinaClient();
//        }
//        return mainServer;
//    }

   public IoSession session;
    public IoConnector connector;
    public ConnectFuture future;

    public MainMinaClient(String serverAdress) {
        connector = new NioSocketConnector();
        connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TvProtocalCodecFactory(Charset.forName("UTF-8"))));
        connector.setHandler(MinaClientHandler.getInstances());
         future = connector.connect(new InetSocketAddress(serverAdress, MinaConstants.MINA_PORT));
        session = future.getSession();
    }




}
