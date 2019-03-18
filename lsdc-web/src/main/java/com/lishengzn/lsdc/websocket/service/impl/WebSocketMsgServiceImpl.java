package com.lishengzn.lsdc.websocket.service.impl;

import com.lishengzn.lsdc.websocket.SpringWebSocketHandler;
import com.lishengzn.lsdc.websocket.service.WebSocketMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.TextMessage;

@Service
@Scope("singleton")
public class WebSocketMsgServiceImpl implements WebSocketMsgService {

    @Autowired
    private SpringWebSocketHandler springWebSocketHandler;

    @Override
    public void sendToAll(String json){
        if (!StringUtils.isEmpty(json) ) {
            springWebSocketHandler.sendMessageToUsers(new TextMessage(json));
        }
    }

    @Override
    public void vehicleOnline()  {

    }

    @Override
    public void vehicleOffline() {

    }

}
