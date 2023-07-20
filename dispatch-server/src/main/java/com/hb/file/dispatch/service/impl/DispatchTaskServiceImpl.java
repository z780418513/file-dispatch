package com.hb.file.dispatch.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hb.file.core.enums.TaskStatusEnum;
import com.hb.file.dispatch.entity.DispatchTaskModel;
import com.hb.file.dispatch.mapper.DispatchTaskMapper;
import com.hb.file.dispatch.service.DispatchTaskService;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

@Service
public class DispatchTaskServiceImpl implements DispatchTaskService {
    @Resource
    private DispatchTaskMapper dispatchTaskMapper;


    @Override
    public boolean save(DispatchTaskModel model) {
        model.setStatus(TaskStatusEnum.WAITING.getStatus());
        model.setMessage("init");
        return dispatchTaskMapper.insert(model) > 0;
    }

    @Override
    public @Nullable DispatchTaskModel queryByStreamingNo(String channel, String streamingNo) {
        return dispatchTaskMapper.selectOne(Wrappers.<DispatchTaskModel>lambdaQuery()
                .eq(DispatchTaskModel::getChannel, channel)
                .eq(DispatchTaskModel::getStreamingNo, streamingNo)
                .last(" LIMIT 1 "));
    }

    @Override
    public void modifyStatus(String channel, String streamingNo, Integer status, String message) {
        DispatchTaskModel dispatchTaskModel = queryByStreamingNo(channel, streamingNo);
        if (Objects.isNull(dispatchTaskModel)) {
            return;
        }
        dispatchTaskModel.setStatus(status);
        dispatchTaskModel.setMessage(message);
        dispatchTaskMapper.updateById(dispatchTaskModel);
    }
}
