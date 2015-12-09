package com.msx7.josn.tvServer.mima.server;

import com.msx7.josn.tvServer.mima.MinaConstants;
import com.msx7.josn.tvServer.mima.common.TvProtocalCodecFactory;
import com.msx7.josn.tvServer.mima.server.handler.ServerMinaHandler;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.logging.LogLevel;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.concurrent.Executors;

/**
 * Created by xiaowei on 2015/12/8.
 */
public class MainMinaServer {
    private static MainMinaServer mainServer = null;
    private SocketAcceptor acceptor = new NioSocketAcceptor(MinaConstants.PROCESSOR_COUNT);
    private DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();
    private int bindPort = MinaConstants.MINA_PORT;

    public static MainMinaServer getInstances() {
        if (null == mainServer) {
            mainServer = new MainMinaServer();
        }
        return mainServer;
    }

    private MainMinaServer() {
        // 定义SLF4J 日志级别
        LoggingFilter loggingFilter = new LoggingFilter();
        loggingFilter.setSessionCreatedLogLevel(LogLevel.NONE);// 一个新的session被创建时触发
        loggingFilter.setSessionOpenedLogLevel(LogLevel.NONE);// 一个新的session打开时触发
        loggingFilter.setSessionClosedLogLevel(LogLevel.NONE);// 一个session被关闭时触发
        loggingFilter.setMessageReceivedLogLevel(LogLevel.NONE);// 接收到数据时触发
        loggingFilter.setMessageSentLogLevel(LogLevel.NONE);// 数据被发送后触发
        loggingFilter.setSessionIdleLogLevel(LogLevel.INFO);// 一个session空闲了一定时间后触发
        loggingFilter.setExceptionCaughtLogLevel(LogLevel.INFO);// 当有异常抛出时触发
        chain.addLast("log", loggingFilter);
        chain.addLast("codec", new ProtocolCodecFilter(new TvProtocalCodecFactory(Charset.forName("UTF-8"))));
        chain.addLast("threadPool", new ExecutorFilter(Executors.newCachedThreadPool()));
        acceptor.getSessionConfig().setReadBufferSize(1024*500);
        acceptor.setHandler(ServerMinaHandler.getInstances());
        this.acceptor.setReuseAddress(true);//加上这句话，避免重启时提示地址被占用
        try {
            acceptor.bind(new InetSocketAddress(bindPort));
        } catch (IOException e) {
            e.printStackTrace();
        }
        acceptor.setCloseOnDeactivation(true);
    }

    public void close() {
        acceptor.dispose();
        mainServer = null;
    }

    public SocketAcceptor getAcceptor() {
        return acceptor;
    }

    public void setAcceptor(SocketAcceptor acceptor) {
        this.acceptor = acceptor;
    }
}
