package com.hb.file.dispatch.service;

import com.hb.file.dispatch.entity.TaskPriorityModel;

import java.util.List;

public interface TaskPriorityService {

    /**
     * 查询渠道优先级最高的任务
     *
     * @param channel 渠道
     * @param status  状态
     * @param num     数量
     * @return
     */
    List<TaskPriorityModel> getPriorityTasks(String channel, Integer status, Integer num);

    void addPriorityTask(TaskPriorityModel model);

    void delPriorityTask(String streamingNo);
}
