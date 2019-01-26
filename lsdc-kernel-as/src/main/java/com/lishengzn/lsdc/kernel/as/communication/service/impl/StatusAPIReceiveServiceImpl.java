package com.lishengzn.lsdc.kernel.as.communication.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.lishengzn.common.communication.AbstractCommunicateAdapter;
import com.lishengzn.lsdc.kernel.as.communication.service.APIStatusMessageReceiveService;
import com.lishengzn.common.constants.SocketConstants;
import com.lishengzn.common.entity.response.statusapi.*;
import com.lishengzn.common.exception.SimpleException;
import com.lishengzn.common.packet.PacketModel;
import com.lishengzn.common.util.SocketUtil;
import com.lishengzn.common.pool.ObjectPool;
import com.lishengzn.common.socket.ClientOfVehicle;
import com.lishengzn.lsdc.kernel.as.shortlink.QueryLoadedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

public class StatusAPIReceiveServiceImpl extends AbstractCommunicateAdapter implements APIStatusMessageReceiveService {
    private static final Logger LOG = LoggerFactory.getLogger(StatusAPIReceiveServiceImpl.class);
    private InputStream in ;

    @Override
    public void handlReceivedMsg() {
        PacketModel packetModel=null;
        try {
            LOG.info("========apistatus receive start");
            while(!isTerminate()){
                if((packetModel=SocketUtil.readNextPacketData(in))!=null){
                    try {
                        short packetType = packetModel.getPacketType();
                        switch (packetType-SocketConstants.RESPONSE_PACKET_ADDED){
                            case SocketConstants.PACKET_TYPE_STATUS_API_VEHICLE_INFO:
                                handleVehicleInfo(packetModel);
                                break;
                            case SocketConstants.PACKET_TYPE_STATUS_API_RUN:
                                handleVehicleRunInfo(packetModel);
                                break;
                            case SocketConstants.PACKET_TYPE_STATUS_API_LOC:
                                handleVehicleLocation(packetModel);
                                break;
                            case SocketConstants.PACKET_TYPE_STATUS_API_SPEED:
                                handleVehicleSpeed(packetModel);
                                break;
                            case SocketConstants.PACKET_TYPE_STATUS_API_BLOCK:
                                handleVehicleBlock(packetModel);
                                break;
                            case SocketConstants.PACKET_TYPE_STATUS_API_BATTERY:
                                handleVehicleBattery(packetModel);
                                break;
                            case SocketConstants.PACKET_TYPE_STATUS_API_BRAKE:
                                handleVehicleBrake(packetModel);
                                break;
                            case SocketConstants.PACKET_TYPE_STATUS_API_LASER:
                                handleVehicleLaser(packetModel);
                                break;
                            case SocketConstants.PACKET_TYPE_STATUS_API_AREA:
                                handleVehicleArea(packetModel);
                                break;
                            case SocketConstants.PACKET_TYPE_STATUS_API_EMERGENCY:
                                handleVehicleEmergencyStop(packetModel);
                                break;
                            case SocketConstants.PACKET_TYPE_STATUS_API_IO:
                                handleVehicleIO(packetModel);
                                break;
                            case SocketConstants.PACKET_TYPE_STATUS_API_IMU:
                                handleVehicleIMU(packetModel);
                                break;
                            case SocketConstants.PACKET_TYPE_STATUS_API_RFID:
                                handleVehicleRFID(packetModel);
                                break;
                            case SocketConstants.PACKET_TYPE_STATUS_API_ULTRASONIC:
                                handleVehicleUltrasonic(packetModel);
                                break;
                            case SocketConstants.PACKET_TYPE_STATUS_API_NAVI_STATUS:
                                handleVehicleNaviStatus(packetModel);
                                break;
                            case SocketConstants.PACKET_TYPE_STATUS_API_RELOC:
                                handleVehicleReloc(packetModel);
                                break;
                            case SocketConstants.PACKET_TYPE_STATUS_API_LOADMAP:
                                handleVehicleLoadMap(packetModel);
                                break;
                            case SocketConstants.PACKET_TYPE_STATUS_API_MAP:
                                handleVehicleMap(packetModel);
                                break;
                            case SocketConstants.PACKET_TYPE_STATUS_API_SLAM:
                                handleVehicleSlam(packetModel);
                                break;
                            case SocketConstants.PACKET_TYPE_STATUS_API_JACK:
                                handleVehicleJack(packetModel);
                                break;
                            case SocketConstants.PACKET_TYPE_STATUS_API_ROLLER:
                                handleVehicleRoller(packetModel);
                                break;
                            case SocketConstants.PACKET_TYPE_STATUS_API_DISPATCH:
                                handleVehicleDispath(packetModel);
                                break;
                            case SocketConstants.PACKET_TYPE_STATUS_API_ALARM:
                                handleVehicleAlarm(packetModel);
                                break;
                            case SocketConstants.PACKET_TYPE_STATUS_API_ALL1:
                                handleAll1(packetModel);
                                break;
                            case SocketConstants.PACKET_TYPE_STATUS_API_ALL2:
                                handleAll2(packetModel);
                                break;
                            case SocketConstants.PACKET_TYPE_STATUS_API_ALL3:
                                handleAll3(packetModel);
                                break;
                            case SocketConstants.PACKET_TYPE_STATUS_API_STATION:
                                handleVehicleStation(packetModel);
                                break;
                            case SocketConstants.PACKET_TYPE_STATUS_API_PARAMS:
                                handleVehicleParams(packetModel);
                                break;
                            default:
                                LOG.error("包类型无效：{}",packetType);
                        }
                    }catch (Exception e){
                        LOG.error("",e);
                    }
                }

            }
            LOG.info("========apistatus receive end");
        }catch (IOException e){
            LOG.info("========apistatus receive end_e");
            if(ObjectPool.getClientOfVehicle(vehicle.getVehicleIp(), ClientOfVehicle.ClientType.statusAPi)!=null){
                LOG.error("接收小车信息异常，IP：{}",vehicle.getVehicleIp());
                ObjectPool.getClientOfVehicle(vehicle.getVehicleIp(), ClientOfVehicle.ClientType.statusAPi).close();
            }
        }
    }


    private void handleVehicleInfo(PacketModel packetModel) {
        ResponseVehicleInfo vehicleInfo = JSONObject.parseObject(packetModel.getData(),ResponseVehicleInfo.class);
        LOG.debug("收到小车基本信息：{}",JSONObject.toJSONString(vehicleInfo));
    }

    private void handleVehicleRunInfo(PacketModel packetModel) {
        ResponseVehicleRunInfo vehicleRunInfo = JSONObject.parseObject(packetModel.getData(),ResponseVehicleRunInfo.class);
        LOG.debug("收到小车运行信息：{}",JSONObject.toJSONString(vehicleRunInfo));
    }

    private void handleVehicleLocation(PacketModel packetModel) {
        ResponsePosition position = JSONObject.parseObject(packetModel.getData(),ResponsePosition.class);
        LOG.debug("收到位置信息：{}",JSONObject.toJSONString(position));
    }

    private void handleVehicleSpeed(PacketModel packetModel) {
        ResponseSpeed speedInfo = JSONObject.parseObject(packetModel.getData(),ResponseSpeed.class);
        LOG.debug("收到小车速度信息：{}",JSONObject.toJSONString(speedInfo));
    }

    private void handleVehicleBlock(PacketModel packetModel) {
        ResponseBlock blockInfo = JSONObject.parseObject(packetModel.getData(),ResponseBlock.class);
        LOG.debug("收到小车受阻挡信息：{}",JSONObject.toJSONString(blockInfo));
    }

    private void handleVehicleBattery(PacketModel packetModel) {
        ResponseBatteryInfo batteryInfo = JSONObject.parseObject(packetModel.getData(),ResponseBatteryInfo.class);
        LOG.debug("收到小车电池信息：{}",JSONObject.toJSONString(batteryInfo));
    }

    private void handleVehicleBrake(PacketModel packetModel) {
        ResponseBrakeInfo brakeInfo = JSONObject.parseObject(packetModel.getData(),ResponseBrakeInfo.class);
        LOG.debug("收到小车抱闸信息：{}",JSONObject.toJSONString(brakeInfo));
    }

    private void handleVehicleLaser(PacketModel packetModel) {
        ResponseLasersInfo laserInfo = JSONObject.parseObject(packetModel.getData(),ResponseLasersInfo.class);
        LOG.debug("收到小车激光点云信息：{}",JSONObject.toJSONString(laserInfo));
    }

    private void handleVehicleArea(PacketModel packetModel) {
        ResponseAreaInfo areaInfo = JSONObject.parseObject(packetModel.getData(),ResponseAreaInfo.class);
        LOG.debug("收到小车区域信息：{}",JSONObject.toJSONString(areaInfo));
    }

    private void handleVehicleEmergencyStop(PacketModel packetModel) {
        ResponseEmergencyStop emergencyStop = JSONObject.parseObject(packetModel.getData(),ResponseEmergencyStop.class);
        LOG.debug("收到小车急停信息：{}",JSONObject.toJSONString(emergencyStop));
    }

    private void handleVehicleIO(PacketModel packetModel) {
        ResponseIOInfo ioInfo = JSONObject.parseObject(packetModel.getData(),ResponseIOInfo.class);
        LOG.debug("收到小车IO信息：{}",JSONObject.toJSONString(ioInfo));
    }

    private void handleVehicleIMU(PacketModel packetModel) {
        ResponseIMUInfo imuInfo = JSONObject.parseObject(packetModel.getData(),ResponseIMUInfo.class);
        LOG.debug("收到小车IMU信息：{}",JSONObject.toJSONString(imuInfo));
    }

    private void handleVehicleRFID(PacketModel packetModel) {
        ResponseRFIDInfo rfidInfo = JSONObject.parseObject(packetModel.getData(),ResponseRFIDInfo.class);
        LOG.debug("收到小车RFID信息：{}",JSONObject.toJSONString(rfidInfo));
    }

    private void handleVehicleUltrasonic(PacketModel packetModel) {
        ResponseUltrasonicInfo ultrasonicInfo = JSONObject.parseObject(packetModel.getData(),ResponseUltrasonicInfo.class);
        LOG.debug("收到小车超声传感器信息：{}",JSONObject.toJSONString(ultrasonicInfo));
    }

    private void handleVehicleNaviStatus(PacketModel packetModel) {
        ResponseNaviStatus naviStatus = JSONObject.parseObject(packetModel.getData(),ResponseNaviStatus.class);
        LOG.debug("收到小车导航状态信息：{}",JSONObject.toJSONString(naviStatus));
    }

    private void handleVehicleReloc(PacketModel packetModel) {
        ResponseRelocStatus relocStatus = JSONObject.parseObject(packetModel.getData(),ResponseRelocStatus.class);
        LOG.debug("收到小车定位状态信息：{}",JSONObject.toJSONString(relocStatus));
    }

    private void handleVehicleLoadMap(PacketModel packetModel) {
        ResponseLoadMapStatus loadMapStatus = JSONObject.parseObject(packetModel.getData(),ResponseLoadMapStatus.class);
        LOG.debug("收到小车地图载入信息：{}",JSONObject.toJSONString(loadMapStatus));
    }

    private void handleVehicleMap(PacketModel packetModel) {
        ResponseCurrMap mapInfo = JSONObject.parseObject(packetModel.getData(), ResponseCurrMap.class);
        LOG.debug("收到小车地图存储信息：{}",JSONObject.toJSONString(mapInfo));
        QueryLoadedMap.currLoadedMap=mapInfo.getCurrent_map();
    }

    private void handleVehicleSlam(PacketModel packetModel) {
        ResponseSlamStatus slamStatus = JSONObject.parseObject(packetModel.getData(),ResponseSlamStatus.class);
        LOG.debug("收到小车扫图状态信息：{}",JSONObject.toJSONString(slamStatus));
    }

    private void handleVehicleJack(PacketModel packetModel) {
        ResponseJackStatus jackStatus = JSONObject.parseObject(packetModel.getData(),ResponseJackStatus.class);
        LOG.debug("收到小车机构顶升信息：{}",JSONObject.toJSONString(jackStatus));
    }

    private void handleVehicleRoller(PacketModel packetModel) {
        ResponseRollerStatus rollerStatus = JSONObject.parseObject(packetModel.getData(),ResponseRollerStatus.class);
        LOG.debug("收到小车皮带信息：{}",JSONObject.toJSONString(rollerStatus));
    }

    private void handleVehicleDispath(PacketModel packetModel) {
        ResponseDispathStatus dispathStatus = JSONObject.parseObject(packetModel.getData(),ResponseDispathStatus.class);
        LOG.debug("收到小车调度信息：{}",JSONObject.toJSONString(dispathStatus));
    }

    private void handleVehicleAlarm(PacketModel packetModel) {
        ResponseAlarmStatus alarmStatus = JSONObject.parseObject(packetModel.getData(),ResponseAlarmStatus.class);
        LOG.debug("收到小车告警信息：{}",JSONObject.toJSONString(alarmStatus));
    }

    private void handleAll1(PacketModel packetModel) {
        ResponseAll1 all1 = JSONObject.parseObject(packetModel.getData(),ResponseAll1.class);
        LOG.debug("收到ALL1 信息：{}",JSONObject.toJSONString(all1));
    }

    private void handleAll2(PacketModel packetModel) {
        ResponseAll2 all2 = JSONObject.parseObject(packetModel.getData(),ResponseAll2.class);
        LOG.debug("收到ALL2 信息：{}",JSONObject.toJSONString(all2));
    }

    private void handleAll3(PacketModel packetModel) {
        ResponseAll3 all3 = JSONObject.parseObject(packetModel.getData(),ResponseAll3.class);
        LOG.debug("收到ALL3 信息：{}",JSONObject.toJSONString(all3));
    }

    private void handleVehicleStation(PacketModel packetModel) {
        ResponseMapStations mapStations = JSONObject.parseObject(packetModel.getData(),ResponseMapStations.class);
        LOG.debug("收到站点信息：{}",JSONObject.toJSONString(mapStations));

    }

    private void handleVehicleParams(PacketModel packetModel) {
        ResponseParams vehicleParams = JSONObject.parseObject(packetModel.getData(),ResponseParams.class);
        LOG.debug("收到车辆参数信息：{}",JSONObject.toJSONString(vehicleParams));
    }




    @Override
    public void initialize() {
        super.initialize();
        try {
            this.in = this.getSocket().getInputStream();
        } catch (IOException e) {
            throw new SimpleException("与车辆连接异常，请重新连接！");
        }

    }
    public void runTask(){
        ObjectPool.messageReceiveThreadPool.execute(this::handlReceivedMsg);
    }
}
