package com.msx7.josn.tvServer.pack;

import org.apache.mina.handler.demux.MessageHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiaowei on 2015/12/8.
 */
public final class MessageHandlerLib {
    private static final MessageHandlerLib INSTANCE = new MessageHandlerLib();

    /**
     * 消息处理器集合.
     */
    private Map<String, MessageHandler> handlers = null;
    private MessageHandler handler;

    private MessageHandlerLib() {
        handlers = new HashMap<String, MessageHandler>();
        addHandlers();
    }

    public static MessageHandlerLib getInstance() {
        return INSTANCE;
    }

    /**
     * 添加所有消息处理器.
     */
    private void addHandlers() {
        //pad所需要的handler
//        handlers.put(String.valueOf(MessageType.LOGIN)
//                + MessageHead.REQUEST_FLAG, new LoginRequestHandler());
//        handlers.put(String.valueOf(MessageType.LOGOUT)
//                + MessageHead.REQUEST_FLAG, new LogoutRequestHandler());
//        handlers.put(String.valueOf(MessageType.TEST)
//                + MessageHead.REQUEST_FLAG, new LinkTestRequestHandler());
//        handlers.put(String.valueOf(MessageType.BULLETIN)
//                + MessageHead.RESPONSE_FLAG, new BulletinResponseHandler());
//        handlers.put(String.valueOf(MessageType.UPGRADE)
//                + MessageHead.REQUEST_FLAG, new UpgradeRequestHandler());
//        //服务端的handler
//        handlers.put(String.valueOf(MessageType.LOGIN)
//                + MessageHead.RESPONSE_FLAG, new LoginResponseHandler());
//        handlers.put(String.valueOf(MessageType.LOGOUT)
//                + MessageHead.RESPONSE_FLAG, new LogoutResponseHandler());
//        handlers.put(String.valueOf(MessageType.TEST)
//                + MessageHead.RESPONSE_FLAG, new LinkTestResponseHandler());
//        handlers.put(String.valueOf(MessageType.BULLETIN)
//                + MessageHead.REQUEST_FLAG, new BulletinRequestHandler());
//        handlers.put(String.valueOf(MessageType.UPGRADE)
//                + MessageHead.RESPONSE_FLAG, new UpgradeResponseHandler());
    }

    /**
     * 添加单个消息处理器
     *
     * @param messageInfo the message info
     * @param decoder     the handler
     */
    public void addHandler(String messageInfo, MessageHandler decoder) {
//        handlers.put(messageInfo, decoder);
        this.handler = decoder;
    }

    /**
     * 根据消息信息获取指定消息处理器.
     *
     * @param msgInfo 消息信息
     * @return 消息处理器
     */
    public MessageHandler getHandler(String msgInfo) {
//        return handlers.get(msgInfo);
        return handler;
    }
}
