package com.lishengzn.lsdc.communication.service.impl;

import com.lishengzn.common.communication.AbstractCommunicateAdapter;
import com.lishengzn.common.constants.SocketConstants;
import com.lishengzn.common.exception.SimpleException;
import com.lishengzn.common.packet.PacketModel;
import com.lishengzn.common.util.SocketUtil;
import com.lishengzn.common.pool.ObjectPool;
import com.lishengzn.common.socket.ClientOfVehicle;
import com.lishengzn.lsdc.communication.service.NaviMessageReceiveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

public class NaviReceiveServiceImpl extends AbstractCommunicateAdapter implements NaviMessageReceiveService {
    private static final Logger LOG = LoggerFactory.getLogger(NaviReceiveServiceImpl.class);
    private InputStream in ;

    @Override
    public void handlReceivedMsg() {
        PacketModel packetModel=null;
        try {
            LOG.info("========navi receive start");
            while(isInitialized()){
                if((packetModel=SocketUtil.readNextPacketData(in))!=null){
                    try {
                        short packetType = packetModel.getPacketType();
                        switch (packetType-SocketConstants.RESPONSE_PACKET_ADDED){
                            default:
                                LOG.error("包类型无效：{}",packetType);
                        }
                    }catch (Exception e){
                        LOG.error("",e);
                    }
                }

            }
            LOG.info("========navi receive end");
        }catch (IOException e){
            LOG.info("========navi receive end_e");
            if(ObjectPool.getClientOfVehicle(vehicle.getVehicleIp(), ClientOfVehicle.ClientType.vehicleNavi)!=null){
                LOG.error("接收小车信息异常，IP：{}",vehicle.getVehicleIp(),e);
                ObjectPool.getClientOfVehicle(vehicle.getVehicleIp(), ClientOfVehicle.ClientType.vehicleNavi).close();
            }
        }
    }


    @Override
    public void initialize() {
        super.initialize();
        try {
            this.in = this.getSocket().getInputStream();
            runTask();
        } catch (IOException e) {
            throw new SimpleException("与车辆连接异常，请重新连接！");
        }

    }
    private void runTask(){
        ObjectPool.messageReceiveThreadPool.execute(this::handlReceivedMsg);
    }

}