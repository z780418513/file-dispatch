package com.hb.file.dispatch.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hb.file.core.enums.BusInessExceptionEnum;
import com.hb.file.core.exception.BusinessException;
import com.hb.file.dispatch.entity.ChannelConfigModel;
import com.hb.file.dispatch.mapper.ChannelConfigMapper;
import com.hb.file.dispatch.service.ChannelConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ChannelConfigServiceImpl implements ChannelConfigService {
    @Resource
    private ChannelConfigMapper channelConfigMapper;

    @Override
    public boolean initChannel(String channel, String channelName) {
        ChannelConfigModel model = queryByChannel(channel);

        if (Objects.isNull(model) || Boolean.TRUE.equals(!model.getEnable())) {
            model = new ChannelConfigModel();
            model.setChannel(channel);
            model.setChannelName(channelName);
            model.setEnable(true);
            return channelConfigMapper.insert(model) > 0;
        } else {
            log.warn("Channel: {} is enable, can't init channel again", channel);
            throw new BusinessException(BusInessExceptionEnum.CHANNEL_IS_ENABLE);
        }
    }

    @Override
    public boolean disableChannel(String channel) {
        ChannelConfigModel channelModel = queryByChannel(channel);
        if (Objects.isNull(channelModel) || Boolean.TRUE.equals(!channelModel.getEnable())) {
            log.warn("Channel: {} not exist or enable, can't disable", channel);
            throw new BusinessException(BusInessExceptionEnum.CHANNEL_NOT_EXIST_OR_ENABLE);
        }
        return doDisable(channelModel);
    }

    @Override
    public ChannelConfigModel queryByChannel(String channel) {
        return channelConfigMapper.selectOne(Wrappers.<ChannelConfigModel>lambdaQuery()
                .eq(ChannelConfigModel::getChannel, channel)
                .orderByDesc(ChannelConfigModel::getCreateTime)
                .last(" LIMIT 1 "));
    }

    @Override
    public List<String> queryAllSupportChannel() {
        List<ChannelConfigModel> configModelList = channelConfigMapper.selectList(Wrappers.<ChannelConfigModel>lambdaQuery()
                .eq(ChannelConfigModel::getEnable, true));
        if (CollectionUtils.isEmpty(configModelList)) {
            return null;
        }
        return configModelList.stream().map(ChannelConfigModel::getChannel).collect(Collectors.toList());
    }

    private boolean doDisable(ChannelConfigModel channelModel) {
        channelModel.setEnable(false);
        return channelConfigMapper.updateById(channelModel) > 0;
    }


}
