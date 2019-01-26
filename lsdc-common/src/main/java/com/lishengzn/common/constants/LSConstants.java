package com.lishengzn.constants;

public interface LSConstants {
	/** 0.包类型转应答包类型时候，加的值 */
	static final int PACKET_TYPE_ADDED=0x80000000;
	
	/** 1.登录的包类型 */
	static final int PACKET_TYPE_LOGIN=0x00000101;
	
	/** 2.读取变量的包类型 */
	static final int PACKET_TYPE_READVAR=0x00000104;
	
	/** 3.写变量的包类型 */
	static final int PACKET_TYPE_WRITEVAR=0x00000105;

	/** 4.心跳包的包类型 */
	static final int PACKET_TYPE_HEART=0x00000106;
	
	/** 5.登录串的包类型 */
	static final int PACKET_TYPE_LOGIN_STR=0x00000107;
	
	/** 6.状态上报的包类型 */
	static final int PACKET_TYPE_UP_STATE=0x00000108;
	
	/** 7.发送导航任务的包类型 */
	static final int PACKET_TYPE_SEND_NAVITASK=0x00000201;
	
	/** 8.取消导航任务的包类型 */
	static final int PACKET_TYPE_CANCLE_NAVITASK=0x00000202;
	
	/**9.暂停导航任务的包类型 */
	static final int PACKET_TYPE_PAUSE_NAVITASK=0x00000203;
	
	/** 10.恢复导航任务的包类型 */
	static final int PACKET_TYPE_RECOVER_NAVITASK=0x00000204;
	
	/** 11.查询导航轨迹的包类型 */
	static final int PACKET_TYPE_QUERY_NAVITRAILS=0x00000206;
	
	/** 12.追加导航的包类型 */
	static final int PACKET_TYPE_APPEND_NAVITASK=0x00000207;
	
	/** 13.发送操作任务的包类型 */
	static final int PACKET_TYPE_SEND_OPRTNTASK=0x0000020A;
	
	/** 14.取消操作任务的包类型 */
	static final int PACKET_TYPE_CANCLE_OPRTNTASK=0x0000020B;
	
	/** 15.暂停操作任务的包类型 */
	static final int PACKET_TYPE_PAUSE_OPRTNTASK=0x0000020C;
	
	/** 16.继续操作任务的包类型 */
	static final int PACKET_TYPE_RECOVER_OPRTNTASK=0x0000020D;
	
	/** 17.清除错误的包类型 */
	static final int PACKET_TYPE_CLEAR_ERROR=0x00000403;
	
	/** 状态类型:导航状态 */
	static final int STATE_TYPE_NAVI=0x00000001;
	
	/** 状态类型:操作状态 */
	static final int STATE_TYPE_OPRT=0x00000003;
	
	/**
	 *变量ID如下 
	 **/
	/** 变量ID：读取当前导航状态及位置信息  */
	static final int VARID_POSITOIN=1;
	/** 变量ID：读取本体是否开启安全防护或者有故障，以及本体的速度和里程 */
	static final int VARID_VELOCITY=2;
	/** 变量ID：读取AGV充电状态 */
	static final int VARID_CHARGESTATE=3;
	/** 变量ID：如果AGV本体能够检测包裹，需要提供读取AGV是否有包裹变量 */
	static final int VARID_CHECKPACKAGE=4;
	/** 变量ID：如果AGV本体是通过翻盖来卸包裹，需要提供变量 */
	static final int VARID_FLIP_STATE=5;
	/** 变量ID：如果AGV是顶升，需要提供当前顶升距离 */
	static final int VARID_JACKING_DISTANCE=6;
	/** 变量ID：如果AGV是皮带转动，需要提供：正在转动、停止转动信息 */
	static final int VARID_BELT_ROTATION_STATE=7;
	/** 变量ID：读取当前操作状态，操作状态包括：操作已完成、操作进行中、操作出错等 */
	static final int VARID_OPERATION_STATE=8;
	/** 变量ID：读取货柜状态 */
	static final int VARID_CONTAINER_STATE=8;
	/** 变量ID：读取AGV电池容量 */
	static final int VARID_BATTERYCAPACITY=9;
	/** 变量ID：读取AGV电池容量 */
	static final int VARID_STOPCHARGE=10;
	/** 变量ID：写入包裹大小 */
	static final int VARID_PACKAGESIZE=11;
	/** 变量ID：读取托盘状态 */
	static final int VARID_SALVER_STATE=12;
	
	
	/**
	 *变量类型如下 
	 **/
	/** 变量类型：读取当前导航状态及位置信息  */
	static final int VARTYPE_POSITOIN=0x1;
	/** 变量类型：读取本体是否开启安全防护或者有故障，以及本体的速度和里程 */
	static final int VARTYPE_VELOCITY=0x2;
	/** 变量类型：读取AGV充电状态 */
	static final int VARTYPE_CHARGESTATE=0x9;
	/** 变量类型：如果AGV本体能够检测包裹，需要提供读取AGV是否有包裹变量 */
	static final int VARTYPE_CHECKPACKAGE=0xc7;
	/** 变量类型：如果AGV本体是通过翻盖来卸包裹，需要提供变量 */
	static final int VARTYPE_FLIP_STATE=0xc7;
	/** 变量类型：如果AGV是顶升，需要提供当前顶升距离 */
	static final int VARTYPE_JACKING_DISTANCE=0x84;
	/** 变量类型：如果AGV是皮带转动，需要提供：正在转动、停止转动信息 */
	static final int VARTYPE_BELT_ROTATION_STATE=0x3;
	/** 变量类型：读取当前操作状态，操作状态包括：操作已完成、操作进行中、操作出错等 */
	static final int VARTYPE_OPERATION_STATE=0x3;
	/** 变量类型：读取货柜状态 */
	static final int VARTYPE_CONTAINER_STATE=0x3;
	/** 变量类型：读取托盘状态 */
	static final int VARTYPE_SALVER_STATE=0x3;

	/** 变量类型：读取AGV电池容量 */
	static final int VARTYPE_BATTERYCAPACITY=0x9;
	/** 变量ID：读取AGV电池容量 */
	static final int VARTYPE_STOPCHARGE=0x9;
	/** 变量ID：写入包裹大小 */
	static final int VARTYPE_PACKAGESIZE=0xc7;
	
	
	/** 错误码：成功 */
	static final int ERROR_CODE_SUCCESS=0;
	
	/** 错误码：失败 */
	static final int ERROR_CODE_FAILED=1;
	
	
	/**操作码定义如下*/
	
	/**翻盖翻起**/
	static final int OPERATIONCODE_FLIP_OVER= 0x001;
	/**翻盖收回**/
	static final int OPERATIONCODE_FLIP_BACK= 0x002;
	/**翻盖翻起后收回 ，接收⼀个操作数：翻盖翻起后停留时间**/
	static final int OPERATIONCODE_FLIP_OVERANDBACK= 0x007;
	/**顶起，接收⼀个操作数：顶起高度**/
	static final int OPERATIONCODE_JACKING= 0x010;
	/**顶起收回**/
	static final int OPERATIONCODE_JACKING_BACK= 0x011;
	/**皮带输送线转动，接收两个操作数：正/反转、转动哪个皮带**/
	static final int OPERATIONCODE_BELT_ROTATION_ON= 0x021;
	/**皮带输送线停止转动，接收⼀个操作数：停止转动哪个皮带**/
	static final int OPERATIONCODE_BELT_ROTATION_STOP= 0x022;
	/**皮带输送线转动后再停止转动，接收3个操作数：正/反转、转动哪个皮带、 转动时间**/
	static final int OPERATIONCODE_BELT_ROTATION_ONANDSTOP= 0x023;
	/**AGV转动货柜朝向，接收⼀个操作数：货柜主货柜最终朝向**/
	static final int OPERATIONCODE_CONTAINER_DIRECTION= 0x031;
	
	/** 控制充电--开始充电*/
	static final int CONTROLCHARGE_START= 0x2;
	/** 控制充电--停止充电*/
	static final int CONTROLCHARGE_STOP= 0x0;
	
}
