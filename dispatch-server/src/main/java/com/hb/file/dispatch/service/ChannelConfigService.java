package com.hb.file.dispatch.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hb.file.dispatch.entity.ChannelConfigModel;
import org.springframework.lang.Nullable;

import java.util.List;

public interface ChannelConfigService {

    boolean initChannel(String channel, String channelName);

    boolean enableChannel(String channel, Boolean enable);

    boolean delChannel(String channel);

    @Nullable
    ChannelConfigModel queryByChannel(String channel);

    @Nullable
    List<ChannelConfigModel> queryAllSupportChannel();

    IPage<ChannelConfigModel> selectPage(Integer current, Integer pageSize);

}
