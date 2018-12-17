package com.lishengzn.controller;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.lishengzn.constant.TopicAddressConstants;
import com.lishengzn.dto.CommandDto;
import com.lishengzn.dto.SimContinousTasks;
import com.lishengzn.dto.SimProCommandDto;
import com.lishengzn.exception.SimpleException;
import com.lishengzn.service.WebSocketMsgService;
import com.lishengzn.socket.Client;
import com.lishengzn.util.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("simpPro")
public class SimProCmdController {
    private static final Logger LOG = LoggerFactory.getLogger(SimProCmdController.class);

    @Autowired
    private WebSocketMsgService webSocketMsgService;

    @RequestMapping(value = "sendContinuousSimpProCmd",method = RequestMethod.POST)
    @ResponseBody
    public void sendContinuousSimpProCmd(SimContinousTasks tasks){
        LOG.info("收到简单协议指令---连续导航：{}", JSONObject.toJSONString(tasks));
        doSendContinuousSimpProCmd(tasks,false);
    }
    @RequestMapping(value = "sendCyclicContinuousSimpProCmd",method = RequestMethod.POST)
    @ResponseBody
    public void sendCyclicContinuousSimpProCmd(SimContinousTasks tasks){
        LOG.info("收到简单协议指令---循环任务---连续导航：{}", JSONObject.toJSONString(tasks));
        doSendContinuousSimpProCmd(tasks,true);
    }
    private void doSendContinuousSimpProCmd(SimContinousTasks tasks ,boolean cyclicExecute){
        List<String> params=new ArrayList<>();
        tasks.getTasks().forEach((task)->{
            params.add(task.getDestPoint());
            if(!"-1".equals(task.getDestOpt())){
                params.add(task.getDestOpt());
            }
        });
        Client client = getClientByIp(tasks.getTasks().get(0).getVehicleIp());
        if(client!=null){
            try {
                client.sendContinuousSimpProCmd(params,cyclicExecute);
            }catch (SimpleException e){
                CommandDto command = new CommandDto();
                command.setCommandType(CommandDto.TYPE.error.name());
                command.setMessage(e.getMessage());
                webSocketMsgService.sendToAll(TopicAddressConstants.error,JSONObject.toJSONString(command));
            }

        }
    }
    @RequestMapping(value = "cancleCyclicTask",method = RequestMethod.POST,consumes= MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String cancleCyclicTask(@RequestBody SimProCommandDto simProCommandDto){
        LOG.info("收到取消循环任务指令：{}", JSONObject.toJSONString(simProCommandDto));
        Client client = getClientByIp(simProCommandDto.getVehicleIp());
        if(client!=null){
            client.cancleCyclicTask();
            return "success";
        }
        return null;
    }

    private Client getClientByIp(String vehicleIp){
        Map<String,Client> clientMap22 =(Map<String,Client>) CacheManager.cache.get(CacheManager.clientPoolKey);
        Client client=null;
        CommandDto command =    new CommandDto();
        try {
            if(StringUtils.isEmpty(vehicleIp)){
                throw new SimpleException("未指定车辆！");
            }
            if (clientMap22 ==null) {
                throw new SimpleException("车辆尚未上线！");
            }
            client = clientMap22.get(vehicleIp);
            if (client ==null) {
                throw new SimpleException("车辆尚未上线！");
            }
        }catch (SimpleException e){
            command.setCommandType(CommandDto.TYPE.error.name());
            command.setMessage(e.getMessage());
            webSocketMsgService.sendToAll(TopicAddressConstants.error,JSONObject.toJSONString(command));
        }

        return client;
    }
}
