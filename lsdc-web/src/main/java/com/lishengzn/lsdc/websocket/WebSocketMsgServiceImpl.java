package com.lishengzn.lsdc.websocket;

import com.lishengzn.common.communication.service.WebSocketMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.util.StringUtils;

import java.io.IOException;

@WebSocketService
@Scope("singleton")
public class WebSocketMsgServiceImpl implements WebSocketMsgService {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Override
    public void sendToAll(String address, String json){
        if (!StringUtils.isEmpty(json) ) {
            //第1个参数是浏览器订阅的地址，第2个是消息本身
            messagingTemplate.convertAndSend(address,json);
        }
    }

    @Override
    public void vehicleOnline() throws IOException {

    }

    @Override
    public void vehicleOffline() {

    }

}
