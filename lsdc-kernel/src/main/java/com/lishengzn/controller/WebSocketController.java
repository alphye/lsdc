package com.lishengzn.controller;

import com.alibaba.fastjson.JSONObject;
import com.lishengzn.constant.TopicAddressConstants;
import com.lishengzn.dto.CommandDto;
import com.lishengzn.dto.OperationCommandDto;
import com.lishengzn.dto.SimContinousTasks;
import com.lishengzn.dto.SimProCommandDto;
import com.lishengzn.exception.SimpleException;
import com.lishengzn.service.WebSocketMsgService;
import com.lishengzn.socket.Client;
import com.lishengzn.util.CacheManager;
import com.lishengzn.util.LSConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@Controller
@MessageMapping("/wbskt")
public class WebSocketController {

    private static final Logger LOG = LoggerFactory.getLogger(WebSocketController.class);
    //通过SimpMessagingTemplate向用户发送消息

    @Autowired
    private WebSocketMsgService webSocketMsgService;



    @MessageMapping("/sendChatMsg")
    public void sendChatMsg( String json)throws Exception{
        if (!StringUtils.isEmpty(json) ) {
            String chatAddress = "/queue/contactMessage";
//            messagingTemplate.convertAndSend(chatAddress,po.getContent());
        }
    }



    @MessageMapping("/sendSimpProCmd")
    public void sendSimpProToServer(CommandDto commandDto){
        LOG.info("收到简单协议指令：{}", JSONObject.toJSONString(commandDto));
        SimProCommandDto simProCommandDto = JSONObject.parseObject(JSONObject.toJSONString(commandDto.getMessage()),SimProCommandDto.class);
        Client client = getClientByIp(simProCommandDto.getVehicleIp());
        client.sendSimProToServer(simProCommandDto.getParam());
        if(",01,02,13,".indexOf(","+simProCommandDto.getParam().substring(0,2)+",")>=0){
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
            }
            client.sendSimProToServer("0900");
        }
    }
    @MessageMapping("/sendContinousSimProCmd")
    public void sendContinuousSimpProCmd(SimContinousTasks tasks){
    /*    LOG.info("收到简单协议指令---连续导航：{}", JSONObject.toJSONString(tasks));
        List<String> params=new ArrayList<>();
        tasks.getTasks().forEach((task)->{
            params.add(task.getDestPoint());
            params.add(task.getDestOpt());
        });
        Client client = getClientByIp(tasks.getVehicleIp());
        client.sendContinuousSimpProCmd(params);*/
    }
    @MessageExceptionHandler
    public  void handleExceptions(Exception e) {
        CommandDto command = new CommandDto();
        command.setCommandType(CommandDto.TYPE.error.name());
        if(e instanceof SimpleException){
            LOG.error("",e.getMessage());
            command.setMessage(e.getMessage());
        }else{
            LOG.error("",e);
            command.setMessage("系统错误");
        }
        webSocketMsgService.sendToAll(TopicAddressConstants.error,JSONObject.toJSONString(command));
    }

    /**
     * 车辆上线
     */
    @MessageMapping("/vehicleOnline")
    public void vehicleOnline( ) throws IOException {
        webSocketMsgService.vehicleOnline();
    }
    /**
     * 车辆下线
     */
    @MessageMapping("/vehicleOffline")
    public void vehicleOffline( ) throws IOException {
        webSocketMsgService.vehicleOffline();
    }
    private Client getClientByIp(String vehicleIp){
        Map<String,Client> clientMap22 =(Map<String,Client>)CacheManager.cache.get(CacheManager.clientPoolKey);
        if(com.alibaba.druid.util.StringUtils.isEmpty(vehicleIp)){
            throw new SimpleException("未指定车辆！");
        }
        if (clientMap22 ==null) {
            throw new SimpleException("车辆尚未上线！");
        }
        Client client = clientMap22.get(vehicleIp);
        if (client ==null) {
            throw new SimpleException("车辆尚未上线！");
        }
        return client;
    }
}
