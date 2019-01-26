package com.lishengzn.common.constants;

public interface SocketConstants {
    /** 同步包头 ，固定字节*/
    public static final short SYNC_HEAD =0x5A;

    /** 计算回包类型的加值 */
    public static final short RESPONSE_PACKET_ADDED =10000;

    /* API status端口用到的packetType===start*/
    /** 查询机器人信息 */
    public static final short PACKET_TYPE_STATUS_API_VEHICLE_INFO = 1000;

    /** 查询机器人的运行状态信息(如运行时间、里程等) */
    public static final short PACKET_TYPE_STATUS_API_RUN = 1002;

    /** 查询机器人位置 */
    public static final short PACKET_TYPE_STATUS_API_LOC = 1004;

    /** 查询机器人速度 */
    public static final short PACKET_TYPE_STATUS_API_SPEED = 1005;

    /** 查询机器人被阻挡状态 */
    public static final short PACKET_TYPE_STATUS_API_BLOCK = 1006;

    /** 查询机器人电池状态 */
    public static final short PACKET_TYPE_STATUS_API_BATTERY = 1007;

    /** 查询机器人抱闸状态 */
    public static final short PACKET_TYPE_STATUS_API_BRAKE = 1008;

    /** 查询机器人激光点云数据 */
    public static final short PACKET_TYPE_STATUS_API_LASER = 1009;

    /** 查询机器人路径数据 */
    public static final short PACKET_TYPE_STATUS_API_PATH = 1010;

    /** 查询机器人当前所在区域 */
    public static final short PACKET_TYPE_STATUS_API_AREA = 1011;

    /** 查询机器人急停状态 */
    public static final short PACKET_TYPE_STATUS_API_EMERGENCY = 1012;

    /** 查询机器人 I/O 数据 */
    public static final short PACKET_TYPE_STATUS_API_IO = 1013;

    /** 查询机器人 IMU 数据 */
    public static final short PACKET_TYPE_STATUS_API_IMU = 1014;

    /** 查询 RFID 数据 */
    public static final short PACKET_TYPE_STATUS_API_RFID = 1015;

    /** 查询机器人的超声传感器数据 */
    public static final short PACKET_TYPE_STATUS_API_ULTRASONIC = 1016;

    /** 查询机器人导航状态, 导航站点, 导航相关路径等 */
    public static final short PACKET_TYPE_STATUS_API_NAVI_STATUS = 1020;

    /** 查询机器人重定位状态 */
    public static final short PACKET_TYPE_STATUS_API_RELOC = 1021;

    /** 查询机器人地图载入状态 */
    public static final short PACKET_TYPE_STATUS_API_LOADMAP = 1022;

    /** 查询机器人扫图状态 */
    public static final short PACKET_TYPE_STATUS_API_SLAM = 1025;

    /** 查询顶﻿升机构状态 */
    public static final short PACKET_TYPE_STATUS_API_JACK = 1027;

    /** 查询货叉(叉车)状态 */
    public static final short PACKET_TYPE_STATUS_API_FORK = 1028;

    /** 查询辊筒(皮带)状态 */
    public static final short PACKET_TYPE_STATUS_API_ROLLER = 1029;

    /** 查询机器人当前的调度状态 */
    public static final short PACKET_TYPE_STATUS_API_DISPATCH = 1030;

    /** 查询机器人告警状态 */
    public static final short PACKET_TYPE_STATUS_API_ALARM = 1050;

    /** 查询批量数据1 */
    public static final short PACKET_TYPE_STATUS_API_ALL1 = 1100;

    /** 查询批量数据2 */
    public static final short PACKET_TYPE_STATUS_API_ALL2 = 1101;

    /** 查询批量数据3 */
    public static final short PACKET_TYPE_STATUS_API_ALL3 = 1102;

    /** 查询机器人载入的地图以及储存的地图 */
    public static final short PACKET_TYPE_STATUS_API_MAP = 1300;

    /** 查询机器人当前载入地图中的站点信息 */
    public static final short PACKET_TYPE_STATUS_API_STATION = 1301;

    /** 查询机器人参数 */
    public static final short PACKET_TYPE_STATUS_API_PARAMS = 1400;
    /* API status端口用到的packetType===end*/

    /* 机器人导航端口用到的packetType===start*/

    /** 暂停当前导航 */
    public static final short PACKET_TYPE_NAVI_PAUSE = 3001 ;

    /** 继续当前导航 */
    public static final short PACKET_TYPE_NAVI_RESUME = 3002 ;

    /** 取消当前导航 */
    public static final short PACKET_TYPE_NAVI_CANCLE = 3003 ;

    /** 路径导航 */
    public static final short PACKET_TYPE_NAVI_GOTARGET = 3051 ;

    /** 获取导航路径 */
    public static final short PACKET_TYPE_NAVI_TARGET_PATH = 3053 ;

    /** 平动 */
    public static final short PACKET_TYPE_NAVI_TRANSLATE = 3055 ;

    /** 转动 */
    public static final short PACKET_TYPE_NAVI_TURN = 3056 ;

    /* 机器人导航端口用到的packetType===end*/

    /* 机器人配置端口用到的packetType===start*/
    /** 从机器人下载地图*/
    public static final short PACKET_TYPE_OTHER_DOWNLOAD_MAP = 4011  ;
    /* 其它端口用到的packetType===end*/


    /* 其它端口用到的packetType===start*/
    /** 转动 */
    public static final short PACKET_TYPE_OTHER_SETDO = 6001 ;

    /* 其它端口用到的packetType===end*/

    public static final int SERVER_PORT_STATUS_API = 19204;
    public static final int SERVER_PORT_VEHICLE_CONTROL = 19205;
    public static final int SERVER_PORT_VEHICLE_NAVI = 19206;
    public static final int SERVER_PORT_VEHICLE_CONFIG = 19207;
    public static final int SERVER_PORT_OTHER = 19210;


}
