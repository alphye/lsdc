package com.lishengzn.entity.navi;

import com.lishengzn.entity.DataArea;
import com.lishengzn.util.SocketUtil;

public class NaviTask implements DataArea{
	protected long taskID;
	
	public NaviTask(){}
	public NaviTask(long taskID) {
		super();
		this.taskID = taskID;
	}
	public  byte[] toBytes(){
		byte[] NaviTaskVar_bytes = SocketUtil.longToBytes(taskID);
		return NaviTaskVar_bytes;
	}
	public  NaviTask fromBytes(byte[] NaviTaskVar_bytes){
		if(NaviTaskVar_bytes.length!=8){
			throw new RuntimeException("只包含任务ID的导航任务数据长度须为8");
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
