package com.hb.file.dispatch.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
    public boolean enableChannel(String channel, Boolean enable) {
        ChannelConfigModel channelModel = queryByChannel(channel);
        if (Objects.isNull(channelModel)) {
            throw new BusinessException(BusInessExceptionEnum.CHANNEL_NOT_EXIST);
        }
        channelModel.setEnable(enable);
        return channelConfigMapper.updateById(channelModel) > 0;
    }

    @Override
    public boolean delChannel(String channel) {
        return channelConfigMapper.delete(Wrappers.<ChannelConfigModel>lambdaQuery()
                .eq(ChannelConfigModel::getChannel, channel)) > 0;
    }

    @Override
    public ChannelConfigModel queryByChannel(String channel) {
        return channelConfigMapper.selectOne(Wrappers.<ChannelConfigModel>lambdaQuery()
                .eq(ChannelConfigModel::getChannel, channel)
                .orderByDesc(ChannelConfigModel::getCreateTime)
                .last(" LIMIT 1 "));
    }

    @Override
    public List<ChannelConfigModel> queryAllSupportChannel() {
        List<ChannelConfigModel> configModelList = channelConfigMapper.selectList(Wrappers.<ChannelConfigModel>lambdaQuery()
                .eq(ChannelConfigModel::getEnable, true));
        if (CollectionUtils.isEmpty(configModelList)) {
            return null;
        }
        return configModelList;
    }

    @Override
    public IPage<ChannelConfigModel> selectPage(Integer current, Integer pageSize) {
        Page<ChannelConfigModel> page = new Page<>(current, pageSize);
        return channelConfigMapper.selectPage(page, null);
    }


}
