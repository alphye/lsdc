package com.lishengzn.lsdc.dto;

import java.io.Serializable;

public class CommandDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String commandType;
	private Object message;
	
	
	public CommandDto(){}
	public CommandDto(String commandType, Object message) {
		super();
		this.commandType = commandType;
		this.message = message;
	}
	public String getCommandType() {
		return commandType;
	}
	public void setCommandType(String commandType) {
		this.commandType = commandType;
	}
	public Object getMessage() {
		return message;
	}
	public void setMessage(Object message) {
		this.message = message;
	}
	
	public enum TYPE{
		/**
		 * 设置车辆信息
		 */
		setVehicleInfo,
		/**
		 * 设置车辆导航状态
		 */
		setVehicleNaviState,
		/**
		 * 设置车辆操作状态
		 */
		setVehicleOperationState,
		/**
		 * 错误信息
		 */
		error,
		/**
		 * 车辆按格数移动
		 */
		vehicleMove,
		/**
		 * 发送到指定点
		 */
		sendToNode,
		/**
		 * 取消导航任务
		 */
		cancleNaviTask,
		/**
		 * 暂停导航任务
		 */
		pauseNaviTask,
		/**
		 * 恢复导航任务
		 */
		recoverNaviTask, 
		setVehicleInIp,
		/**
		 * 查询导航轨迹
		 */
		queryNaviTrails,
		/**
		 * 追加导航(按格数移动)
		 */
		appendNaviTaskWidthCellNum,
		/**
		 * 操作任务
		 */
		sendOperationTask,
		cancleOperationTask,
		pauseOperationTask,
		recoverOperationTask,
		
		/**
		 * 停止充电
		 */
		stopCharge,
		startCharge,
		packageSize,
		/**
		 * 清除错误
		 */
		clearError,
		printNaviTrails;
	}
	
}
