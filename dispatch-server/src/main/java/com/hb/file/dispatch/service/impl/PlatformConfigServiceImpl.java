package com.hb.file.dispatch.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hb.file.core.enums.BusInessExceptionEnum;
import com.hb.file.core.exception.BusinessException;
import com.hb.file.dispatch.entity.BaseModel;
import com.hb.file.dispatch.entity.PlatformConfigModel;
import com.hb.file.dispatch.mapper.PlatformConfigMapper;
import com.hb.file.dispatch.service.PlatformConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PlatformConfigServiceImpl implements PlatformConfigService {
    @Resource
    private PlatformConfigMapper platformConfigMapper;

    @Override
    @Transactional
    public boolean addConfigs(String channel, Integer platform, Map<String, Object> configs) {
        List<PlatformConfigModel> configModels = queryConfigByPlatform(channel, platform);
        if (!CollectionUtils.isEmpty(configModels)) {
            throw new BusinessException(BusInessExceptionEnum.PLATFORM_CONFIG_IS_EXIST);
        }
        for (Map.Entry<String, Object> config : configs.entrySet()) {
            PlatformConfigModel model = new PlatformConfigModel();
            model.setParameter(config.getKey());
            model.setValue(config.getValue().toString());
            model.setChannel(channel);
            model.setPlatform(platform);
            platformConfigMapper.insert(model);
        }
        return true;
    }

    @Override
    public HashMap<String, String> queryPlatformConfig(String channel, Integer platform) {
        List<PlatformConfigModel> configs = queryConfigByPlatform(channel, platform);
        if (CollectionUtils.isEmpty(configs)) {
            return null;
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("channel", channel);
        params.put("platform", String.valueOf(platform));
        configs.forEach(config -> params.put(config.getParameter(), config.getValue()));
        return params;
    }

    @Override
    public boolean removePlatformConfig(String channel, Integer platform) {
        List<PlatformConfigModel> configModels = queryConfigByPlatform(channel, platform);
        if (CollectionUtils.isEmpty(configModels)) {
            throw new BusinessException(BusInessExceptionEnum.PLATFORM_CONFIG_NOT_EXIST);
        }
        List<Long> ids = configModels.stream().map(BaseModel::getId).collect(Collectors.toList());
        return platformConfigMapper.deleteBatchIds(ids) > 0;
    }

    private List<PlatformConfigModel> queryConfigByPlatform(String channel, Integer platform) {
        return platformConfigMapper.selectList(
                Wrappers.<PlatformConfigModel>lambdaQuery()
                        .eq(PlatformConfigModel::getChannel, channel)
                        .eq(PlatformConfigModel::getPlatform, platform));
    }
}
