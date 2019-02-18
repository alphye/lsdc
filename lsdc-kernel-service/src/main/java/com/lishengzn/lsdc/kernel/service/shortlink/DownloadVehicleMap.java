package com.lishengzn.lsdc.kernel.service.shortlink;

import com.alibaba.fastjson.JSONObject;
import com.lishengzn.common.constants.SocketConstants;
import com.lishengzn.common.entity.request.config.RequestDownloadMap;
import com.lishengzn.common.exception.SimpleException;
import com.lishengzn.common.packet.PacketModel;
import com.lishengzn.common.pool.ObjectPool;
import com.lishengzn.common.service.VehiclePoolService;
import com.lishengzn.common.socket.ClientOfVehicle;
import com.lishengzn.common.socket.ShortLinkClient;
import com.lishengzn.common.util.SocketUtil;
import com.lishengzn.lsdc.kernel.service.object.impl.VehiclePoolServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import java.io.*;
import java.net.Socket;

public class DownloadVehicleMap {
    private static final Logger LOG = LoggerFactory.getLogger(DownloadVehicleMap.class);
    private static VehiclePoolService vehiclePoolService;
    static {
        WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
        vehiclePoolService =wac.getBean(VehiclePoolServiceImpl.class);
    }
    public synchronized static void loadMap(){
        String ip = vehiclePoolService.getVehicleIps().get(0);
        ShortLinkClient client =new ShortLinkClient(ip, SocketConstants.SERVER_PORT_VEHICLE_CONFIG, ClientOfVehicle.ClientType.vehicleConfig,vehiclePoolService.getVehicle(ip));
        client.runShortLinkClient((socket)->sendRequest(socket),(socket)->receiveResponse(socket));
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
                    LOG.info("下载到地图,length:{},{}",packetModel.getData().length(),packetModel.getData());
                    saveMapData(packetModel.getData());
                    return;
                }
            }
            throw new SimpleException("从小车下载地图失败！");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private static void saveMapData(String mapData){
        String path = DownloadVehicleMap.class.getClassLoader().getResource("").getPath();
        BufferedOutputStream bos=null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(new File(path,"map.smap")));
            byte map_byte[] = mapData.getBytes();
            bos.write(map_byte);
            bos.flush();
        } catch (IOException e) {
            LOG.error("保存地图异常：",e);
        }finally{
            if(bos !=null){
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}