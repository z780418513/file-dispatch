package com.hb.file.dispatch.service.business.stream;

import com.hb.file.core.enums.BusInessExceptionEnum;
import com.hb.file.core.enums.PlatformEnum;
import com.hb.file.core.exception.BusinessException;
import com.hb.file.core.factory.ObsClientFactory;
import com.hb.file.core.properties.ObsProperties;
import com.hb.file.core.utils.ObsUtil;
import com.hb.file.core.utils.StoreUtil;
import org.springframework.beans.BeanUtils;

import java.util.Map;
import java.util.Objects;

public class ObsStreamService extends AbstractPlatformService {

    @Override
    public StoreUtil getStoreUtil(String channel) {
        return getObsUtil(channel);
    }

    private ObsUtil getObsUtil(String channel) {
        ObsProperties properties = getConfig(channel);
        ObsClientFactory factory = new ObsClientFactory(properties);
        return new ObsUtil(factory, properties.getBucket());
    }

    private ObsProperties getConfig(String channel) {
        Map<String, String> config = getConfig(channel, PlatformEnum.OBS);
        if (Objects.isNull(config)) {
            throw new BusinessException(BusInessExceptionEnum.PLATFORM_CONFIG_NOT_EXIST);
        }
        ObsProperties properties = new ObsProperties();
        BeanUtils.copyProperties(config, properties);
        return properties;
    }
}
