package com.lishengzn.lsdc.strategies.shortlink;

import com.alibaba.fastjson.JSONObject;
import com.lishengzn.common.constants.SocketConstants;
import com.lishengzn.common.entity.request.config.RequestDownloadMap;
import com.lishengzn.common.entity.response.map.Smap;
import com.lishengzn.common.exception.SimpleException;
import com.lishengzn.common.packet.PacketModel;
import com.lishengzn.common.socket.ClientOfVehicle;
import com.lishengzn.common.socket.ShortLinkClient;
import com.lishengzn.common.util.SocketUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

public class DownloadVehicleMap {
    private static final Logger LOG = LoggerFactory.getLogger(DownloadVehicleMap.class);

    /*static {
        WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
    }*/
    public synchronized static Smap downloadMap(String ip){
        ShortLinkClient client =new ShortLinkClient(ip, SocketConstants.SERVER_PORT_VEHICLE_CONFIG, ClientOfVehicle.ClientType.vehicleConfig);
        Smap[] smaps =new Smap[1];
        client.runShortLinkClient((socket)->sendRequest(ip,socket),(socket)->receiveResponse(socket,smaps));
        return smaps[0];
    }

    private static void sendRequest(String ip,Socket socket){
        String map_name = QueryLoadedMap.queryMap(ip);
        RequestDownloadMap downloadMap = new RequestDownloadMap();
        downloadMap.setMap_name(map_name);
        PacketModel packetModel = new PacketModel(SocketConstants.PACKET_TYPE_OTHER_DOWNLOAD_MAP, JSONObject.toJSONString(downloadMap));
        try {
            SocketUtil.sendPacketData(socket.getOutputStream(),packetModel);
        } catch (IOException e) {
            throw new SimpleException("向小车发送信息失败，请检查与小车的连接！");
        }
    }
    private static void receiveResponse(Socket socket,Smap[] smaps){
        PacketModel packetModel = null;
        try {
            if((packetModel=SocketUtil.readNextPacketData(socket.getInputStream()))!=null){
                if((packetModel.getPacketType()-SocketConstants.RESPONSE_PACKET_ADDED)==SocketConstants.PACKET_TYPE_OTHER_DOWNLOAD_MAP){
                    LOG.info("下载到地图,length:{},{}",packetModel.getData().length(),packetModel.getData());
                    saveMapData(packetModel.getData());
                    smaps[0]=JSONObject.parseObject(packetModel.getData(),Smap.class);
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
