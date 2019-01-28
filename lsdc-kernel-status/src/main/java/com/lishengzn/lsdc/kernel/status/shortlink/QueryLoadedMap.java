package com.lishengzn.lsdc.kernel.status.shortlink;

import com.alibaba.fastjson.JSONObject;
import com.lishengzn.common.constants.SocketConstants;
import com.lishengzn.common.entity.response.statusapi.ResponseCurrMap;
import com.lishengzn.common.exception.SimpleException;
import com.lishengzn.common.packet.PacketModel;
import com.lishengzn.common.pool.ObjectPool;
import com.lishengzn.common.socket.ClientOfVehicle;
import com.lishengzn.common.socket.ShortLinkClient;
import com.lishengzn.common.util.SocketUtil;
import com.lishengzn.lsdc.kernel.status.communication.service.impl.StatusAPISenderServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Socket;

public class QueryLoadedMap {
    public static volatile  String currLoadedMap;
    private static final Logger LOG = LoggerFactory.getLogger(QueryLoadedMap.class);
    public synchronized static String queryMap(){
        currLoadedMap ="";
        String ip = ObjectPool.getVehicleIps().get(0);
        ShortLinkClient client =new ShortLinkClient(ip, SocketConstants.SERVER_PORT_STATUS_API, ClientOfVehicle.ClientType.statusAPi,ObjectPool.getVehicle(ip));
        client.runShortLink((socket)->sendRequest(socket),(socket)->receiveResponse(socket));
        return currLoadedMap;
    }

    private static void sendRequest(Socket socket){
        StatusAPISenderServiceImpl messageSenderService = new StatusAPISenderServiceImpl();
        messageSenderService.setSocket(socket);
        messageSenderService.initialize();
        messageSenderService.queryMapInfo();
    }
    private static void receiveResponse(Socket socket){
        PacketModel packetModel = null;
        try {
            if((packetModel= SocketUtil.readNextPacketData(socket.getInputStream()))!=null){
                if((packetModel.getPacketType()-SocketConstants.RESPONSE_PACKET_ADDED)==SocketConstants.PACKET_TYPE_STATUS_API_MAP){
                    ResponseCurrMap mapInfo = JSONObject.parseObject(packetModel.getData(), ResponseCurrMap.class);
                    LOG.debug("收到小车地图存储信息：{}",JSONObject.toJSONString(mapInfo));
                    QueryLoadedMap.currLoadedMap=mapInfo.getCurrent_map();
                    return;
                }
            }
            throw new SimpleException("查询小车当前地图失败！");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
