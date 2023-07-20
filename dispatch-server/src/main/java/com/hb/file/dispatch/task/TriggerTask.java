package com.hb.file.dispatch.task;

import com.hb.file.core.enums.TaskStatusEnum;
import com.hb.file.dispatch.entity.DispatchTaskModel;
import com.hb.file.dispatch.entity.TaskPriorityModel;
import com.hb.file.dispatch.service.DispatchTaskService;
import com.hb.file.dispatch.service.PlatformConfigService;
import com.hb.file.dispatch.service.TaskPriorityService;
import com.hb.file.dispatch.service.business.ChannelSupportService;
import com.hb.file.dispatch.service.business.stream.StreamCopyService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Component
public class TriggerTask {
    @Resource
    private TaskPriorityService taskPriorityService;
    @Resource
    private ChannelSupportService channelSupportService;
    @Resource
    private DispatchTaskService dispatchTaskService;
    @Resource
    private PlatformConfigService platformConfigService;

    /***
     * 监控上传任务续期，每10秒续期一次，每次续期15秒
     */
    @Scheduled(cron = "0/10 * * * * ?")
    public void startTask() {
        for (String channel : channelSupportService.getAllChannel()) {
            List<TaskPriorityModel> toStartTasks =
                    taskPriorityService.getPriorityTasks(channel, TaskStatusEnum.WAITING.getStatus(), 10);
            for (TaskPriorityModel toStartTask : toStartTasks) {
                // TODO 后续改成线程池调用
                doStart(toStartTask.getChannel(), toStartTask.getStreamingNo());
            }
        }
    }


    /**
     * 开始任务
     *
     * @param channel
     * @param streamingNo
     */
    private void doStart(String channel, String streamingNo) {
        // 查询任务
        DispatchTaskModel taskModel = dispatchTaskService.queryByStreamingNo(channel, streamingNo);
        if (Objects.nonNull(taskModel)) {
            StreamCopyService streamCopyService = new StreamCopyService(channel, taskModel.getInputPlatform(), taskModel.getOutputPlatform(), platformConfigService);
            streamCopyService.copy(taskModel.getSourceUrl(), taskModel.getUploadDir(), 1024);
        }

    }
}
