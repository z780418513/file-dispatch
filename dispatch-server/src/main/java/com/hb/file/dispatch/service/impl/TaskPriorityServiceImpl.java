package com.hb.file.dispatch.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hb.file.dispatch.entity.TaskPriorityModel;
import com.hb.file.dispatch.mapper.TaskPriorityMapper;
import com.hb.file.dispatch.service.TaskPriorityService;
import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Service
public class TaskPriorityServiceImpl implements TaskPriorityService {
    @Resource
    private TaskPriorityMapper taskPriorityMapper;

    @Override
    public List<TaskPriorityModel> getPriorityTasks(String channel, Integer status, Integer num) {
        TaskPriorityModel priorityModel = taskPriorityMapper.selectOne(Wrappers.<TaskPriorityModel>lambdaQuery()
                .eq(TaskPriorityModel::getChannel, channel)
                .eq(TaskPriorityModel::getStatus, status)
                .orderByAsc(TaskPriorityModel::getPriority)
                .orderByAsc(TaskPriorityModel::getCreateTime)
                .last(" limit 1 "));
        if (Objects.isNull(priorityModel)) {
            return Lists.newArrayList();
        }
        return taskPriorityMapper.selectList(Wrappers.<TaskPriorityModel>lambdaQuery()
                .eq(TaskPriorityModel::getChannel, channel)
                .eq(TaskPriorityModel::getStatus, status)
                .eq(TaskPriorityModel::getPriority, priorityModel.getPriority())
                .eq(TaskPriorityModel::getGroup, priorityModel.getGroup())
                .orderByAsc(TaskPriorityModel::getGroupOrder)
                .last(" LIMIT " + num));

    }

    @Override
    public void addPriorityTask(TaskPriorityModel model) {
        taskPriorityMapper.insert(model);
    }

    @Override
    public void delPriorityTask(String streamingNo) {
        taskPriorityMapper.delete(Wrappers.<TaskPriorityModel>lambdaQuery()
                .eq(TaskPriorityModel::getStreamingNo, streamingNo));
    }
}
