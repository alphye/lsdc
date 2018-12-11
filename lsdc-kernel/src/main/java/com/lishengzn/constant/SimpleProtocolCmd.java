package com.lishengzn.constant;

public class SimpleProtocolCmd {

    /** X行走 10mm */
    public static final String TRAVEL_10_X = "0101";

    /** X行走 -10mm */
    public static final String TRAVELBACK_10_X = "0100";

    /** Y行走 10mm */
    public static final String TRAVEL_10_Y = "0201";

    /** Y行走 -10mm */
    public static final String TRAVELBACK_10_Y = "0200";

    /** 读取平台状态 */
    public static final String PLATFORM_STATUS = "030E";

    /** 设置原点并返回原点 */
    public static final String SET_ORIGIN_AND_ARRIVE = "040E";

    /** 读取当前位置 */
    public static final String GET_POSITION = "050E";

    /** 读取X速度 */
    public static final String GET_VELOCITY_X = "0601";

    /** 读取Y速度 */
    public static final String GET_VELOCITY_Y = "0600";

    /** 清除X驱动器告警 */
    public static final String CLEAR_DRIVER_WARNING_X = "0701";

    /** 清除Y驱动器告警 */
    public static final String CLEAR_DRIVER_WARNING_Y = "0702";

    /** 读取当前位置 */
    public static final String GET_CURR_POSITION = "080E";

    /** 读取目标位置 */
    public static final String GET_TARGET_POSITION = "090E";


    /** 软件急停 */
    public static final String EMERGENCY_STOP = "1001";

    /** 清除软件急停 */
    public static final String CLEAR_EMERGENCY_STOP = "1002";

    /** 导航到目的地 */
    public static final String SEND_TO_POINT_0 = "1000";



}
