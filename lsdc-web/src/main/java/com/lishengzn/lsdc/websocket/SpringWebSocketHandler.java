package com.lishengzn.lsdc.websocket;


import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.*;

@Service
public class SpringWebSocketHandler implements WebSocketHandler {
    private static Logger LOG = Logger.getLogger(SpringWebSocketHandler.class);
    private static Map<String, WebSocketSession> users = new ConcurrentHashMap<>();

    /**
     * 连接成功时候，会触发页面上onopen方法
     */
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        users.put(session.getId(), session);
        LOG.debug("connect to the websocket success......当前数量:"+users.size());
        //这块会实现自己业务，比如，当用户登录后，会把离线消息推送给用户
        //TextMessage returnMessage = new TextMessage("你将收到的离线");
        //session.sendMessage(returnMessage);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        LOG.debug(session + "---->" + message + ":"+ message.getPayload().toString());
        session.sendMessage(message);
    }

    /**
     * 关闭连接时触发
     */
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) {
        LOG.debug("websocket connection closed......");
        String username= (String) session.getAttributes().get("WEBSOCKET_USERNAME");
        LOG.debug("用户"+username+"已退出！");
        users.remove(session.getId());
        LOG.debug("剩余在线用户"+users.size());
    }


    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if(session.isOpen()){session.close();}
        LOG.debug("websocket connection closed......");
        users.remove(session.getId());
    }

    public boolean supportsPartialMessages() {
        return false;
    }


    /**
     * 给某个用户发送消息
     *
     * @param userName
     * @param message
     */
    public void sendMessageToUser(String userName, TextMessage message) {
        Set<Map.Entry<String, WebSocketSession>> entrySet = users.entrySet();
        for (Map.Entry<String, WebSocketSession> entry : entrySet) {
            WebSocketSession user = entry.getValue();
            if (user.getAttributes().get("WEBSOCKET_USERNAME").equals(userName)) {
                try {
                    if (user.isOpen()) {
                        user.sendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    /**
     * 给所有在线用户发送消息
     *
     * @param message
     */
    public void sendMessageToUsers(TextMessage message) {
        Set<Map.Entry<String, WebSocketSession>> entrySet = users.entrySet();
        for (Map.Entry<String, WebSocketSession> entry : entrySet) {
            WebSocketSession user = entry.getValue();
            try {
                if (user.isOpen()) {
                    user.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
