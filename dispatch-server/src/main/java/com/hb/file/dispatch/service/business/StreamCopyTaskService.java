package com.hb.file.dispatch.service.business;

import com.hb.file.core.commons.CommonConstant;
import com.hb.file.core.enums.TaskStatusEnum;
import com.hb.file.dispatch.entity.DispatchTaskModel;
import com.hb.file.dispatch.entity.TaskPriorityModel;
import com.hb.file.dispatch.request.DispatchTaskAddRequest;
import com.hb.file.dispatch.service.DispatchTaskService;
import com.hb.file.dispatch.service.TaskPriorityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class StreamCopyTaskService {
    @Resource
    private DispatchTaskService dispatchTaskService;
    @Resource
    private TaskPriorityService taskPriorityService;
    @Resource
    private RedisIdWorker redisIdWorker;


    public void initTask(DispatchTaskAddRequest request) {
        DispatchTaskModel dispatchTaskModel = new DispatchTaskModel();
        BeanUtils.copyProperties(request, dispatchTaskModel);

        String streamingNo = String.valueOf(redisIdWorker.nextId(dispatchTaskModel.getChannel()));
        dispatchTaskModel.setStreamingNo(streamingNo);
        if (dispatchTaskService.save(dispatchTaskModel)) {

            TaskPriorityModel priorityModel = new TaskPriorityModel();
            BeanUtils.copyProperties(request, priorityModel);
            priorityModel.setStreamingNo(streamingNo);
            priorityModel.setPriority(CommonConstant.DEFAULT_PRIORITY);
            priorityModel.setGroup(CommonConstant.DEFAULT_GROUP);
            priorityModel.setGroupOrder(CommonConstant.DEFAULT_GROUP_ORDER);
            priorityModel.setStatus(TaskStatusEnum.WAITING.getStatus());
            taskPriorityService.addPriorityTask(priorityModel);
        }
    }
}
