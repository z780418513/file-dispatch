package com.hb.file.dispatch.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hb.file.dispatch.entity.ChannelPlatformRelModel;
import com.hb.file.dispatch.mapper.ChannelPlatformRelMapper;
import com.hb.file.dispatch.service.ChannelPlatformRelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class ChannelPlatformRelServiceImpl implements ChannelPlatformRelService {

    @Resource
    private ChannelPlatformRelMapper channelPlatformRelMapper;

    @Override
    public List<ChannelPlatformRelModel> queryByChannel(String channel, @Nullable Integer platform) {

        return channelPlatformRelMapper.selectList(Wrappers.<ChannelPlatformRelModel>lambdaQuery()
                .eq(ChannelPlatformRelModel::getChannel, channel)
                .eq(Objects.nonNull(platform), ChannelPlatformRelModel::getPlatform, platform));
    }

    @Override
    public void saveOne(String channel, Integer platform, String platformName, String platformId) {
        ChannelPlatformRelModel model = new ChannelPlatformRelModel();
        model.setChannel(channel);
        model.setPlatform(platform);
        model.setPlatformId(platformId);
        model.setPlatformName(platformName);
        channelPlatformRelMapper.insert(model);
    }

    @Override
    public void remove(String channel, Integer platform, String platformId) {
        channelPlatformRelMapper.delete(Wrappers.<ChannelPlatformRelModel>lambdaQuery()
                .eq(ChannelPlatformRelModel::getChannel, channel)
                .eq(ChannelPlatformRelModel::getPlatform, platform)
                .eq(ChannelPlatformRelModel::getPlatformId, platformId));
    }

    @Override
    public ChannelPlatformRelModel queryByPlatformId(String channel, String platformId) {
        return channelPlatformRelMapper.selectOne(Wrappers.<ChannelPlatformRelModel>lambdaQuery()
                .eq(ChannelPlatformRelModel::getChannel, channel)
                .eq(ChannelPlatformRelModel::getPlatformId, platformId)
                .last("limit 1"));
    }

    @Nullable
    @Override
    public IPage<ChannelPlatformRelModel> queryByChannel(String channel, Long current, Long size) {

        Page<ChannelPlatformRelModel> page = new Page<>(current, size);
        return channelPlatformRelMapper.selectPage(page, Wrappers.<ChannelPlatformRelModel>lambdaQuery()
                .eq(ChannelPlatformRelModel::getChannel, channel));
    }
}
