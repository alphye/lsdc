package com.lishengzn.lsdc.kernel.as.controller;

import com.lishengzn.common.communication.service.WebSocketMsgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
@MessageMapping("/wbskt")
public class WebSocketController {

    private static final Logger LOG = LoggerFactory.getLogger(WebSocketController.class);
    //通过SimpMessagingTemplate向用户发送消息

    @Autowired
    private WebSocketMsgService webSocketMsgService;


    /**
     * 车辆上线
     */
    @MessageMapping("/vehicleOnline")
    public void vehicleOnline( ) throws IOException {
        webSocketMsgService.vehicleOnline();
    }

    /**
     * 车辆上线
     */
    @MessageMapping("/vehicleOffline")
    public void vehicleOffline( ) throws IOException {
        webSocketMsgService.vehicleOffline();
    }


}
