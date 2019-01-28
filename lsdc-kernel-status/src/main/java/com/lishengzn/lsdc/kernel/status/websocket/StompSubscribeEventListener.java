package com.lishengzn.lsdc.kernel.status.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

/**
 * 监听订阅地址的用户
 */
@WebSocketService
public class StompSubscribeEventListener implements ApplicationListener<SessionSubscribeEvent> {

    private static final Logger logger = LoggerFactory.getLogger(StompSubscribeEventListener.class);

    @Override
    public void onApplicationEvent(SessionSubscribeEvent sessionSubscribeEvent) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(sessionSubscribeEvent.getMessage());
        //这里的sessionId对应HttpSessionIdHandshakeInterceptor拦截器的存放key
        // String sessionId = headerAccessor.getSessionAttributes().get(Constants.SESSIONID).toString();
        logger.info("stomp Subscribe : "+headerAccessor.getMessageHeaders()  );
    }
}

