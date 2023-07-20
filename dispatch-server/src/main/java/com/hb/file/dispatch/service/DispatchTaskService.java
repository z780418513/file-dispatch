package com.hb.file.dispatch.service;

import com.hb.file.dispatch.entity.DispatchTaskModel;
import org.springframework.lang.Nullable;

public interface DispatchTaskService {
    boolean save(DispatchTaskModel model);

    @Nullable
    DispatchTaskModel queryByStreamingNo(String channel, String streamingNo);

    void modifyStatus(String channel, String streamingNo, Integer status, String message);
}
