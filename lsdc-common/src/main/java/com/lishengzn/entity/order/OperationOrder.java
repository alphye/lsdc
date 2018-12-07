package com.lishengzn.entity.order;

import com.lishengzn.entity.operation.SendOperationTask;

public class OperationOrder {
	private long operationTaskID;
	private SendOperationTask sendOperationTask;
	private STATE state;
	private long createTime =System.currentTimeMillis();
	
	public long getOperationTaskID() {
		return operationTaskID;
	}

	public void setOperationTaskID(long operationTaskID) {
		this.operationTaskID = operationTaskID;
	}

	public SendOperationTask getSendOperationTask() {
		return sendOperationTask;
	}

	public void setSendOperationTask(SendOperationTask sendOperationTask) {
		this.sendOperationTask = sendOperationTask;
	}

	public STATE getState() {
		return state;
	}

	public void setState(STATE state) {
		this.state = state;
	}

	public long getCreateTime() {
		return createTime;
	}


	public enum STATE{
		DISPATCHABLE,
		EXECUTING,
		FAILED,
		FINISHED;
	}
}
