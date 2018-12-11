package com.lishengzn.controller;

import com.alibaba.fastjson.JSONObject;
import com.lishengzn.constant.TopicAddressConstants;
import com.lishengzn.dto.CommandDto;
import com.lishengzn.dto.OperationCommandDto;
import com.lishengzn.dto.SimProCommandDto;
import com.lishengzn.dto.VehicleMoveDto;
import com.lishengzn.entity.read.ReadItem_Request;
import com.lishengzn.entity.read.ReadVariable;
import com.lishengzn.exception.SimpleException;
import com.lishengzn.objectpool.TransportOrderPool;
import com.lishengzn.packet.PacketModel;
import com.lishengzn.packet.PacketSerialNo;
import com.lishengzn.service.WebSocketMsgService;
import com.lishengzn.socket.Client;
import com.lishengzn.util.CacheManager;
import com.lishengzn.util.LSConstants;
import com.lishengzn.websocket.MsgWeixin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@MessageMapping("/wbskt")
public class WebSocketController {

    private static final Logger LOG = LoggerFactory.getLogger(WebSocketController.class);
    //通过SimpMessagingTemplate向用户发送消息

    @Autowired
    private WebSocketMsgService webSocketMsgService;

    @Autowired
    private TransportOrderPool transportOrderPool;



    @MessageMapping("/sendChatMsg")
    public void sendChatMsg( String json)throws Exception{
        if (!StringUtils.isEmpty(json) ) {
            MsgWeixin po = JSONObject.parseObject(json,MsgWeixin.class);
            String chatAddress = "/queue/contactMessage";
//            messagingTemplate.convertAndSend(chatAddress,po.getContent());
        }
    }

    /**小车按格子数移动
     * @param commandDto
     */
    @MessageMapping("/vehicleMove")
    public void vehicleMove(CommandDto commandDto){
       LOG.info("收到按格子数移动命令：{}", JSONObject.toJSONString(commandDto));
        VehicleMoveDto vehicleMove = JSONObject.parseObject(JSONObject.toJSONString(commandDto.getMessage()),VehicleMoveDto.class);
        transportOrderPool.createTransportOrderWidthCellNum(vehicleMove);
    }

    /**小车发送到指定点
     * @param commandDto
     */
    @MessageMapping("/sendToNode")
    public void sendToNode(CommandDto commandDto){
       LOG.info("收到发送到指定点：{}", JSONObject.toJSONString(commandDto));
        VehicleMoveDto vehicleMove = JSONObject.parseObject(JSONObject.toJSONString(commandDto.getMessage()),VehicleMoveDto.class);
        transportOrderPool.createTransportOrderWidthDestCoor(vehicleMove);
    }

    /**取消导航任务
     * @param commandDto
     */
    @MessageMapping("/cancleNaviTask")
    public void cancleNaviTask(CommandDto commandDto){
       LOG.info("收到取消导航任务：{}", JSONObject.toJSONString(commandDto));
        VehicleMoveDto vehicleMove = JSONObject.parseObject(JSONObject.toJSONString(commandDto.getMessage()),VehicleMoveDto.class);
        Map<String,Client> clientMap =(Map<String,Client>) CacheManager.cache.get(CacheManager.clientPoolKey);
        Client client = getClientByIp(vehicleMove.getVehicleIp());
        
        client.cancleNaviTask();
    }

    /**暂停导航任务
     * @param commandDto
     */
    @MessageMapping("/pauseNaviTask")
    public void pauseNaviTask(CommandDto commandDto){
       LOG.info("收到暂停导航任务：{}", JSONObject.toJSONString(commandDto));
        VehicleMoveDto vehicleMove = JSONObject.parseObject(JSONObject.toJSONString(commandDto.getMessage()),VehicleMoveDto.class);
        Client client = getClientByIp(vehicleMove.getVehicleIp());
        client.pauseNaviTask();
    }

    /**恢复导航任务
     * @param commandDto
     */
    @MessageMapping("/recoverNaviTask")
    public void recoverNaviTask(CommandDto commandDto){
       LOG.info("收到恢复导航任务：{}",  JSONObject.toJSONString(commandDto));
        VehicleMoveDto vehicleMove = JSONObject.parseObject(JSONObject.toJSONString(commandDto.getMessage()),VehicleMoveDto.class);
        Client client = getClientByIp(vehicleMove.getVehicleIp());
        client.recoverNaviTask();
    }

    /**查询导航轨迹
     * @param commandDto
     */
    @MessageMapping("/queryNaviTrails")
    public void queryNaviTrails(CommandDto commandDto){
       LOG.info("收到查询导航轨迹任务：{}", JSONObject.toJSONString(commandDto));
        VehicleMoveDto vehicleMove = JSONObject.parseObject(JSONObject.toJSONString(commandDto.getMessage()),VehicleMoveDto.class);
        Client client = getClientByIp(vehicleMove.getVehicleIp());
        client.queryNaviTrails();
    }

    /**追加导航
     * @param commandDto
     */
    @MessageMapping("/appendNaviTaskWidthCellNum")
    public void appendNaviTaskWidthCellNum(CommandDto commandDto){
       LOG.info("收到追加导航任务：{}", JSONObject.toJSONString(commandDto));
        VehicleMoveDto vehicleMove = JSONObject.parseObject(JSONObject.toJSONString(commandDto.getMessage()),VehicleMoveDto.class);
        Client client = getClientByIp(vehicleMove.getVehicleIp());
        client.appendNaviTaskWidthCellNum(vehicleMove);
    }

    /**发送操作任务
     * @param commandDto
     */
    @MessageMapping("/sendOperationTask")
    public void sendOperationTask(CommandDto commandDto){
       LOG.info("收到发送操作任务：{}", JSONObject.toJSONString(commandDto));
        OperationCommandDto operationCommandDto = JSONObject.parseObject(JSONObject.toJSONString(commandDto.getMessage()),OperationCommandDto.class);
        Client client = getClientByIp(operationCommandDto.getVehicleIp());
        client.sendOperationTask(operationCommandDto);
    }

    /**取消操作任务
     * @param commandDto
     */
    @MessageMapping("/cancleOperationTask")
    public void cancleOperationTask(CommandDto commandDto){
       LOG.info("收到取消操作任务：{}", JSONObject.toJSONString(commandDto));
        OperationCommandDto operationCommandDto = JSONObject.parseObject(JSONObject.toJSONString(commandDto.getMessage()),OperationCommandDto.class);
        Client client = getClientByIp(operationCommandDto.getVehicleIp());
        client.cancleOperationTask(operationCommandDto);
    }

    /**暂停操作任务
     * @param commandDto
     */
    @MessageMapping("/pauseOperationTask")
    public void pauseOperationTask(CommandDto commandDto){
       LOG.info("收到暂停操作任务：{}", JSONObject.toJSONString(commandDto));
        OperationCommandDto operationCommandDto = JSONObject.parseObject(JSONObject.toJSONString(commandDto.getMessage()),OperationCommandDto.class);
        Client client = getClientByIp(operationCommandDto.getVehicleIp());
        client.pauseOperationTask(operationCommandDto);
    }

    /**恢复操作任务
     * @param commandDto
     */
    @MessageMapping("/recoverOperationTask")
    public void recoverOperationTask(CommandDto commandDto){
       LOG.info("收到恢复操作任务：{}", JSONObject.toJSONString(commandDto));
        OperationCommandDto operationCommandDto = JSONObject.parseObject(JSONObject.toJSONString(commandDto.getMessage()),OperationCommandDto.class);
        Client client = getClientByIp(operationCommandDto.getVehicleIp());
        client.recoverOperationTask(operationCommandDto);
    }

    /**停止充电
     * @param commandDto
     */
    @MessageMapping("/stopCharge")
    public void stopCharge(CommandDto commandDto){
       LOG.info("收到停止充电任务：{}", JSONObject.toJSONString(commandDto));
        OperationCommandDto operationCommandDto = JSONObject.parseObject(JSONObject.toJSONString(commandDto.getMessage()),OperationCommandDto.class);
        Client client = getClientByIp(operationCommandDto.getVehicleIp());
        client.controlCharge(LSConstants.CONTROLCHARGE_STOP);
    }

    /**开始充电
     * @param commandDto
     */
    @MessageMapping("/startCharge")
    public void startCharge(CommandDto commandDto){
       LOG.info("收到开始充电任务：{}", JSONObject.toJSONString(commandDto));
        OperationCommandDto operationCommandDto = JSONObject.parseObject(JSONObject.toJSONString(commandDto.getMessage()),OperationCommandDto.class);
        Client client = getClientByIp(operationCommandDto.getVehicleIp());
        client.controlCharge(LSConstants.CONTROLCHARGE_START);
    }

    /**写入包裹大小
     * @param commandDto
     */
    @MessageMapping("/packageSize")
    public void packageSize(CommandDto commandDto){
       LOG.info("收到写入包裹大小任务：{}", JSONObject.toJSONString(commandDto));
        VehicleMoveDto vehicleMove = JSONObject.parseObject(JSONObject.toJSONString(commandDto.getMessage()),VehicleMoveDto.class);
        Client client = getClientByIp(vehicleMove.getVehicleIp());
        client.writePakageSize(vehicleMove);
    }

    /**清除错误
     * @param commandDto
     */
    @MessageMapping("/clearError")
    public void clearError(CommandDto commandDto){
       LOG.info("收到清除错误任务：{}", JSONObject.toJSONString(commandDto));
        OperationCommandDto operationCommandDto = JSONObject.parseObject(JSONObject.toJSONString(commandDto.getMessage()),OperationCommandDto.class);
        Client client = getClientByIp(operationCommandDto.getVehicleIp());
        client.clearError();
    }

    @MessageMapping("/sendSimpProCmd")
    public void sendSimpProToServer(CommandDto commandDto){
        LOG.info("收到简单协议指令：{}", JSONObject.toJSONString(commandDto));
        SimProCommandDto simProCommandDto = JSONObject.parseObject(JSONObject.toJSONString(commandDto.getMessage()),SimProCommandDto.class);
        Client client = getClientByIp(simProCommandDto.getVehicleIp());
        client.sendSimProToServer(simProCommandDto.getParam());
        if(",13".indexOf(","+simProCommandDto.getParam().substring(0,2))>=0){
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
            }
            client.sendSimProToServer("090E");
        }
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
    @MessageMapping("/verhicleOnline")
    public void verhicleOnline( ) throws IOException {
        webSocketMsgService.vehicleOnline();
    }

    @MessageMapping("/retrievalVehicleInfo")
    public void retrievalVehicleInfo(CommandDto commandDto){
        LOG.info("=========retrievalVehicleInfo");
        List<ReadItem_Request> readItemList= new ArrayList<>();
        readItemList.add(new ReadItem_Request(LSConstants.VARTYPE_POSITOIN,LSConstants.VARID_POSITOIN,0,0));
        ReadVariable<ReadItem_Request> readVariable = new ReadVariable<ReadItem_Request>(readItemList.size(),readItemList,ReadItem_Request.class);
        byte[] data_byte=readVariable.toBytes();
        PacketModel packetModel = new PacketModel();
        packetModel.setPacketType(LSConstants.PACKET_TYPE_READVAR);
        packetModel.setPacketSerialNo(PacketSerialNo.getSerialNo());
        packetModel.setData_bytes(data_byte);
        packetModel.setErrorCode(LSConstants.ERROR_CODE_SUCCESS);

        OperationCommandDto operationCommandDto = JSONObject.parseObject(JSONObject.toJSONString(commandDto.getMessage()),OperationCommandDto.class);
        Client client = getClientByIp(operationCommandDto.getVehicleIp());
        client.sendMsgToServer(packetModel);
    }

    private Client getClientByIp(String vehicleIp){
        Map<String,Client> clientMap22 =(Map<String,Client>)CacheManager.cache.get(CacheManager.clientPoolKey);
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
