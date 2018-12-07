package com.lishengzn.entity.operation;

import com.lishengzn.entity.DataArea;
import com.lishengzn.util.SocketUtil;

public class OperationTask implements DataArea{
	protected long taskID;
	
	public OperationTask(){}
	public OperationTask(long taskID) {
		super();
		this.taskID = taskID;
	}
	public  byte[] toBytes(){
		byte[] naviTaskVar_bytes = SocketUtil.longToBytes(taskID);
		return naviTaskVar_bytes;
	}
	public  OperationTask fromBytes(byte[] NaviTaskVar_bytes){
		if(NaviTaskVar_bytes.length!=8){
			throw new RuntimeException("只包含任务ID的操作任务数据长度须为8");
		}
		this.setTaskID(SocketUtil.bytesToLong(NaviTaskVar_bytes));
		return this;
	}
	public long getTaskID() {
		return taskID;
	}
	public void setTaskID(long taskID) {
		this.taskID = taskID;
	}
	
}
