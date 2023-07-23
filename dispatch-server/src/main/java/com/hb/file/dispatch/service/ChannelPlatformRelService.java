package com.hb.file.dispatch.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hb.file.dispatch.entity.ChannelPlatformRelModel;
import org.springframework.lang.Nullable;

import java.util.List;

public interface ChannelPlatformRelService {

    List<ChannelPlatformRelModel> queryByChannel(String channel, @Nullable Integer platform);

    void saveOne(String channel, Integer platform, String platformName, String platformId);

    void remove(String channel, Integer platform, String platformId);

    @Nullable
    ChannelPlatformRelModel queryByPlatformId(String channel, String platformId);

    @Nullable
    IPage<ChannelPlatformRelModel> queryByChannel(String channel, Long current, Long size);

}
