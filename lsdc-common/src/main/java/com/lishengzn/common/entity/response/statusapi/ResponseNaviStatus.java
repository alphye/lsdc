package com.lishengzn.common.entity.response.statusapi;

import com.lishengzn.common.entity.response.ResponseEntity;

import java.util.List;

public class ResponseNaviStatus extends ResponseEntity {
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
    private String target_id;

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

    public String getTarget_id() {
        return target_id;
    }

    public void setTarget_id(String target_id) {
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
}
