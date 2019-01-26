package com.lishengzn.lsdc.kernel.as.communication.service.impl;

import com.lishengzn.common.communication.AbstractCommunicateAdapter;
import com.lishengzn.lsdc.kernel.as.communication.service.APIStatusMessageSenderService;
import com.lishengzn.common.constants.SocketConstants;
import com.lishengzn.common.exception.SimpleException;
import com.lishengzn.common.packet.PacketModel;
import com.lishengzn.common.util.SocketUtil;
import com.lishengzn.common.pool.ObjectPool;
import com.lishengzn.common.socket.ClientOfVehicle;
import com.lishengzn.lsdc.task.CyclicTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;

public class StatusAPISenderServiceImpl extends AbstractCommunicateAdapter implements APIStatusMessageSenderService {

    private static final Logger LOG = LoggerFactory.getLogger(StatusAPISenderServiceImpl.class);
    private OutputStream out;
    private CyclicTask retrievalVehicleInfoTask;


    private void sendRequest(short packetType, String data, OutputStream out){
        PacketModel packetModel = new PacketModel(packetType,data);
        try {
            SocketUtil.sendPacketData(out,packetModel);
        } catch (IOException e) {
            if(ObjectPool.getClientOfVehicle(vehicle.getVehicleIp(), ClientOfVehicle.ClientType.statusAPi)!=null){
                LOG.error("向小车发送信息异常，IP：{}",vehicle.getVehicleIp());
                ObjectPool.getClientOfVehicle(vehicle.getVehicleIp(), ClientOfVehicle.ClientType.statusAPi).close();
            }
        }
    }

    @Override
    public void queryVehicleInfo() {
        short packetType = SocketConstants.PACKET_TYPE_STATUS_API_VEHICLE_INFO;
        sendRequest(packetType,"",out);
    }

    @Override
    public void queryRuningInfo() {
        short packetType = SocketConstants.PACKET_TYPE_STATUS_API_RUN;
        sendRequest(packetType,"",out);
    }

    @Override
    public void queryLocation() {
        short packetType = SocketConstants.PACKET_TYPE_STATUS_API_LOC;
        sendRequest(packetType,"",out);
    }

    @Override
    public void querySpeed() {
        short packetType = SocketConstants.PACKET_TYPE_STATUS_API_SPEED;
        sendRequest(packetType,"",out);
    }

    @Override
    public void queryBlockStatus() {
        short packetType = SocketConstants.PACKET_TYPE_STATUS_API_BLOCK;
        sendRequest(packetType,"",out);

    }

    @Override
    public void queryBatteryStatus() {
        short packetType = SocketConstants.PACKET_TYPE_STATUS_API_BATTERY;
        sendRequest(packetType,"",out);
    }

    @Override
    public void queryBrakeStatus() {
        short packetType = SocketConstants.PACKET_TYPE_STATUS_API_BRAKE;
        sendRequest(packetType,"",out);

    }

    @Override
    public void queryLaserInfo() {
        short packetType = SocketConstants.PACKET_TYPE_STATUS_API_LASER;
        sendRequest(packetType,"",out);
    }

    @Override
    public void queryFreeNaviPath() {
        short packetType = SocketConstants.PACKET_TYPE_STATUS_API_PATH;
        sendRequest(packetType,"",out);
    }

    @Override
    public void queryCurrentArea() {
        short packetType = SocketConstants.PACKET_TYPE_STATUS_API_AREA;
        sendRequest(packetType,"",out);
    }

    @Override
    public void queryEmergencyStopStatus() {
        short packetType = SocketConstants.PACKET_TYPE_STATUS_API_EMERGENCY;
        sendRequest(packetType,"",out);
    }

    @Override
    public void queryIOInfo() {
        short packetType = SocketConstants.PACKET_TYPE_STATUS_API_IO;
        sendRequest(packetType,"",out);
    }

    @Override
    public void queryIMUInfo() {
        short packetType = SocketConstants.PACKET_TYPE_STATUS_API_IMU;
        sendRequest(packetType,"",out);
    }

    @Override
    public void queryRFIDInfo() {
        short packetType = SocketConstants.PACKET_TYPE_STATUS_API_RFID;
        sendRequest(packetType,"",out);
    }

    @Override
    public void queryUltrasonicInfo() {
        short packetType = SocketConstants.PACKET_TYPE_STATUS_API_ULTRASONIC;
        sendRequest(packetType,"",out);
    }

    @Override
    public void queryNaviStatus(boolean simple) {
        short packetType = SocketConstants.PACKET_TYPE_STATUS_API_NAVI_STATUS;
        sendRequest(packetType,"{\"simple\":"+simple+"}",out);
    }

    @Override
    public void queryRelocStatus() {
        short packetType = SocketConstants.PACKET_TYPE_STATUS_API_RELOC;
        sendRequest(packetType,"",out);
    }

    @Override
    public void queryLoadMapStatus() {
        short packetType = SocketConstants.PACKET_TYPE_STATUS_API_LOADMAP;
        sendRequest(packetType,"",out);
    }

    @Override
    public void querySlamStatus() {
        short packetType = SocketConstants.PACKET_TYPE_STATUS_API_SLAM;
        sendRequest(packetType,"",out);
    }

    @Override
    public void queryJackStatus() {
        short packetType = SocketConstants.PACKET_TYPE_STATUS_API_JACK;
        sendRequest(packetType,"",out);
    }

    @Override
    public void queryRollerStatus() {
        short packetType = SocketConstants.PACKET_TYPE_STATUS_API_ROLLER;
        sendRequest(packetType,"",out);
    }

    @Override
    public void queryDispathStatus() {
        short packetType = SocketConstants.PACKET_TYPE_STATUS_API_DISPATCH;
        sendRequest(packetType,"",out);
    }

    @Override
    public void queryAlarmStatus() {
        short packetType = SocketConstants.PACKET_TYPE_STATUS_API_ALARM;
        sendRequest(packetType,"",out);
    }

    @Override
    public void queryAll1(boolean return_laser) {
        short packetType = SocketConstants.PACKET_TYPE_STATUS_API_ALL1;
        sendRequest(packetType,"{\"return_laser\":"+return_laser+"}",out);
    }

    @Override
    public void queryAll2(boolean return_laser) {
        short packetType = SocketConstants.PACKET_TYPE_STATUS_API_ALL2;
        sendRequest(packetType,"{\"return_laser\":"+return_laser+"}",out);
    }

    @Override
    public void queryAll3() {
        short packetType = SocketConstants.PACKET_TYPE_STATUS_API_ALL3;
        sendRequest(packetType,"",out);
    }

    @Override
    public void queryMapInfo() {
        short packetType = SocketConstants.PACKET_TYPE_STATUS_API_MAP;
        sendRequest(packetType,"",out);
    }

    @Override
    public void queryStationInfo() {
        short packetType = SocketConstants.PACKET_TYPE_STATUS_API_STATION;
        sendRequest(packetType,"",out);
    }

    @Override
    public void queryVehicleParams(String plugin,String param) {
        short packetType = SocketConstants.PACKET_TYPE_STATUS_API_PARAMS;
        sendRequest(packetType,"{\"plugin\":"+plugin+",\"param\":+param+}",out);
    }

    @Override
    public void initialize() {
        super.initialize();
        try {
            this.out = this.getSocket().getOutputStream();
        } catch (IOException e) {
            throw new SimpleException("与车辆连接异常，请重新连接！");
        }

        // 获取地图信息
       // queryLoadMapStatus();
        // 获取地图站点
       // queryStationInfo();
    }

    public void runTask(){
        retrievalVehicleInfoTask=new CyclicTask(200) {
            @Override
            protected void runActualTask() {
                queryAll2(false);
            }
        };
        ObjectPool.messageSendThreadPool.execute(retrievalVehicleInfoTask);
    }

    @Override
    public void terminate() {
        super.terminate();
        if(isInitialized() && retrievalVehicleInfoTask!=null){
            retrievalVehicleInfoTask.terminate();
        }

    }



}
