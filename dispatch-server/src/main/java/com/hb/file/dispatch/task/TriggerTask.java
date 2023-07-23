package com.hb.file.dispatch.task;

import com.hb.file.core.enums.BusInessExceptionEnum;
import com.hb.file.core.enums.TaskStatusEnum;
import com.hb.file.core.exception.BusinessException;
import com.hb.file.dispatch.entity.ChannelPlatformRelModel;
import com.hb.file.dispatch.entity.DispatchTaskModel;
import com.hb.file.dispatch.entity.TaskPriorityModel;
import com.hb.file.dispatch.service.ChannelPlatformRelService;
import com.hb.file.dispatch.service.DispatchTaskService;
import com.hb.file.dispatch.service.PlatformConfigService;
import com.hb.file.dispatch.service.TaskPriorityService;
import com.hb.file.dispatch.service.business.ChannelSupportService;
import com.hb.file.dispatch.service.business.stream.StreamCopyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Component
@Slf4j
public class TriggerTask {
    @Resource
    private TaskPriorityService taskPriorityService;
    @Resource
    private ChannelSupportService channelSupportService;
    @Resource
    private DispatchTaskService dispatchTaskService;
    @Resource
    private PlatformConfigService platformConfigService;
    @Resource
    private ChannelPlatformRelService channelPlatformRelService;
    @Resource
    private ThreadPoolTaskExecutor fileTaskExecutor;

    /***
     * 启动上传任务，每10秒续期一次
     */
    @Scheduled(cron = "0/10 * * * * ?")
    public void startTask() {
        Set<String> allChannel = channelSupportService.getAllChannel();
        if (!CollectionUtils.isEmpty(allChannel)) {
            for (String channel : allChannel) {
                List<TaskPriorityModel> toStartTasks =
                        taskPriorityService.getPriorityTasks(channel, TaskStatusEnum.WAITING.getStatus(), 10);
                for (TaskPriorityModel toStartTask : toStartTasks) {
                    fileTaskExecutor.execute(() -> {
                        // 删除优先级记录
                        taskPriorityService.delPriorityTask(toStartTask.getStreamingNo());
                        doStart(toStartTask.getChannel(), toStartTask.getStreamingNo());
                    });
                }
            }
        }
    }


    /**
     * 开始任务
     *
     * @param channel     渠道
     * @param streamingNo 任务号
     */
    private void doStart(String channel, String streamingNo) {
        // 查询任务
        DispatchTaskModel task = dispatchTaskService.queryByStreamingNo(channel, streamingNo);
        if (Objects.nonNull(task)) {
            ChannelPlatformRelModel inputPlatformRel = channelPlatformRelService.queryByPlatformId(channel, task.getInputPlatformId());
            ChannelPlatformRelModel outputPlatformRel = channelPlatformRelService.queryByPlatformId(channel, task.getOutputPlatformId());
            if (Objects.isNull(inputPlatformRel) || Objects.isNull(outputPlatformRel)) {
                throw new BusinessException(BusInessExceptionEnum.PLATFORM_CONFIG_NOT_EXIST);
            }
            StreamCopyService streamCopyService = new StreamCopyService(channel, inputPlatformRel.getPlatform(), outputPlatformRel.getPlatform(),
                    inputPlatformRel.getPlatformId(), outputPlatformRel.getPlatformId(), platformConfigService);
            int finallyStatus;
            String message;
            try {
                streamCopyService.copy(task.getSourceUrl(), task.getUploadDir());
                finallyStatus = TaskStatusEnum.SUCCESS.getStatus();
                message = "copy success";
            } catch (Exception e) {
                log.error("Copy task fail, err: ", e);
                finallyStatus = TaskStatusEnum.FAIL.getStatus();
                message = e.getMessage();
            }
            dispatchTaskService.modifyStatus(channel, streamingNo, finallyStatus, message);
        }

    }
}
