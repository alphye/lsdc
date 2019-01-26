package com.lishengzn.lsdc.kernel.as.shortlink;

import com.alibaba.fastjson.JSONObject;
import com.lishengzn.common.constants.SocketConstants;
import com.lishengzn.common.entity.request.config.RequestDownloadMap;
import com.lishengzn.common.exception.SimpleException;
import com.lishengzn.common.packet.PacketModel;
import com.lishengzn.common.pool.ObjectPool;
import com.lishengzn.common.socket.ClientOfVehicle;
import com.lishengzn.common.socket.ShortLinkClient;
import com.lishengzn.common.util.SocketUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Socket;

public class LoadVehicleMap {
    private static final Logger LOG = LoggerFactory.getLogger(LoadVehicleMap.class);
    public synchronized static void loadMap(){
        String ip = ObjectPool.getVehicleIps().get(0);
        ShortLinkClient client =new ShortLinkClient(ip, SocketConstants.SERVER_PORT_VEHICLE_CONFIG, ClientOfVehicle.ClientType.vehicleConfig,ObjectPool.getVehicle(ip));
        client.runShortLink((socket)->sendRequest(socket),(socket)->receiveResponse(socket));
    }

    private static void sendRequest(Socket socket){
        String map_name = QueryLoadedMap.queryMap();
        RequestDownloadMap downloadMap = new RequestDownloadMap();
        downloadMap.setMap_name(map_name);
        PacketModel packetModel = new PacketModel(SocketConstants.PACKET_TYPE_OTHER_DOWNLOAD_MAP, JSONObject.toJSONString(downloadMap));
        try {
            SocketUtil.sendPacketData(socket.getOutputStream(),packetModel);
        } catch (IOException e) {
            throw new SimpleException("向小车发送信息失败，请检查与小车的连接！");
        }
    }
    private static void receiveResponse(Socket socket){
        PacketModel packetModel = null;
        try {
            if((packetModel=SocketUtil.readNextPacketData(socket.getInputStream()))!=null){
                if((packetModel.getPacketType()-SocketConstants.RESPONSE_PACKET_ADDED)==SocketConstants.PACKET_TYPE_OTHER_DOWNLOAD_MAP){
                    LOG.info("下载到地图：{}",packetModel.getData());
                    return;
                }
            }
            throw new SimpleException("从小车下载地图失败！");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
