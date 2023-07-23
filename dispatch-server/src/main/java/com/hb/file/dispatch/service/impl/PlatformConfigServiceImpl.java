package com.hb.file.dispatch.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
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

@Service
@Slf4j
public class PlatformConfigServiceImpl implements PlatformConfigService {
    @Resource
    private PlatformConfigMapper platformConfigMapper;

    @Override
    @Transactional
    public boolean addConfigs(String platformId, Map<String, Object> configs) {
        for (Map.Entry<String, Object> config : configs.entrySet()) {
            PlatformConfigModel model = new PlatformConfigModel();
            model.setParameter(config.getKey());
            model.setValue(config.getValue().toString());
            model.setPlatformId(platformId);
            platformConfigMapper.insert(model);
        }
        return true;
    }

    @Override
    public HashMap<String, String> queryPlatformConfig(String platformId) {
        List<PlatformConfigModel> configs = queryConfigByPlatformId(platformId);
        if (CollectionUtils.isEmpty(configs)) {
            return new HashMap<>();
        }
        HashMap<String, String> params = new HashMap<>();
        configs.forEach(config -> params.put(config.getParameter(), config.getValue()));
        return params;
    }

    @Override
    public boolean removePlatformConfig(String platformId) {
        return platformConfigMapper.delete(Wrappers.<PlatformConfigModel>lambdaQuery()
                .eq(PlatformConfigModel::getPlatformId, platformId)) > 0;
    }


    private List<PlatformConfigModel> queryConfigByPlatformId(String platformId) {
        return platformConfigMapper.selectList(
                Wrappers.<PlatformConfigModel>lambdaQuery()
                        .eq(PlatformConfigModel::getPlatformId, platformId));
    }
}
