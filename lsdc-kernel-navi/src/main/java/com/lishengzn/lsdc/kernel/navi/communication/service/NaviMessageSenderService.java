package com.lishengzn.lsdc.kernel.navi.communication.service;


import com.lishengzn.common.communication.service.MessageSenderService;

public interface NaviMessageSenderService extends MessageSenderService {

    /**
     * 查询机器人信息
     * 数据区长度为0, 只有包头:
     */
    void queryVehicleInfo();

    /**
     * 查询机器人运行信息
     */
    void queryRuningInfo();

    /**
     * 查询机器人位置
     */
    void queryLocation();

    /**
     * 查询机器人速度
     */
    void querySpeed();

    /**
     * 查询机器人的被阻挡状态
     */
    void queryBlockStatus();

    /**
     * 查询机器人电池状态
     */
    void queryBatteryStatus();

    /**
     * 查询机器人抱闸状态
     */
    void queryBrakeStatus();

    /**
     * 查询机器人激光点云数据
     */
    void queryLaserInfo();

    /**
     * 查询机器人自由导航路径数据
     */
    void queryFreeNaviPath();

    /**
     * 查询机器人当前所在区域
     */
    void queryCurrentArea();

    /**
     * 查询机器人急停状态
     */
    void queryEmergencyStopStatus();

    /**
     * 查询机器人 I/O 数据
     */
    void queryIOInfo();

    /**
     * 查询机器人 IMU 数据
     */
    void queryIMUInfo();

    /**
     * 查询 RFID 数据
     */
    void queryRFIDInfo();

    /**
     * 查询机器人的超声传感器数据
     */
    void queryUltrasonicInfo();

    /**
     * 查询机器人导航状态
     * @param simple 是否只返回简单数据, true = 是，false = 否，缺省则为否
     */
    void queryNaviStatus(boolean simple);

    /**
     * 查询机器人定位状态
     */
    void queryRelocStatus();

    /**
     * 查询机器人地图载入状态
     */
    void queryLoadMapStatus();

    /**
     * 查询机器人扫图状态
     */
    void querySlamStatus();

    /**
     * 查询顶升机构状态
     */
    void queryJackStatus();

    /**
     * 查询辊筒(皮带)状态
     */
    void queryRollerStatus();

    /**
     * 查询机器人当前的调度状态
     */
    void queryDispathStatus();

    /**
     * 查询机器人告警状态
     */
    void queryAlarmStatus();

    /**
     * 查询批量数据1
     * all1 是为了通过一个请求批量获取之前提到的大部分状态数据,
     * 包括 1002, 1004, 1005, 1006, 1007, 1008, 1009, 1010, 1011, 1012, 1013, 1014, 1015, 1016, 1020, 1021, 1022, 1025, 1027, 1028, 1029, 1030, 1050
     * @param return_laser 是否返回激光书数据（即1009的数据），true = 返回，false = 不返回，缺省则为返回
     */
    void queryAll1(boolean return_laser);
    /**
     * 查询批量数据2
     * all2 是为了通过一个请求批量获取更新比较频繁或比较实时的数据(如位置, 速度等),
     * 包括 1004, 1005, 1006, 1008, 1009,  1010, 1011, 1012, 1013, 1014, 1016, 1020, 1050
     * @param return_laser 是否返回激光书数据（即1009的数据），true = 返回，false = 不返回，缺省则为返回
     */
    void queryAll2(boolean return_laser);

    /**
     * 查询批量数据3
     * all3 是为了通过一个请求批量获取更新不频繁的状态数据,
     * 包括 1002, 1007, 1021, 1022, 1025
     */
    void queryAll3();

    /**
     * 查询机器人载入的地图以及储存的地图
     */
    void queryMapInfo();

    /**
     * 查询机器人当前载入地图中的站点信息
     */
    void queryStationInfo();

    /**
     * 查询机器人参数
     * @param plugin 参数所属的插件名, 如果缺省, 表示查询所有插件的所有参数
     * @param param 参数名, 如果 plugin 存在，但 param 缺省，代表查询该插件的所有参数。否侧查询该插件的指定参数
     */
    void queryVehicleParams(String plugin, String param);

}
