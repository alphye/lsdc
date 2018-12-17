package com.lishengzn.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.lishengzn.constant.TopicAddressConstants;
import com.lishengzn.dto.CommandDto;
import com.lishengzn.service.WebSocketMsgService;
import com.lishengzn.socket.Client;
import com.lishengzn.util.CacheManager;
import com.lishengzn.websocket.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@WebSocketService
@Scope("singleton")
public class WebSocketMsgServiceImpl implements WebSocketMsgService {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Value("${vehicleips}")
    private String vehicleips;

    @Value("${FullDispatchTask_tSleep}")
    private String fullDispatchTask_tSleep;

    private ExecutorService threadPool = Executors.newCachedThreadPool();
    @Override
    public void sendToAll(String address, String json){
        if (!StringUtils.isEmpty(json) ) {
            //第1个参数是浏览器订阅的地址，第2个是消息本身
            messagingTemplate.convertAndSend(address,json);
        }
    }

    @Override
    public void vehicleOnline() throws IOException {
        if(CacheManager.cache.get(CacheManager.clientPoolKey)==null){
            CacheManager.cache.put(CacheManager.clientPoolKey, new ConcurrentHashMap<String,Client>());
        }
        // 先把现有的小车都线
        vehicleOffline();
        String[] ipArray = vehicleips.split(",");
        for(int i =0;i<ipArray.length;i++){
            String ip=ipArray[i].split(":")[0];
            int port=Integer.valueOf(ipArray[i].split(":")[1]);
            new Client(ip,port, this);
            CommandDto command = new CommandDto(CommandDto.TYPE.setVehicleInIp.name(),ip);
            sendToAll(TopicAddressConstants.setVehicleInIp,JSONObject.toJSONString(command));
        }
    }

    @Override
    public void vehicleOffline(){
        Map<String,Client> map =(Map<String,Client>) CacheManager.cache.get(CacheManager.clientPoolKey);
        Set<Map.Entry<String,Client>> entrySet = map.entrySet();
        entrySet.forEach((e)->vehicleOffLine(e.getKey()));
    }

    private void vehicleOffLine(String ip){
        Client client =((Map<String,Client>) CacheManager.cache.get(CacheManager.clientPoolKey)).remove(ip);
        client.close();
    }

}
