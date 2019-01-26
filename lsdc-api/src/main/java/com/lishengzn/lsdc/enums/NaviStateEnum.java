package com.lishengzn.lsdc.enums;

public enum NaviStateEnum {
	/** 未知		0 */
	UNKNOWN("Unknown",0,"未知"),
	
	/** 空闲状态，接受任务		1 */
	IDLE("Idle",1,"空闲状态"),
	
	/** 预完成阶段，接受任务		2 */
//	PREPAREFINISH("PrepareFinish",2,"预完成阶段"),
	
	/** 准备阶段，不接受任务	3 */
	PENDINGFUNCTION("PendingFunction",3,"准备阶段"),
	
	/** 运行中，不接受任务		4 */
	RUNNING("Running",4,"运行中"),
	
	/** 挂起，不接受任务	5 */
	HANG("Hang",5,"挂起"),
	
	/** 阻塞中，可唤醒， 等待重新调度，不接受任务	6 */
//	ALERTABLE("Alertable",6,"阻塞中"),
	
	/**启动中，不接受任务		7 */
//	STARTUP("Startup",7,"启动中"),
	
	/**取消中，不接受任务		8 */
//	CANCEL("Cancel",8,"取消中"),
	
	/**暂停中，不接受任务		9 */
//	PAUSE("Pause",9,"暂停中"),
	
	/**恢复中，不接受任务		10 */
//	RESUME("Resume",10,"恢复中"),
	
	/**警告状态，可能回出现错误，不接受任务		11 */
//	WARNING("Warning",11,"警告状态"),
	
	/**最终状态，可以任务是出错完成，接受任务		12 */
	FINALFUNCTION("FinalFunction",12,"最终状态"),
	
	/**正常完成，接受任务		13 */
	COMPLETED("Completed",13,"正常完成"),
	
	/**正常取消，接受任务		14 */
	TERMINATED("Terminated",14,"正常取消"),
	
	/**错误完成，接受任务		15 */
	ERROR("Error",15,"错误完成");
	
	
	private String name;
	private int value; 
	private String description;
	
	

	private NaviStateEnum(String name, int value, String description) {
		this.name = name;
		this.value = value;
		this.description = description;
	}



	public String getName() {
		return name;
	}



	public int getValue() {
		return value;
	}



	public String getDescription() {
		return description;
	}



	public static NaviStateEnum get(int value){
		NaviStateEnum[] values =NaviStateEnum.values();
		for(int i=0;i<values.length;i++){
			if(values[i].value==value){
				return values[i];
			}
		}
		return null;
	}

	public static String getDescription(int value){
		NaviStateEnum[] values =NaviStateEnum.values();
		for(int i=0;i<values.length;i++){
			if(values[i].value==value){
				return values[i].getDescription();
			}
		}
		return null;
	}
}
