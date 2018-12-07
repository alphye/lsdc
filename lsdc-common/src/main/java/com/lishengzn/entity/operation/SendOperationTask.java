package com.lishengzn.entity.operation;

import com.google.common.primitives.Bytes;
import com.lishengzn.util.SocketUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SendOperationTask extends OperationTask{
	private int operationCode;
	private int operationParamNum;
	private List<Integer> operationParams;
	
	public SendOperationTask() {
	}

	public SendOperationTask(long taskID, int operationCode, int operationParamNum, List<Integer> operationParams) {
		super(taskID);
		this.operationCode = operationCode;
		this.operationParamNum = operationParamNum;
		this.operationParams = operationParams;
	}

	public int getOperationCode() {
		return operationCode;
	}

	public void setOperationCode(int operationCode) {
		this.operationCode = operationCode;
	}

	public int getOperationParamNum() {
		return operationParamNum;
	}

	public void setOperationParamNum(int operationParamNum) {
		this.operationParamNum = operationParamNum;
	}

	public List<Integer> getOperationParams() {
		return operationParams;
	}

	public void setOperationParams(List<Integer> operationParams) {
		this.operationParams = operationParams;
	}
	
	@Override
	public byte[] toBytes() {
		List<Byte> byteList=new ArrayList<Byte>();
		byteList.addAll(Bytes.asList(SocketUtil.longToBytes(taskID)));
		byteList.addAll(Bytes.asList(SocketUtil.intToBytes(operationCode)));
		byteList.addAll(Bytes.asList(SocketUtil.intToBytes(operationParamNum)));
		
		operationParams.forEach((operationParam)->{
			if(operationParam !=null){
				byteList.addAll(Bytes.asList(SocketUtil.intToBytes(operationParam)));
			}
		});
		
		return Bytes.toArray(byteList);
	}
	@Override
	public SendOperationTask fromBytes(byte[] operationTask_bytes) {
		if(operationTask_bytes.length<16){
			throw new RuntimeException("发送导航任务的数据长度不得小于16！");
		}
		this.setTaskID(SocketUtil.bytesToLong(Arrays.copyOf(operationTask_bytes, 8)));
		this.setOperationCode(SocketUtil.bytesToInt(Arrays.copyOfRange(operationTask_bytes, 8,12)));
		this.setOperationParamNum(SocketUtil.bytesToInt(Arrays.copyOfRange(operationTask_bytes, 12,16)));
		// 从16 开始，后边的内容都是operationParam，每个trail的长度为4
		int operationParamStart_index=16;
		List<Integer> operationParams=new ArrayList<Integer>();
		for(int i=0;i<operationParamNum;i++){
			operationParams.add(SocketUtil.bytesToInt(Arrays.copyOfRange(operationTask_bytes, operationParamStart_index,operationParamStart_index+=4)));
			
		}
		this.setOperationParams(operationParams);
		return this;
	}
}
