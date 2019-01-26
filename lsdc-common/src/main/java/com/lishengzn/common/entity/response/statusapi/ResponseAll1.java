package com.lishengzn.common.entity.response.statusapi;

import com.lishengzn.common.entity.response.ResponseEntity;

import java.util.List;
import java.util.Map;

public class ResponseAll1 extends ResponseEntity {
    /**累计行驶里程, 单位 m; 可缺省：是*/
    private double odo;

    /**本次运行时间(开机后到当前的时间), 单位 ms; 可缺省：是*/
    private long time;

    /**累计运行时间, 单位 ms; 可缺省：是*/
    private long total_time;

    /**控制器温度, 单位 ℃; 可缺省：是*/
    private double controller_temp;

    /**控制器湿度, 单位 %; 可缺省：是*/
    private double controller_humi;

    /**控制器电压, 单位 V; 可缺省：是*/
    private double controller_voltage;
    /**机器人的 x 坐标, 单位 m; 可缺省：是*/
    private double x;

    /**机器人的 y 坐标, 单位 m; 可缺省：是*/
    private double y;

    /**机器人的 angle 坐标, 单位 rad; 可缺省：是*/
    private double angle;

    /**机器人的定位置信度, 范围 [0, 1]; 可缺省：是*/
    private double confidence;

    /**机器人当前所在站点的 ID（该判断比较严格，机器人必须很靠近某一个站点(<30cm)，否则为空字符，即不处于任何站点）; 可缺省：是*/
    private String current_station;
    /**机器人在机器人坐标系的 x 方向实际的速度, 单位 m/s; 可缺省：是*/
    private double vx;

    /**机器人在机器人坐标系的 y 方向实际的速度, 单位 m/s; 可缺省：是*/
    private double vy;

    /**机器人在机器人坐标系的实际的角速度(即顺时针转为负, 逆时针转为正), 单位 rad/s; 可缺省：是*/
    private double w;

    /**单舵轮机器人当前的舵轮角度 rad; 可缺省：是*/
    private double steer;

    /**托盘机器人的托盘角度 rad; 可缺省：是*/
    private double spin;

    /**机器人在机器人坐标系的 x 方向接收到的速度, 单位 m/s; 可缺省：是*/
    private double r_vx;

    /**机器人在机器人坐标系的 y 方向收到的速度, 单位 m/s; 可缺省：是*/
    private double r_vy;

    /**机器人在机器人坐标系的收到的角速度(即顺时针转为负, 逆时针转为正), 单位 rad/s; 可缺省：是*/
    private double r_w;

    /**单舵轮机器人收到的舵轮角度 rad; 可缺省：是*/
    private double r_steer;

    /**托盘机器人的收到托盘转动速度 rad/s; 可缺省：是*/
    private double r_spin;
    /**机器人是否被阻挡; 可缺省：是*/
    private boolean blocked;

    /**被阻挡的原因, 0 = Ultrasonic (超声检测到被阻挡), 1 = Laser (激光检测到被阻挡), 2 = Fallingdown (防跌落传感器检测到被阻挡),
     *  3 = Collision (碰撞传感器检测到被阻挡), 4 = Infrared (红外传感器检测到被阻挡), 5 = Lock（锁车开关），6 = 动态障碍物，
     *  7 = 虚拟激光点;
     *  可缺省：是*/
    private int block_reason;

    /**最近障碍物位置的 x 坐标, 单位 m; 可缺省：是*/
    private double block_x;

    /**最近障碍物位置的 y 坐标, 单位 m; 可缺省：是*/
    private double block_y;

    /**发生阻挡的超声 id 号, 仅当 block_reason = 0 时有意义; 可缺省：是*/
    private int block_ultrasonic_id;

    /**发生阻挡的 DI 的 id 号, 仅当 block_reason = 2、3、4 时有意义; 可缺省：是*/
    private int block_di;
    /**机器人电池电量, 范围 [0, 1]; 可缺省：是*/
    private double battery_level;

    /**机器人电池温度, 单位 ℃; 可缺省：是*/
    private double battery_temp;

    /**电池是否正在充电; 可缺省：是*/
    private boolean charging;

    /**电压, 单位 V; 可缺省：是*/
    private double voltage;

    /**电流, 单位 A; 可缺省：是*/
    private double current;

    /**是否在手动充电(仅SRC-2000支持); 可缺省：是*/
    private boolean manual_charge;

    /**是否在自动充电(仅SRC-2000支持); 可缺省：是*/
    private boolean auto_charge;

    /**电池循环次数; 可缺省：是*/
    private int battery_cycle;

    /**机器人是否抱闸, 如果该字段不存在, 说明机器人没有抱闸功能; 可缺省：是*/
    private boolean brake;

    /** 激光点云数据*/
    private List<LasersData> lasers;

    /** 机器人所在区域 id 的数组(由于地图上的区域是可以重叠的, 所以机器人可能同时在多个区域)，数组可能为空*/
    private List<String> area_ids;

    /**true 表示急停按钮处于激活状态(按下), false 表示急停按钮处于非激活状态(拔起); 可缺省：是*/
    private boolean emergency;

    /**true 表示驱动器发生急停, false 驱动器发生未急停; 可缺省：是*/
    private boolean driver_emc;

    /**DI 数据, boolean 表示高低电平, 从 0 开始; 可缺省：是*/
    private List<Boolean> DI;

    /**DO 数据, boolean 表示高低电平, 从 0 开始; 可缺省：是*/
    private List<Boolean> DO;

    /**DI 对应位置是否启动, false = 禁用, true = 启用; 可缺省：是*/
    private List<Boolean> DI_valid;

    /**偏航角，单位：rad; 可缺省：是*/
    private double yaw;

    /**滚转角，单位：rad; 可缺省：是*/
    private double roll;

    /**俯仰角，单位：rad; 可缺省：是*/
    private double pitch;


    /** 扫描到的所有 RFID 标签 id, 如果没扫描到 RFID 标签, 则为空数组*/
    private List<Integer> rfids;

    List<UltrasonicNode> ultrasonic_nodes;

    /**0 = NONE, 1 = WAITING, 2 = RUNNING, 3 = SUSPENDED, 4 = COMPLETED, 5 = FAILED, 6 = CANCELED; 可缺省：是*/
    private int task_status;

    /**导航类型,
     * 0 = 没有导航, 1 = 自由导航到任意点, 2 = 自由导航到站点,
     * 3 = 路径导航到站点, 5 = 钻货架, 6 = 跟随,
     * 7 = 平动转动, 8 = 磁条导航, 100 = 其他;
     * 可缺省：是*/
    private int task_type;

    /**当前导航要去的站点,
     * 仅当 task_type 为 2 或 3 时该字段有效,
     * task_status 为 RUNNING 时说明正在去这个站点, t
     * ask_status 为 COMPLETED 时说明已经到达这个站点, t
     * ask_status 为 FAILED 时说明去这个站点失败,
     * task_status 为 SUSPENDED 说明去这个站点的导航暂停;
     * 可缺省：是*/
    private int target_id;

    /**当前导航要去的坐标点, 为一个包含三个元素的数组, 分别为在世界坐标系中的 x, y, r 坐标,
     * 仅当 task_type 为 1 时该字段有效,
     * task_status 为 RUNNING 时说明正在去这个坐标点,
     * task_status 为 COMPLETED 时说明已经到达这个坐标点,
     * task_status 为 FAILED 时说明去这个坐标点失败,
     * task_status 为 SUSPENDED 说明去这个坐标点的导航暂停; 可缺省：是*/
    private List<Double> target_point;

    /**当前导航路径上已经经过的站点, 为站点的数组,
     * 仅当 task_type 为 3 或 4 时该字段有效点。如果是路径导航， 这里会列出所有已经经过的中间点;
     * 可缺省：是*/
    private List<String> finished_path;

    /**当前导航路径上尚未经过的站点, 为站点的数组,
     * 仅当 task_type 为 3 或 4 时该字段有效。如果是路径导航， 这里会列出所有尚未经过的中间点;
     * 可缺省：是*/
    private List<String> unfinished_path;

    /** 0 = FAILED(定位失败), 1 = SUCCESS(定位正确), 2 = RELOCING(正在重定位), 3=COMPLETED(定位完成)*/
    private int reloc_status;

    /** 0 = FAILED(载入地图失败), 1 = SUCCESS(载入地图成功), 2 = LOADING(正在载入地图)*/
    private int loadmap_status;

    /** 0 = 没有扫图, 1 = 正在扫图, 2 = 正在实时扫图*/
    private int slam_status;

    /**系统状态, 0=急停状态, 1=正常运行; 可缺省：是*/
    private int jack_mode;

    /**顶升状态, 0=上升中, 1=上升到位, 2=下降中, 3=下降到位, 4=停止; 可缺省：是*/
    private int jack_state;

    /**是否启用且连接成功; 可缺省：是*/
    private boolean jack_enable;

    /**错误码; 可缺省：是*/
    private int jack_error_code;

    /**顶升机构上是否有料, true = 有料, false = 空; 可缺省：是*/
    private boolean jack_isFull;

    /**顶升机构电机速率, 取值范围 30 ~ 100 超过 100 按 100 计算，低于 30 按 30 计算; 可缺省：*/
    private double jack_speed_level;

    /**左上料标志位, true = 正在左上料, false = 上料完成, 从 true 变为 false, 且roller_error_code 为 0, 表示上料完成; 可缺省：是*/
    private boolean roller_left_load;

    /**左下料标志位, true = 正在左下料, false = 下料完成, 从 true 变为 false, 且roller_error_code 为 0, 表示下料完成; 可缺省：是*/
    private boolean roller_left_unload;

    /**右上料标志位, true = 正在右上料, false = 上料完成, 从 true 变为 false, 且roller_error_code 为 0, 表示上料完成; 可缺省：是*/
    private boolean roller_right_load;

    /**右下料标志位, true = 正在右下料, false = 下料完成, 从 true 变为 false, 且roller_error_code 为 0, 表示下料完成; 可缺省：是*/
    private boolean roller_right_unload;

    /**是否启用且连接成功; 可缺省：是*/
    private boolean roller_enable;

    /**错误码; 可缺省：是*/
    private int roller_error_code;

    /**辊筒或皮带上是否有料, true = 有料, false = 空; 可缺省：是*/
    private boolean roller_isFull;

    /**光电管 1 信号; 可缺省：是*/
    private boolean roller_sig1;

    /**光电管 2 信号; 可缺省：是*/
    private boolean roller_sig2;

    /**光电管 3 信号; 可缺省：是*/
    private boolean roller_sig3;

    /**辊筒的速度等级, 0 = 100%, 1 = 75%, 2 = 50%; 可缺省：是*/
    private int roller_speed_level;

    /** 0 = 单机模式, 1 = 调度模式, 2 = 调度模式但与调度系统失去连接*/
    private int dispatch_mode;


    /**告警码 Fatal 的数组, 所有出现的 Fatal 告警都会出现在数据中, object 格式见下文; 可缺省：是*/
    private List<Map> atals;

    /**告警码 Error 的数组, 所有出现的 Error 告警都会出现在数据中, object 格式见下文; 可缺省：是*/
    private List<Map> errors;

    /**告警码 Warning 的数组, 所有出现的 Warning 告警都会出现在数据中, object 格式见下文; 可缺省：是*/
    private List<Map> warnings;

    /**告警码 Warning 的数组, 所有出现的 Notice 告警都会出现在数据中, object 格式见下文; 可缺省：是*/
    private List<Map> notices;

    public double getOdo() {
        return odo;
    }

    public void setOdo(double odo) {
        this.odo = odo;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getTotal_time() {
        return total_time;
    }

    public void setTotal_time(long total_time) {
        this.total_time = total_time;
    }

    public double getController_temp() {
        return controller_temp;
    }

    public void setController_temp(double controller_temp) {
        this.controller_temp = controller_temp;
    }

    public double getController_humi() {
        return controller_humi;
    }

    public void setController_humi(double controller_humi) {
        this.controller_humi = controller_humi;
    }

    public double getController_voltage() {
        return controller_voltage;
    }

    public void setController_voltage(double controller_voltage) {
        this.controller_voltage = controller_voltage;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    public String getCurrent_station() {
        return current_station;
    }

    public void setCurrent_station(String current_station) {
        this.current_station = current_station;
    }

    public double getVx() {
        return vx;
    }

    public void setVx(double vx) {
        this.vx = vx;
    }

    public double getVy() {
        return vy;
    }

    public void setVy(double vy) {
        this.vy = vy;
    }

    public double getW() {
        return w;
    }

    public void setW(double w) {
        this.w = w;
    }

    public double getSteer() {
        return steer;
    }

    public void setSteer(double steer) {
        this.steer = steer;
    }

    public double getSpin() {
        return spin;
    }

    public void setSpin(double spin) {
        this.spin = spin;
    }

    public double getR_vx() {
        return r_vx;
    }

    public void setR_vx(double r_vx) {
        this.r_vx = r_vx;
    }

    public double getR_vy() {
        return r_vy;
    }

    public void setR_vy(double r_vy) {
        this.r_vy = r_vy;
    }

    public double getR_w() {
        return r_w;
    }

    public void setR_w(double r_w) {
        this.r_w = r_w;
    }

    public double getR_steer() {
        return r_steer;
    }

    public void setR_steer(double r_steer) {
        this.r_steer = r_steer;
    }

    public double getR_spin() {
        return r_spin;
    }

    public void setR_spin(double r_spin) {
        this.r_spin = r_spin;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public int getBlock_reason() {
        return block_reason;
    }

    public void setBlock_reason(int block_reason) {
        this.block_reason = block_reason;
    }

    public double getBlock_x() {
        return block_x;
    }

    public void setBlock_x(double block_x) {
        this.block_x = block_x;
    }

    public double getBlock_y() {
        return block_y;
    }

    public void setBlock_y(double block_y) {
        this.block_y = block_y;
    }

    public int getBlock_ultrasonic_id() {
        return block_ultrasonic_id;
    }

    public void setBlock_ultrasonic_id(int block_ultrasonic_id) {
        this.block_ultrasonic_id = block_ultrasonic_id;
    }

    public int getBlock_di() {
        return block_di;
    }

    public void setBlock_di(int block_di) {
        this.block_di = block_di;
    }

    public double getBattery_level() {
        return battery_level;
    }

    public void setBattery_level(double battery_level) {
        this.battery_level = battery_level;
    }

    public double getBattery_temp() {
        return battery_temp;
    }

    public void setBattery_temp(double battery_temp) {
        this.battery_temp = battery_temp;
    }

    public boolean isCharging() {
        return charging;
    }

    public void setCharging(boolean charging) {
        this.charging = charging;
    }

    public double getVoltage() {
        return voltage;
    }

    public void setVoltage(double voltage) {
        this.voltage = voltage;
    }

    public double getCurrent() {
        return current;
    }

    public void setCurrent(double current) {
        this.current = current;
    }

    public boolean isManual_charge() {
        return manual_charge;
    }

    public void setManual_charge(boolean manual_charge) {
        this.manual_charge = manual_charge;
    }

    public boolean isAuto_charge() {
        return auto_charge;
    }

    public void setAuto_charge(boolean auto_charge) {
        this.auto_charge = auto_charge;
    }

    public int getBattery_cycle() {
        return battery_cycle;
    }

    public void setBattery_cycle(int battery_cycle) {
        this.battery_cycle = battery_cycle;
    }

    public boolean isBrake() {
        return brake;
    }

    public void setBrake(boolean brake) {
        this.brake = brake;
    }

    public List<LasersData> getLasers() {
        return lasers;
    }

    public void setLasers(List<LasersData> lasers) {
        this.lasers = lasers;
    }

    public List<String> getArea_ids() {
        return area_ids;
    }

    public void setArea_ids(List<String> area_ids) {
        this.area_ids = area_ids;
    }

    public boolean isEmergency() {
        return emergency;
    }

    public void setEmergency(boolean emergency) {
        this.emergency = emergency;
    }

    public boolean isDriver_emc() {
        return driver_emc;
    }

    public void setDriver_emc(boolean driver_emc) {
        this.driver_emc = driver_emc;
    }

    public List<Boolean> getDI() {
        return DI;
    }

    public void setDI(List<Boolean> DI) {
        this.DI = DI;
    }

    public List<Boolean> getDO() {
        return DO;
    }

    public void setDO(List<Boolean> DO) {
        this.DO = DO;
    }

    public List<Boolean> getDI_valid() {
        return DI_valid;
    }

    public void setDI_valid(List<Boolean> DI_valid) {
        this.DI_valid = DI_valid;
    }

    public double getYaw() {
        return yaw;
    }

    public void setYaw(double yaw) {
        this.yaw = yaw;
    }

    public double getRoll() {
        return roll;
    }

    public void setRoll(double roll) {
        this.roll = roll;
    }

    public double getPitch() {
        return pitch;
    }

    public void setPitch(double pitch) {
        this.pitch = pitch;
    }

    public List<Integer> getRfids() {
        return rfids;
    }

    public void setRfids(List<Integer> rfids) {
        this.rfids = rfids;
    }

    public List<UltrasonicNode> getUltrasonic_nodes() {
        return ultrasonic_nodes;
    }

    public void setUltrasonic_nodes(List<UltrasonicNode> ultrasonic_nodes) {
        this.ultrasonic_nodes = ultrasonic_nodes;
    }

    public int getTask_status() {
        return task_status;
    }

    public void setTask_status(int task_status) {
        this.task_status = task_status;
    }

    public int getTask_type() {
        return task_type;
    }

    public void setTask_type(int task_type) {
        this.task_type = task_type;
    }

    public int getTarget_id() {
        return target_id;
    }

    public void setTarget_id(int target_id) {
        this.target_id = target_id;
    }

    public List<Double> getTarget_point() {
        return target_point;
    }

    public void setTarget_point(List<Double> target_point) {
        this.target_point = target_point;
    }

    public List<String> getFinished_path() {
        return finished_path;
    }

    public void setFinished_path(List<String> finished_path) {
        this.finished_path = finished_path;
    }

    public List<String> getUnfinished_path() {
        return unfinished_path;
    }

    public void setUnfinished_path(List<String> unfinished_path) {
        this.unfinished_path = unfinished_path;
    }

    public int getReloc_status() {
        return reloc_status;
    }

    public void setReloc_status(int reloc_status) {
        this.reloc_status = reloc_status;
    }

    public int getLoadmap_status() {
        return loadmap_status;
    }

    public void setLoadmap_status(int loadmap_status) {
        this.loadmap_status = loadmap_status;
    }

    public int getSlam_status() {
        return slam_status;
    }

    public void setSlam_status(int slam_status) {
        this.slam_status = slam_status;
    }

    public int getJack_mode() {
        return jack_mode;
    }

    public void setJack_mode(int jack_mode) {
        this.jack_mode = jack_mode;
    }

    public int getJack_state() {
        return jack_state;
    }

    public void setJack_state(int jack_state) {
        this.jack_state = jack_state;
    }

    public boolean isJack_enable() {
        return jack_enable;
    }

    public void setJack_enable(boolean jack_enable) {
        this.jack_enable = jack_enable;
    }

    public int getJack_error_code() {
        return jack_error_code;
    }

    public void setJack_error_code(int jack_error_code) {
        this.jack_error_code = jack_error_code;
    }

    public boolean isJack_isFull() {
        return jack_isFull;
    }

    public void setJack_isFull(boolean jack_isFull) {
        this.jack_isFull = jack_isFull;
    }

    public double getJack_speed_level() {
        return jack_speed_level;
    }

    public void setJack_speed_level(double jack_speed_level) {
        this.jack_speed_level = jack_speed_level;
    }

    public boolean isRoller_left_load() {
        return roller_left_load;
    }

    public void setRoller_left_load(boolean roller_left_load) {
        this.roller_left_load = roller_left_load;
    }

    public boolean isRoller_left_unload() {
        return roller_left_unload;
    }

    public void setRoller_left_unload(boolean roller_left_unload) {
        this.roller_left_unload = roller_left_unload;
    }

    public boolean isRoller_right_load() {
        return roller_right_load;
    }

    public void setRoller_right_load(boolean roller_right_load) {
        this.roller_right_load = roller_right_load;
    }

    public boolean isRoller_right_unload() {
        return roller_right_unload;
    }

    public void setRoller_right_unload(boolean roller_right_unload) {
        this.roller_right_unload = roller_right_unload;
    }

    public boolean isRoller_enable() {
        return roller_enable;
    }

    public void setRoller_enable(boolean roller_enable) {
        this.roller_enable = roller_enable;
    }

    public int getRoller_error_code() {
        return roller_error_code;
    }

    public void setRoller_error_code(int roller_error_code) {
        this.roller_error_code = roller_error_code;
    }

    public boolean isRoller_isFull() {
        return roller_isFull;
    }

    public void setRoller_isFull(boolean roller_isFull) {
        this.roller_isFull = roller_isFull;
    }

    public boolean isRoller_sig1() {
        return roller_sig1;
    }

    public void setRoller_sig1(boolean roller_sig1) {
        this.roller_sig1 = roller_sig1;
    }

    public boolean isRoller_sig2() {
        return roller_sig2;
    }

    public void setRoller_sig2(boolean roller_sig2) {
        this.roller_sig2 = roller_sig2;
    }

    public boolean isRoller_sig3() {
        return roller_sig3;
    }

    public void setRoller_sig3(boolean roller_sig3) {
        this.roller_sig3 = roller_sig3;
    }

    public int getRoller_speed_level() {
        return roller_speed_level;
    }

    public void setRoller_speed_level(int roller_speed_level) {
        this.roller_speed_level = roller_speed_level;
    }

    public int getDispatch_mode() {
        return dispatch_mode;
    }

    public void setDispatch_mode(int dispatch_mode) {
        this.dispatch_mode = dispatch_mode;
    }

    public List<Map> getAtals() {
        return atals;
    }

    public void setAtals(List<Map> atals) {
        this.atals = atals;
    }

    public List<Map> getErrors() {
        return errors;
    }

    public void setErrors(List<Map> errors) {
        this.errors = errors;
    }

    public List<Map> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<Map> warnings) {
        this.warnings = warnings;
    }

    public List<Map> getNotices() {
        return notices;
    }

    public void setNotices(List<Map> notices) {
        this.notices = notices;
    }
}
