package com.lishengzn.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lishengzn.entity.read.ReadItem_Request;
import com.lishengzn.entity.read.ReadVariable;
import com.lishengzn.packet.PacketModel;
import com.lishengzn.packet.PacketSerialNo;
import com.lishengzn.socket.Client;
import com.lishengzn.util.LSConstants;

public class RetrievalAGVInfoTask extends CyclicTask{
	private static final Logger LOG=LoggerFactory.getLogger(RetrievalAGVInfoTask.class);
	private Client client;
	private List<Integer> varIDList;
	private static final Map<Integer,ReadItem_Request>requestVariableMap=new HashMap<Integer,ReadItem_Request>();
	static{
		requestVariableMap.put(LSConstants.VARID_POSITOIN, new ReadItem_Request(LSConstants.VARTYPE_POSITOIN,LSConstants.VARID_POSITOIN,0,0));
		requestVariableMap.put(LSConstants.VARID_VELOCITY, new ReadItem_Request(LSConstants.VARTYPE_VELOCITY,LSConstants.VARID_VELOCITY,0,0));
		requestVariableMap.put(LSConstants.VARID_CHARGESTATE, new ReadItem_Request(LSConstants.VARTYPE_CHARGESTATE,LSConstants.VARID_CHARGESTATE,0,0));
		requestVariableMap.put(LSConstants.VARID_CHECKPACKAGE, new ReadItem_Request(LSConstants.VARTYPE_CHECKPACKAGE,LSConstants.VARID_CHECKPACKAGE,0,0));
		requestVariableMap.put(LSConstants.VARID_FLIP_STATE, new ReadItem_Request(LSConstants.VARTYPE_FLIP_STATE,LSConstants.VARID_FLIP_STATE,0,0));
		requestVariableMap.put(LSConstants.VARID_JACKING_DISTANCE, new ReadItem_Request(LSConstants.VARTYPE_JACKING_DISTANCE,LSConstants.VARID_JACKING_DISTANCE,0,0));
		requestVariableMap.put(LSConstants.VARID_BELT_ROTATION_STATE, new ReadItem_Request(LSConstants.VARTYPE_BELT_ROTATION_STATE,LSConstants.VARID_BELT_ROTATION_STATE,0,0));
		requestVariableMap.put(LSConstants.VARID_OPERATION_STATE, new ReadItem_Request(LSConstants.VARTYPE_OPERATION_STATE,LSConstants.VARID_OPERATION_STATE,0,0));
		requestVariableMap.put(LSConstants.VARID_BATTERYCAPACITY, new ReadItem_Request(LSConstants.VARTYPE_BATTERYCAPACITY,LSConstants.VARID_BATTERYCAPACITY,0,0));
		
	}
	public RetrievalAGVInfoTask(long tSleep, Client client,List<Integer> varIDList) {
		super(tSleep);
		this.client = client;
		this.varIDList=varIDList;
	}


	@Override
	protected void runActualTask() {
		try {
			if(client.isTerminate()){
				this.terminate();
				return;
			}
			List<ReadItem_Request> readItemList= varIDList.stream().map((varid)->{return requestVariableMap.get(varid);}).collect(Collectors.toList());
			ReadVariable<ReadItem_Request> readVariable = new ReadVariable<ReadItem_Request>(readItemList.size(),readItemList,ReadItem_Request.class);
			byte[] data_byte=readVariable.toBytes();
			PacketModel packetModel = new PacketModel();
			packetModel.setPacketType(LSConstants.PACKET_TYPE_READVAR);
			packetModel.setPacketSerialNo(PacketSerialNo.getSerialNo());
			packetModel.setData_bytes(data_byte);
			packetModel.setErrorCode(LSConstants.ERROR_CODE_SUCCESS);
			client.sendMsgToServer(packetModel);
		} catch (Exception e) {
			LOG.error("",e);
		}

		
	}

}