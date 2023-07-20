package com.hb.file.dispatch.service;

import com.hb.file.dispatch.entity.ChannelConfigModel;
import org.springframework.lang.Nullable;

import java.util.List;

public interface ChannelConfigService {

    boolean initChannel(String channel, String channelName);

    boolean disableChannel(String channel);

    @Nullable
    ChannelConfigModel queryByChannel(String channel);

    @Nullable
    List<String> queryAllSupportChannel();

}
