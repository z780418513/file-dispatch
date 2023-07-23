package com.hb.file.dispatch.service.business.stream;

import cn.hutool.core.bean.BeanUtil;
import com.hb.file.core.enums.BusInessExceptionEnum;
import com.hb.file.core.exception.BusinessException;
import com.hb.file.core.factory.ObsClientFactory;
import com.hb.file.core.properties.ObsProperties;
import com.hb.file.core.utils.ObsUtil;
import com.hb.file.core.utils.StoreUtil;

import java.util.Map;
import java.util.Objects;

public class ObsStreamService extends AbstractPlatformService {

    @Override
    public StoreUtil getStoreUtil(String platformId) {
        return getObsUtil(platformId);
    }

    private ObsUtil getObsUtil(String platformId) {
        ObsProperties properties = getConfig(platformId);
        ObsClientFactory factory = new ObsClientFactory(properties);
        return new ObsUtil(factory, properties.getBucket());
    }

    private ObsProperties getConfig(String platformId) {
        Map<String, String> config = getConfigByPlatformId(platformId);
        if (Objects.isNull(config)) {
            throw new BusinessException(BusInessExceptionEnum.PLATFORM_CONFIG_NOT_EXIST);
        }
        return BeanUtil.mapToBean(config, ObsProperties.class, false, null);
    }
}
